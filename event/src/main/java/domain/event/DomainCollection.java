package domain.event;

/**
 * Constants for system collections.
 * 
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public enum  DomainCollection {
    
    SALES,
    
    CUSTOMERS,

    PRODUCTS,

    ITEMS,
    
    DAILY_SALES,
    
    GATHERINGS,
    
    DELIVERIES;
    
    public String collection(){
        return this.name().toLowerCase();
    }
    
}
