package domain.event;

/**
 * Constants for email system.
 *
 *  @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public enum DomainEmail {

    ADMIN_EMAIL;

    public String email(){
        return this.name();
    }

}
