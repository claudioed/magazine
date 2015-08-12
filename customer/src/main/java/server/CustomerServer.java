package server;

import domain.customer.api.CustomerAPI;
import domain.customer.receiver.RegisterCustomer;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Magazine Manager.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CustomerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new CustomerAPI(9005));
                vertx.deployVerticle(new RegisterCustomer());
                LOGGER.info("***************************************");
                LOGGER.info(" Customer Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Customer Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
