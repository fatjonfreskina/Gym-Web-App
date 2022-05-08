package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import constants.Constants;
import constants.exceptions.NotAcceptableMissingFields;
import constants.exceptions.ParsingError;
import constants.exceptions.WrongDateFormat;
import constants.exceptions.WrongTimeFormat;
import resource.Reservation;
import resource.Subscription;
import utils.DateJsonAdapter;
import utils.TimeJsonAdapter;

import java.sql.Date;
import java.sql.Time;

/**
 * Service handling json
 *
 * @author Harjot Singh
 */
public class GsonService {

    private final Gson gson;

    /**
     * The constructor
     */
    public GsonService() {
        gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Time.class, new TimeJsonAdapter()).registerTypeAdapter(Date.class, new DateJsonAdapter()).create();
    }

    /**
     * Deserializes a json string to a {@code Reservation}
     * @param string  the reservation as {@code String}
     * @return  the reservation
     * @throws WrongDateFormat if the date format is wrong
     * @throws NotAcceptableMissingFields if there is a missing field
     * @throws ParsingError if there is an error while parsing
     * @throws WrongTimeFormat if the time format is not valid
     */
    public Reservation getReservationFromString(String string) throws WrongDateFormat, NotAcceptableMissingFields, ParsingError, WrongTimeFormat {
        try {
            Reservation reservation = gson.fromJson(string, Reservation.class);
            if (reservation == null) {
                throw new NotAcceptableMissingFields();
            }
            if (reservation.getTrainee() == null || reservation.getRoom() == null || reservation.getLectureDate() == null || reservation.getLectureStartTime() == null) {
                throw new NotAcceptableMissingFields();
            }
            return reservation;
        } catch (JsonParseException | NumberFormatException e) {
            e.printStackTrace();
            if (e.getMessage().contains(Constants.UNPARSABLE_DATE)) throw new WrongDateFormat();
            else if (e.getMessage().contains(Constants.UNPARSABLE_TIME)) throw new WrongTimeFormat();
            else throw new ParsingError();
        }
    }

    /**
     * Deserializes a json string to a {@code Subscription}
     * @param string  the subscription as {@code String}
     * @return  the subscription
     * @throws WrongDateFormat if the date format is wrong
     * @throws NotAcceptableMissingFields if there is a missing field
     * @throws ParsingError if there is an error while parsing
     * @throws WrongTimeFormat if the time format is not valid
     */
    public Subscription getSubscriptionFromString(String string) throws NotAcceptableMissingFields, WrongDateFormat, ParsingError, WrongTimeFormat {
        try {
            Subscription subscription = gson.fromJson(string, Subscription.class);
            if (subscription == null) {
                throw new NotAcceptableMissingFields();
            }
            if (subscription.getTrainee() == null || subscription.getCourseName() == null || subscription.getStartDay() == null) {
                throw new NotAcceptableMissingFields();
            }
            return subscription;
        } catch (JsonParseException | NumberFormatException e) {
            e.printStackTrace();
            if (e.getMessage().contains(Constants.UNPARSABLE_DATE)) throw new WrongDateFormat();
            else if (e.getMessage().contains(Constants.UNPARSABLE_TIME)) throw new WrongTimeFormat();
            else throw new ParsingError();
        }
    }
}
