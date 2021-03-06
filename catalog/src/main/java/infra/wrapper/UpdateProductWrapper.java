package infra.wrapper;


import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class UpdateProductWrapper extends AbstractWrapper<JsonObject>{

    private final String id;

    public UpdateProductWrapper(JsonObject toConvert, String id) {
        super(toConvert);
        this.id = id;
    }

    public JsonObject toJson() {
        JsonObject validJson = new JsonObject();
        validJson.put("id",id);
        validJson.put("updatedAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        validJson.put("name",toConvert.getString("name"));
        validJson.put("plainBarcode",toConvert.getString("plainBarcode"));
        validJson.put("price",Double.valueOf(toConvert.getString("price")));
        return validJson;
    }

}
