package domain.event;

/**
 * Constants for system events.
 * 
 *  @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public enum DomainEvent {

    REGISTER_DELIVERY,

    REGISTER_SALE,

    REGISTER_GATHERING,

    NEW_CUSTOMER,

    NEW_CUSTOMER_PREFERENCE,

    UPDATE_PRODUCT,

    NEW_PRODUCT,

    REGISTER_DELIVERY_ITEM,

    SEND_EMAIL_DELIVERY_ITEM,

    REGISTER_ITEM_BY_SALE,

    REGISTER_ITEM_BY_GATHERING;

    public String event(){
        return this.name();
    }

}
