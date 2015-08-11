
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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

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
                new JsonObject().put(DomainDb.CATALOG.db(), DomainDb.CATALOG.db()), DomainDb.CATALOG.db());
        final Router router = Router.router(vertx);
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

        router.get("/api/product/barcode/:barcode").handler(
                ctx -> mongoClient.find(DomainCollection.PRODUCTS.collection(), new JsonObject().put("barcode.plainBarcode",ctx.request().getParam("barcode")), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.post("/api/product").handler(ctx -> {
            vertx.eventBus().publish(DomainEvent.NEW_PRODUCT.event(), new ProductWrapper(ctx.getBodyAsJson()).toJson());
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(9004);
        
    }

}
