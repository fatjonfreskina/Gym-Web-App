package utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Time;

public class JsonTimeSerializer implements JsonSerializer<Time> {
  @Override
  public JsonElement serialize(Time time, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(time.toString());
  }
}