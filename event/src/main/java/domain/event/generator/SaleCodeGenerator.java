package domain.event.generator;

import java.time.LocalDateTime;

/**
 * It is responsible to generate code for sale.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class SaleCodeGenerator extends CodeGenerator{

    public SaleCodeGenerator(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public String prefix() {
        return "V";
    }

}
