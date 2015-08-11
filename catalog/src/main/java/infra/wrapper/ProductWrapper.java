package infra.wrapper;


import domain.event.barcode.PlainBarcode;
import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class ProductWrapper extends AbstractWrapper<JsonObject>{

    public ProductWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    public JsonObject toJson() {
        JsonObject validJson = new JsonObject();
        JsonObject barcode = PlainBarcode.newPlainBarcode(toConvert.getString("plainBarcode")).toJson();
        validJson.put("barcode",barcode);
        validJson.put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        validJson.put("name",toConvert.getString("name"));
        validJson.put("price",toConvert.getDouble("price"));
        return validJson;
    }

}
