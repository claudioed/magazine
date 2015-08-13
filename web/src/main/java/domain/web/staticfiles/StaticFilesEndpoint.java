package domain.web.staticfiles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

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
        router.route().handler(BodyHandler.create()).handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type"));
        router.get("/").handler( ctx -> ctx.response().sendFile("app/index.html"));
        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }

}