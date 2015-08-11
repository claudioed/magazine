
package infra.wrapper;

import domain.event.generator.SaleCodeGenerator;
import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 */
public class GatheringWrapper extends AbstractWrapper<JsonObject> {

    public GatheringWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    @Override
    public JsonObject toJson() {
        final String gatheringCode = new SaleCodeGenerator(LocalDateTime.now()).newCode();
        final JsonObject gathering = new JsonObject().put("code", gatheringCode).put("items", toConvert.getJsonArray("items")).put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        gathering.getJsonArray("items").forEach(item -> ((JsonObject) item).put("gathering", gatheringCode));
        return gathering;
    }

}
