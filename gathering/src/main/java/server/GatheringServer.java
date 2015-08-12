package server;

import domain.gathering.api.GatheringAPI;
import domain.gathering.receiver.RegisterGathering;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Gathering API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class GatheringServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatheringServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new GatheringAPI(9007));
                vertx.deployVerticle(new RegisterGathering());
                LOGGER.info("***************************************");
                LOGGER.info(" Gathering Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Gathering Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
