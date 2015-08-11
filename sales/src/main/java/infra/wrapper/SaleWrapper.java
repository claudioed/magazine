package infra.wrapper;

import io.vertx.core.json.JsonObject;

/**
 */
public class SaleWrapper extends AbstractWrapper<JsonObject>{

    public SaleWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    @Override
    public JsonObject toJson() {
        return null;
    }

}
