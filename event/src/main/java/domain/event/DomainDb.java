package domain.event;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public enum  DomainDb {
    
    CATALOG,
    
    DELIVERY,

    SALE,

    GATHERING,
    
    CUSTOMER;
    
    public String db(){
        return this.name();
    }

}
