package domain.customer.receiver;

import domain.event.DomainCollection;
import domain.event.DomainDb;
import domain.event.DomainEvent;
import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class RegisterCustomer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.CUSTOMER.db()), DomainDb.CUSTOMER.poolName());
        EventBus eb = vertx.eventBus();
        eb.consumer(DomainEvent.NEW_CUSTOMER.event(), message ->
                mongoClient.insert(DomainCollection.CUSTOMERS.collection(), new JsonObject(message
                        .body().toString()).put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now()))), result -> {
                }));
    }

}
