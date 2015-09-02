
package domain.stock.receiver;

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
 * This receiver is responsible to update stock information when the product info
 * was changed in catalog micro-service.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class UpdateStockInfo extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStockInfo.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", DomainDb.STOCK.db()),
                DomainDb.STOCK.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.UPDATE_PRODUCT.event(), message -> {
            JsonObject updatedProduct = new JsonObject(message.body().toString());
            JsonObject query = new JsonObject().put("plainBarcode", updatedProduct.getString("plainBarcode"));
            JsonObject update = new JsonObject().put("$set", new JsonObject().put("name", updatedProduct.getString("name")));
            mongoClient.update(DomainCollection.ITEMS.collection(),query,update,result ->{
                if(result.failed()){
                    LOGGER.error("Error on update stock info !!! Any rows affected !!!");
                } else {
                    LOGGER.info("Success on update stock info !!!");
                }
            });
        });

    }

}
