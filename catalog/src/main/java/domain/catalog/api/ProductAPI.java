
package domain.catalog.api;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.web.MediaType;
import infra.wrapper.ProductWrapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class ProductAPI extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new ProductAPI());
    }

    @Override
    public void start() throws Exception {

        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CATALOG.db()), DomainDb.CATALOG.poolName());

        final Router router = Router.router(vertx);

        final CorsHandler corsHandler = CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type")
                .allowedHeader("Access-Control-Allow-Origin");

        router.route().handler(corsHandler);
        router.route().handler(BodyHandler.create());

        router.get("/api/products").handler(
                ctx -> mongoClient.find(DomainCollection.PRODUCTS.collection(), new JsonObject(), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.get("/api/product/barcode/:plainBarcode").handler(
                ctx -> mongoClient.find(DomainCollection.PRODUCTS.collection(), new JsonObject().put("barcode.plainBarcode",ctx.request().getParam("plainBarcode")), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }else if(lookup.result().isEmpty()){
                        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                        ctx.response().setStatusCode(404);
                        ctx.response().end(new JsonObject().encode());
                        return;
                    }
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(lookup.result().get(0).encode());
                }));

        router.post("/api/product").handler(ctx -> {
            vertx.eventBus().publish(DomainEvent.NEW_PRODUCT.event(), new ProductWrapper(ctx.getBodyAsJson()).toJson());
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8004));
        
    }

}
