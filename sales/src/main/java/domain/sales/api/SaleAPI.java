package domain.sales.api;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.web.MediaType;
import infra.wrapper.SaleWrapper;
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
public class SaleAPI extends AbstractVerticle {

    private final Integer port;

    public SaleAPI(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new SaleAPI(9006));
    }

    @Override
    public void start() throws Exception {

        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.DELIVERY.db()), DomainDb.DELIVERY.poolName());
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create()).handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type")).handler(BodyHandler.create());

        router.get("/api/sales").handler(
                ctx -> mongoClient.find(DomainCollection.SALES.collection(), new JsonObject(), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.post("/api/sale").handler(ctx -> {
            vertx.eventBus().publish(DomainEvent.REGISTER_SALE.event(), new SaleWrapper(ctx.getBodyAsJson()).toJson());
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }

}