package utils;


import com.google.gson.*;
import constants.Constants;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * Class to handle serialization and deserialization of {@code java.sql.Time} objects to/from JSON format.
 *
 * @author Harjot Singh, Marco Alessio
 */
public class TimeJsonAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {

    /**
     * Deserialize a {@code Time} object from JSON data.
     *
     * @param element The JSON data to deserialize.
     * @param type    The type of object to deserialize to ({@code java.sql.Time}).
     * @param context The deserialization context.
     * @return A {@code Time} object, filled with the time given by JSON data.
     * @throws JsonParseException If the given JSON data could not be deserialized to a valid {@code Time} object.
     */
    @Override
    public Time deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        final String timeString = element.getAsString();

        try {
            return Time.valueOf(timeString);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(Constants.UNPARSABLE_TIME + "\"" + timeString + "\".");
        }
    }

    /**
     * Serialize a {@code Time} object to JSON format.
     *
     * @param time    The time to serialize.
     * @param type    The type of object to serialize ({@code java.sql.Time}).
     * @param context The serialization context.
     * @return A {@code JsonElement} object corresponding to the specified time object.
     */
    @Override
    public JsonElement serialize(Time time, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(time.toString());
    }

}