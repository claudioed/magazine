
package domain.customer.receiver;

import domain.event.DomainEmail;
import domain.event.DomainEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

/**
 *  This receiver send email favorite product for customer.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class SendFavoriteProductMailService extends AbstractVerticle {

    private final static Logger LOGGER = LoggerFactory.getLogger(SendFavoriteProductMailService.class);

    private final static String HOSTNAME = System.getProperty("HOSTNAME");

    private final static int PORT = Integer.valueOf(System.getProperty("PORT"));

    private final static String USER = System.getProperty("USER");

    private final static String PASSWORD = System.getProperty("PASSWORD");

    private final static String FROM = System.getProperty("FROM");

    private final static String MESSAGE = "Hello %s .Your favorite magazine %s arrived";

    @Override
    public void start() throws Exception {
        MailConfig config = new MailConfig().setHostname(HOSTNAME).setPort(PORT).setUsername(USER).setPassword(PASSWORD).setStarttls(StartTLSOptions.REQUIRED);
        MailClient mailClient = MailClient.createShared(vertx, config, DomainEmail.ADMIN_EMAIL.email());
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(DomainEvent.SEND_EMAIL_DELIVERY_ITEM.event(), message -> {
                    JsonObject emailInformation = new JsonObject(message.body().toString());
                    MailMessage mailMessage = new MailMessage();
                    mailMessage.setFrom(FROM);
                    mailMessage.setTo(emailInformation.getString("email"));
                    mailMessage.setText(String.format(MESSAGE,emailInformation.getString("name"),emailInformation.getString("magazine")));
                    mailClient.sendMail(mailMessage, mailResult ->{
                        if(mailResult.failed()){
                            LOGGER.error("Error on send favorite product mail");
                        }else {
                            LOGGER.info("Success on send email");
                        }
                    });
                }
        );
    }
}
