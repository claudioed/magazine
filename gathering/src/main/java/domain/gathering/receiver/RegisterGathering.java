
package domain.gathering.receiver;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class RegisterGathering extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterGathering.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.GATHERING.db()), DomainDb.GATHERING.db());
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(DomainEvent.REGISTER_GATHERING.event(), message ->
                mongoClient.insert(DomainCollection.GATHERINGS.collection(), new JsonObject(message
                        .body().toString()), result -> {
                    if (result.succeeded()) {
                        LOGGER.info("Success on insert gathering!!!");
                        mongoClient.findOne(
                                DomainCollection.GATHERINGS.collection(),
                                new JsonObject(),
                                new JsonObject(),
                                saleResult -> {
                                    if (saleResult.failed()) {
                                        LOGGER.error("Error on find gathering!!!");
                                    } else {
                                        JsonArray saleItems = saleResult.result().getJsonArray(
                                                "items");
                                        saleItems.forEach(item -> eventBus.publish(
                                                DomainEvent.REGISTER_ITEM_BY_GATHERING.event(), item));
                                    }
                                });
                    } else {
                        LOGGER.error("Error on insert gathering!!!");
                    }
                }));
    }

}
