package server;

import domain.delivery.api.DeliveryAPI;
import domain.delivery.receiver.RegisterDelivery;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Delivery API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class DeliveryServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new DeliveryAPI());
                vertx.deployVerticle(new RegisterDelivery());
                LOGGER.info("***************************************");
                LOGGER.info(" Delivery Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Delivery Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
