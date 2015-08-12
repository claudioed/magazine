package server;

import domain.sales.api.SaleAPI;
import domain.sales.receiver.RegisterSale;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Delivery API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class SaleServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new SaleAPI());
        vertx.deployVerticle(new RegisterSale());
    }

}
