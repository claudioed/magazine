package domain.customer.receiver;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

/**
 * This receiver is responsible to register customer preferences.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class RegisterCustomerPreference extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterCustomerPreference.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,new JsonObject().put("db_name", DomainDb.CUSTOMER_PREFERENCE.db()), DomainDb.CUSTOMER_PREFERENCE.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.NEW_CUSTOMER_PREFERENCE.event(), message ->{
                JsonObject item = new JsonObject(message.body().toString());
                mongoClient.insert(DomainCollection.FAVORITE_PRODUCTS.collection(), item, result -> {
                    if(result.succeeded()){
                        LOGGER.info("Customer Preferences inserted with success !!!");
                    } else {
                        LOGGER.error("Error on insert Customer Preferences !!!");
                    }
                });
        });
    }

}
