package domain.web.server;

import domain.web.staticfiles.StaticFilesEndpoint;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * The startup server of Static Files Server.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class StaticFilesServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new StaticFilesEndpoint(9011));
    }

}
