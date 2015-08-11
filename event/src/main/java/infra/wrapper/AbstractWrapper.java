package infra.wrapper;

import io.vertx.core.json.JsonObject;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public abstract class AbstractWrapper<T> implements RequestWrapper<T>{
    
    protected final JsonObject toConvert;

    protected AbstractWrapper(JsonObject toConvert) {
        this.toConvert = toConvert;
    }

}
