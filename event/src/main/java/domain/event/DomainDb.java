package domain.event;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public enum  DomainDb {
    
    CATALOG,
    
    DELIVERY,

    SALE,

    GATHERING,
    
    CUSTOMER,

    STOCK,

    DASHBOARD,

    CUSTOMER_PREFERENCE;
    
    public String db(){
        return this.name();
    }

    public String poolName(){
        return this.name() + "-POOL";
    }

}
