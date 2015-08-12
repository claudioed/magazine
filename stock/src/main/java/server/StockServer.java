package server;

import domain.stock.api.StockAPI;
import domain.stock.receiver.RegisterStock;
import domain.stock.receiver.RegisterStockByGathering;
import domain.stock.receiver.RegisterStockBySale;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Stock API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class StockServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new StockAPI(9010));
                vertx.deployVerticle(new RegisterStock());
                vertx.deployVerticle(new RegisterStockByGathering());
                vertx.deployVerticle(new RegisterStockBySale());
                LOGGER.info("***************************************");
                LOGGER.info(" Stock Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Stock Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
