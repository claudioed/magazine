package server;

import domain.customer.api.CustomerAPI;
import domain.customer.receiver.RegisterCustomer;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Magazine Manager.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CustomerServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new CustomerAPI());
        vertx.deployVerticle(new RegisterCustomer());
    }

}
