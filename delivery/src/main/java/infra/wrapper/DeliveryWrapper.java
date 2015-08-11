package infra.wrapper;

import io.vertx.core.json.JsonObject;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public class DeliveryWrapper extends AbstractWrapper<JsonObject> {

    public DeliveryWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    @Override
    public JsonObject toJson() {
        return null;
    }
    
}
