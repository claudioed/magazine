package server;

import domain.customer.api.CustomerPreferenceAPI;
import domain.customer.receiver.NotifyCustomerDelivery;
import domain.customer.receiver.RegisterCustomerPreference;
import domain.customer.receiver.SendFavoriteProductMailService;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * The startup server of Customer Preference API.
 *  
 * @author Claudio Eduardo de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CustomerPreferenceServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPreferenceServer.class);

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new CustomerPreferenceAPI());
                vertx.deployVerticle(new NotifyCustomerDelivery());
                vertx.deployVerticle(new RegisterCustomerPreference());
                vertx.deployVerticle(new SendFavoriteProductMailService());
                LOGGER.info("****************************************************");
                LOGGER.info(" Customer Preference Server Connected on cluster !!!");
                LOGGER.info("****************************************************");
            } else {
                LOGGER.error("*************************************************************************");
                LOGGER.error(" Customer Preference Server Failed on connect on cluster : " + res.cause());
                LOGGER.error("*************************************************************************");
            }
        });

    }

}
