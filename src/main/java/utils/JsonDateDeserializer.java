package utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import constants.Constants;

import java.lang.reflect.Type;
import java.sql.Date;

/**
 * @author Harjot Singh
 */
public class JsonDateDeserializer implements JsonDeserializer<Date> {
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String dateAsString = json.getAsJsonPrimitive().getAsString();
    try {
      return Date.valueOf(dateAsString);
    } catch (IllegalArgumentException e) {
      throw new JsonParseException(Constants.UNPARSABLE_DATE + "\"" + dateAsString + "\".");
    }
  }
}