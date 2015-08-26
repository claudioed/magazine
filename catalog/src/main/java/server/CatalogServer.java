package server;

import domain.catalog.api.ProductAPI;
import domain.catalog.receiver.NewProductByDelivery;
import domain.catalog.receiver.RegisterProduct;
import domain.catalog.receiver.UpdateProductInfo;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Catalog API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CatalogServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new ProductAPI());
                vertx.deployVerticle(new RegisterProduct());
                vertx.deployVerticle(new NewProductByDelivery());
                vertx.deployVerticle(new UpdateProductInfo());
                LOGGER.info("***************************************");
                LOGGER.info(" Catalog Server Connected on cluster !!!");
                LOGGER.info("***************************************");
            } else {
                LOGGER.error("*************************************************************");
                LOGGER.error(" Catalog Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************");
            }
        });
    }

}
