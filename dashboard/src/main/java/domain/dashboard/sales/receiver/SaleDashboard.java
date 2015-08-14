
package domain.dashboard.sales.receiver;

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
public class SaleDashboard extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleDashboard.class);

    @Override
    public void start() throws Exception {
        final MongoClient mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", DomainDb.DASHBOARD.db()),
                DomainDb.DASHBOARD.poolName());
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(DomainEvent.REGISTER_SALE.event(), message -> {
            JsonObject sale = new JsonObject(message.body().toString());
                    Double saleTotal = sale.getDouble("total");
            }

        );
    }

}
