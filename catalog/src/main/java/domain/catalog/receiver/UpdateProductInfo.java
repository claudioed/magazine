
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
public class UpdateProductInfo extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProductInfo.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient
                .createShared(vertx, new JsonObject().put("db_name", DomainDb.CATALOG.db()),
                        DomainDb.CATALOG.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.UPDATE_PRODUCT.event(), message -> {
            JsonObject updatedProduct = new JsonObject(message.body().toString());
            JsonObject query = new JsonObject().put("_id", updatedProduct.getString("id"));
            JsonObject update = new JsonObject().put("$set", new JsonObject().put("name", updatedProduct.getString("id")).put("price",updatedProduct.getDouble("price")));
            mongoClient.update(DomainCollection.PRODUCTS.collection(),query,update,result ->{
                if(result.failed()){
                    LOGGER.error("Error on update product info !!! Any rows affected !!!");
                } else {
                    LOGGER.info("Success on update product info !!!");
                }
            });
        });

    }

}
