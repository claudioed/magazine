package domain.gathering.api;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.web.MediaType;
import infra.wrapper.GatheringWrapper;
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
public class GatheringAPI extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new GatheringAPI());
    }

    @Override
    public void start() throws Exception {

        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.GATHERING.db()), DomainDb.GATHERING.poolName());
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create()).handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type")).handler(BodyHandler.create());

        router.get("/api/gatherings").handler(
                ctx -> mongoClient.find(DomainCollection.GATHERINGS.collection(), new JsonObject(), lookup -> {
                    if (lookup.failed()) {
                        ctx.fail(lookup.cause());
                        return;
                    }
                    final JsonArray json = new JsonArray();
                    lookup.result().forEach(json::add);
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
                    ctx.response().end(json.encode());
                }));

        router.post("/api/gathering").handler(ctx -> {
            vertx.eventBus().publish(DomainEvent.REGISTER_GATHERING.event(), new GatheringWrapper(ctx.getBodyAsJson()).toJson());
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.mediaType());
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8007));
    }

}