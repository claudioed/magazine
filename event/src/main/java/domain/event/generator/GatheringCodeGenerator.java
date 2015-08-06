package domain.event.generator;

import java.time.LocalDateTime;

/**
 * 
 * It is responsible to generate code for gathering.
 *  
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class GatheringCodeGenerator extends CodeGenerator{

    public GatheringCodeGenerator(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public String prefix() {
        return "R";
    }
    
}
