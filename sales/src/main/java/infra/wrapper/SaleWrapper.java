
package infra.wrapper;

import domain.event.generator.SaleCodeGenerator;
import infra.formatter.DateTimeMongoFormat;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 */
public class SaleWrapper extends AbstractWrapper<JsonObject> {

    public SaleWrapper(JsonObject toConvert) {
        super(toConvert);
    }

    @Override
    public JsonObject toJson() {
        final String saleCode = new SaleCodeGenerator(LocalDateTime.now()).newCode();
        final JsonObject sale = new JsonObject().put("code", saleCode).put("items", toConvert.getJsonArray("items")).put("creationAt", new JsonObject().put("$date", DateTimeMongoFormat.format(LocalDateTime.now())));
        sale.getJsonArray("items").forEach(item -> ((JsonObject) item).put("sale", saleCode));
        return sale;
    }

}
