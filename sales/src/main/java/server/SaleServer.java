package server;

import domain.sales.api.SaleAPI;
import domain.sales.receiver.RegisterSale;
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
public class SaleServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new SaleAPI(9006));
                vertx.deployVerticle(new RegisterSale());
                LOGGER.info("***************************************");
                LOGGER.info(" Sale Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Sale Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
