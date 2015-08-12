
package domain.sales.receiver;

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
public class RegisterSale extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterSale.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.SALE.db()), DomainDb.SALE.db());
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(DomainEvent.REGISTER_SALE.event(), message ->
                mongoClient.insert(DomainCollection.SALES.collection(), new JsonObject(message
                        .body().toString()), result -> {
                    if (result.succeeded()) {
                        LOGGER.info("Success on insert sale!!!");
                        mongoClient.findOne(
                                DomainCollection.SALES.collection(),
                                new JsonObject(),
                                new JsonObject(),
                                saleResult -> {
                                    if (saleResult.failed()) {
                                        LOGGER.error("Error on find sale!!!");
                                    } else {
                                        JsonArray saleItems = saleResult.result().getJsonArray(
                                                "items");
                                        saleItems.forEach(item -> eventBus.publish(
                                                DomainEvent.REGISTER_ITEM_BY_SALE.event(), item));
                                    }
                                });
                    } else {
                        LOGGER.error("Error on insert sale!!!");
                    }
                }));
    }

}
