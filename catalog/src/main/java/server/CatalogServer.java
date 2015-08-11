package server;

import domain.catalog.api.ProductAPI;
import domain.catalog.receiver.RegisterProduct;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Catalog API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CatalogServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new ProductAPI());
        vertx.deployVerticle(new RegisterProduct());
    }

}
