package server;

import domain.gathering.api.GatheringAPI;
import domain.gathering.receiver.RegisterGathering;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Delivery API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class GatheringServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new GatheringAPI());
        vertx.deployVerticle(new RegisterGathering());
    }

}
