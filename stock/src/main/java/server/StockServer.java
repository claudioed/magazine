package server;

import domain.stock.receiver.RegisterStock;
import domain.stock.receiver.RegisterStockByGathering;
import domain.stock.receiver.RegisterStockBySale;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Stock API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class StockServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new RegisterStock());
        vertx.deployVerticle(new RegisterStockByGathering());
        vertx.deployVerticle(new RegisterStockBySale());
    }

}
