package domain.event.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * Abstract class for generate code.
 *
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public abstract class CodeGenerator {

    private final LocalDateTime dateTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-M-yyyy");

    public CodeGenerator(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String newCode() {
        return prefix() + "-" + FORMATTER.format(dateTime) + "-" + UUID.randomUUID().toString();
    }
    
    public abstract String prefix();
    
}