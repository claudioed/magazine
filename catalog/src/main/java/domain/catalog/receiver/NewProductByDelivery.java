package domain.catalog.receiver;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.formatter.DateTimeMongoFormat;
import infra.integration.Services;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class NewProductByDelivery extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewProductByDelivery.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CATALOG.db()), DomainDb.CATALOG.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.REGISTER_DELIVERY_ITEM.event(), message -> {
            JsonObject item = new JsonObject(message.body().toString());
            mongoClient.findOne(DomainCollection.PRODUCTS.collection(),new JsonObject().put("barcode",item.getJsonObject("barcode").getString("barcode")),new JsonObject(),res ->{
                if(res.failed() || res.result() == null){
                    JsonObject product = new JsonObject().put("barcode", item.getJsonObject("barcode").getString("barcode")).put("registeredAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
                    mongoClient.insert(DomainCollection.PRODUCTS.collection(), product, result -> {
                        if (result.succeeded()) {
                            LOGGER.info("Product inserted with success !!!");
                        } else {
                            LOGGER.error("Error on insert product !!!");
                        }
                    });
                    
                }else{
                    LOGGER.info("Product already registered !!!");
                }
            });
        });
    }

}
