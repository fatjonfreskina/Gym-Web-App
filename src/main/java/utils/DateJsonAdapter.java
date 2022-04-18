package utils;

import com.google.gson.*;
import constants.Constants;

import java.lang.reflect.Type;
import java.sql.Date;


/**
 * Class to handle serialization and deserialization of {@code java.sql.Date} objects to/from JSON format.
 *
 * @author Harjot Singh, Marco Alessio
 */
public class DateJsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date>
{
    /**
     * Deserialize a {@code Date} object from JSON data.
     * @param element The JSON data to deserialize.
     * @param type The type of object to deserialize to ({@code java.sql.Date}).
     * @param context The deserialization context.
     * @return A {@code Date} object, filled with the date given by JSON data.
     * @throws JsonParseException If the given JSON data could not be deserialized to a valid {@code Date} object.
     */
    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException
    {
        final String dateString = element.getAsString();

        try
        {
            return Date.valueOf(dateString);
        }
        catch (IllegalArgumentException e)
        {
            throw new JsonParseException(Constants.UNPARSABLE_DATE + "\"" + dateString + "\".");
        }
    }


    /**
     * Serialize a {@code Date} object to JSON format.
     * @param date The date to serialize.
     * @param type The type of object to serialize ({@code java.sql.Date}).
     * @param context The serialization context.
     * @return A {@code JsonElement} object corresponding to the specified date object.
     */
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext context)
    {
        return new JsonPrimitive(date.toString());
    }
}