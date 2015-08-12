
package domain.delivery.receiver;

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
public class RegisterDelivery extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterDelivery.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CUSTOMER.db()),
                DomainDb.CUSTOMER.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.REGISTER_DELIVERY.event(), message ->
                mongoClient.insert(DomainCollection.DELIVERIES.collection(), new JsonObject(message
                        .body().toString()), result -> {
                    if (result.failed()) {
                        LOGGER.error("Error on insert delivery in DB");
                    } else {
                        if (result.succeeded()) {
                            LOGGER.info("Delivery inserted in DB");
                            mongoClient.findOne(DomainCollection.DELIVERIES.collection(),
                                    new JsonObject().put("_id", result.result()), new JsonObject(),
                                    handler -> {
                                        if(result.succeeded()){
                                            JsonArray items = new JsonObject(result.result()).getJsonArray("items");
                                            items.forEach(item -> eb.publish(DomainEvent.REGISTER_DELIVERY_ITEM.event(),item));
                                        }
                                    });
                        }
                    }
                }));
    }

}
