package domain.stock.receiver;

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
public class RegisterStock extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterStock.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.STOCK.db()), DomainDb.STOCK.db());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.REGISTER_DELIVERY_ITEM.event(), message ->{
                JsonObject item = new JsonObject(message.body().toString());
                mongoClient.insert(DomainCollection.ITEMS.collection(), item.put("registeredAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now()))).put("available",Boolean.TRUE), result -> {
                    if(result.succeeded()){
                        LOGGER.info("Stock inserted with success !!!");
                        vertx.createHttpClient().getNow(Services.CATALOG.port(),Services.CATALOG.host(),"/api/product/barcode/"+item.getJsonObject("barcode").getString("plainBarcode"),resultHttp ->{
                            if(resultHttp.statusCode() == 200){
                                resultHttp.bodyHandler(body -> {
                                    JsonObject product = new JsonObject(body.toString());
                                    JsonObject query = new JsonObject().put("_id", result.result());
                                    JsonObject update = new JsonObject().put("$set", new JsonObject().put("name", product.getString("name")));
                                    mongoClient.update(DomainCollection.ITEMS.collection(),query,update,updateResult ->{
                                        if(updateResult.succeeded()){
                                            LOGGER.info("Success on update name in stock item !!! ");
                                        }
                                    });
                                });
                            }else{
                                LOGGER.error("Error on get product information !!! " + resultHttp.statusMessage());
                            }
                        });
                    } else {
                        LOGGER.error("Error on insert stock !!!");
                    }
                });
        });
    }

}
