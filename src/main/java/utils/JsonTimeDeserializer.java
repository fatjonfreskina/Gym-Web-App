package utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import constants.Constants;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * @author Harjot Singh
 */
public class JsonTimeDeserializer implements JsonDeserializer<Time> {
  public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String timeAsString = json.getAsJsonPrimitive().getAsString();
    try {
      return Time.valueOf(timeAsString);
    } catch (IllegalArgumentException e) {
      throw new JsonParseException(Constants.UNPARSABLE_TIME + "\"" + timeAsString + "\".");
    }
  }
}