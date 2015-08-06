package domain.event.generator;

import java.time.LocalDateTime;

/**
 * 
 * It is responsible to generate code for delivery.
 *  
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class DeliveryCodeGenerator extends CodeGenerator{

    public DeliveryCodeGenerator(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public String prefix() {
        return "E";
    }
    
}
