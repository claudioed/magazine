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
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class RegisterCustomer extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterCustomer.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CUSTOMER.db()), DomainDb.CUSTOMER.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.NEW_CUSTOMER.event(), message ->
                mongoClient.insert(DomainCollection.CUSTOMERS.collection(), new JsonObject(message
                        .body().toString()), result -> {
                    if(result.failed()){
                        LOGGER.error("Error on insert customer in DB");
                    }else{
                        LOGGER.info("Customer inserted in DB");
                    }
                }));
    }

}
