package infra.wrapper;


import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public class CustomerPreferenceWrapper extends AbstractWrapper<JsonObject>{

    public CustomerPreferenceWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    public JsonObject toJson() {
        JsonObject validJson = new JsonObject();
        validJson.put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        validJson.put("name",toConvert.getString("name"));
        validJson.put("email",toConvert.getString("email"));
        return validJson;
    }

}
