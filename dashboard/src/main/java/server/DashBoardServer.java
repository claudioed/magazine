package server;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Dashboard API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class DashBoardServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                LOGGER.info("***************************************");
                LOGGER.info(" DashBoard Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" DashBoard Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
