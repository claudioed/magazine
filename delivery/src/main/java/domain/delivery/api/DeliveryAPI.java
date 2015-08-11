package domain.delivery.api;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.web.MediaType;
import infra.wrapper.DeliveryWrapper;
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
public class DeliveryAPI extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new DeliveryAPI());
    }

    @Override
    public void start() throws Exception {

        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put(DomainDb.DELIVERY.db(), DomainDb.DELIVERY.db()), DomainDb.DELIVERY.db());
        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/api/deliveries").handler(
                ctx -> mongoClient.find(DomainCollection.DELIVERIES.collection(), new JsonObject(), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.post("/api/delivery").handler(ctx -> {
            vertx.eventBus().publish(DomainEvent.REGISTER_DELIVERY.event(), new DeliveryWrapper(ctx.getBodyAsJson()).toJson());
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(9002);
    }

}