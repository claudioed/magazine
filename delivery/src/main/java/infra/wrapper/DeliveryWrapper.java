package infra.wrapper;

import domain.event.barcode.PlainBarcode;
import domain.event.generator.DeliveryCodeGenerator;
import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira on 10/08/15.
 */
public class DeliveryWrapper extends AbstractWrapper<JsonObject> {

    public DeliveryWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    @Override
    public JsonObject toJson() {
        JsonObject validJson = new JsonObject();
        toConvert.getJsonArray("items").forEach(item -> {
            JsonObject jsonItem = (JsonObject) item;
            jsonItem.put("barcode", PlainBarcode.newPlainBarcode(jsonItem.getString("plainBarcode")).toJson());
        });
        validJson.put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        validJson.put("code",new DeliveryCodeGenerator(LocalDateTime.now()).newCode());
        validJson.put("items",toConvert.getJsonArray("items"));
        return validJson;
    }
    
}
