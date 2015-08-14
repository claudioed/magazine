
package domain.web.staticfiles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;

import java.io.File;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class StaticFilesEndpoint extends AbstractVerticle {

    private final Integer port;

    public StaticFilesEndpoint(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());
        vertx.deployVerticle(new StaticFilesEndpoint(9011));
    }

    @Override
    public void start() throws Exception {
        final Router router = Router.router(vertx);
        router.get("/").handler(ctx -> ctx.request().response().sendFile("webroot/index.html"));
        router.get("/**.*\\.(css|js)$").handler(ctx -> ctx.request().response().sendFile("webroot/" + new File(ctx.request().path())));
        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }

}
