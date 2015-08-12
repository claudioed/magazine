
package domain.customer.preference.receiver;

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
public class NotifyCustomerDelivery extends AbstractVerticle {

    private final static Logger LOGGER = LoggerFactory.getLogger(NotifyCustomerDelivery.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CUSTOMER_PREFERENCE.db()),
                DomainDb.CUSTOMER_PREFERENCE.db());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.REGISTER_DELIVERY_ITEM.event(), message -> {
                    JsonObject deliveredItem = new JsonObject(message.body().toString());
                    mongoClient.find(DomainCollection.FAVORITE_PRODUCTS.collection(),new JsonObject().put("barcode",deliveredItem.getString("barcode")),result ->{
                        if(result.failed()){
                            LOGGER.error("Error on find customer favorites");
                        }
                    });
                }
        );
    }
}
