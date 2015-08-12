
package domain.stock.api;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import infra.web.MediaType;
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
public class StockAPI extends AbstractVerticle {

    private final Integer port;

    public StockAPI(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new StockAPI(9010));
    }

    @Override
    public void start() throws Exception {

        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.STOCK.db()), DomainDb.STOCK.poolName());
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create()).handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type"));

        router.get("/api/items").handler(
                ctx -> mongoClient.find(DomainCollection.ITEMS.collection(), new JsonObject(), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.get("/api/items/barcode/:plainBarcode").handler(
                ctx -> mongoClient.find(DomainCollection.ITEMS.collection(), new JsonObject().put("barcode.plainBarcode",ctx.request().getParam("plainBarcode")).put("available",Boolean.TRUE), lookup -> {
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
                    final JsonArray resultArray = new JsonArray();
                    lookup.result().forEach(resultArray::add);
                    ctx.response().end(resultArray.encode());
                }));

        vertx.createHttpServer().requestHandler(router::accept).listen(port);
        
    }

}
