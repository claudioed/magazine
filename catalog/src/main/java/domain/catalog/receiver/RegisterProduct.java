package domain.catalog.receiver;

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
public class RegisterProduct extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterProduct.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CATALOG.db()), DomainDb.CATALOG.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.NEW_PRODUCT.event(), message ->
                mongoClient.insert(DomainCollection.PRODUCTS.collection(), new JsonObject(message.body().toString()), result -> {
                    if (result.succeeded()) {
                        LOGGER.info("Success on insert product!!!");
                    } else {
                        LOGGER.error("Error on insert product!!!");
                    }
                }));
    }

}
