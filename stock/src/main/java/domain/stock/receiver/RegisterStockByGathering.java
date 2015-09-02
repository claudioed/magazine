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
import io.vertx.ext.mongo.UpdateOptions;

/**
 * This receiver is responsible to make unavailable stock when this stock was
 * used in a gathering.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class RegisterStockByGathering extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterStockByGathering.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.STOCK.db()),
                DomainDb.STOCK.db());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.REGISTER_ITEM_BY_GATHERING.event(), message -> {
            JsonObject saleItem = new JsonObject(message.body().toString());
            JsonObject query = new JsonObject().put("barcode.plainBarcode", saleItem.getString("plainBarcode"));
            JsonObject update = new JsonObject().put("$set", new JsonObject().put("available", Boolean.FALSE).put("gathering", saleItem.getString("gathering")));
            UpdateOptions options = new UpdateOptions().setMulti(false);
            mongoClient.updateWithOptions(DomainCollection.ITEMS.collection(),query,update,options,result ->{
                if(result.failed()){
                    LOGGER.error("Error on update stock by gathering !!! Any rows affected !!!");
                } else {
                    LOGGER.info("Success on update stock by gathering !!!");
                }
            });
        });
    }

}
