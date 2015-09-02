
package domain.customer.receiver;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.integration.Services;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

/**
 * This receiver is responsible to select customer with the new product.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class NotifyCustomerDelivery extends AbstractVerticle {

    private final static Logger LOGGER = LoggerFactory.getLogger(NotifyCustomerDelivery.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,new JsonObject().put("db_name", DomainDb.CUSTOMER_PREFERENCE.poolName()),DomainDb.CUSTOMER_PREFERENCE.db());
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(DomainEvent.REGISTER_DELIVERY_ITEM.event(), message -> {
                    JsonObject deliveredItem = new JsonObject(message.body().toString());
                    mongoClient.find(DomainCollection.FAVORITE_PRODUCTS.collection(),new JsonObject().put("barcode",deliveredItem.getString("barcode")),result ->{
                        if(result.failed()){
                            LOGGER.error("Error on find customer favorites");
                        }
                        List<JsonObject> users = result.result();
                        if(users.isEmpty()){
                            LOGGER.info("Customers list is empty");
                        }else{
                            vertx.createHttpClient().getNow(Services.CATALOG.port(),Services.CATALOG.host(),"/api/product/barcode/"+deliveredItem.getJsonObject("barcode").getString("barcode"),resultHttp ->{
                                if(resultHttp.statusCode() == 200){
                                    resultHttp.bodyHandler(body -> {
                                        JsonObject product = new JsonObject(body.toString());
                                        users.forEach(el ->{
                                            JsonObject user = new JsonObject(el.toString());
                                            eventBus.publish(DomainEvent.SEND_EMAIL_DELIVERY_ITEM.event(),user);
                                        });
                                    });
                                }else{
                                    LOGGER.error("Error on get product information !!! " + resultHttp.statusMessage());
                                }
                            });
                        }
                    });
                }
        );
    }
}
