package server;

import domain.delivery.api.DeliveryAPI;
import domain.delivery.receiver.RegisterDelivery;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Delivery API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class DeliveryServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new DeliveryAPI());
        vertx.deployVerticle(new RegisterDelivery());
    }

}
