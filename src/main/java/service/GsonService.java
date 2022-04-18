package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import constants.Constants;
import constants.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import utils.DateJsonAdapter;
import utils.TimeJsonAdapter;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Harjot Singh
 */
public class GsonService {

  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.service.gson: ";
  private final Gson gson;

  public GsonService() {
    gson = new GsonBuilder()
        //.setDateFormat("yyyy-MM-dd")
        .registerTypeAdapter(Time.class, new TimeJsonAdapter()).registerTypeAdapter(Date.class, new DateJsonAdapter()).create();
  }

  public Reservation getReservationFromString(String string) throws WrongDateFormat, NotAcceptableMissingFields, ParsingError, WrongTimeFormat {
    try {
      logger.debug(loggerClass + "Reservation as String: " + string);
      Reservation reservation = gson.fromJson(string, Reservation.class);
      if (reservation == null) {
        logger.debug(loggerClass + "Reservation is null");
        throw new NotAcceptableMissingFields();
      }
      if (reservation.getTrainee() == null || reservation.getRoom() == null || reservation.getLectureDate() == null || reservation.getLectureStartTime() == null) {
        logger.debug(loggerClass + "Reservation has something to null (missing field):" + reservation);
        throw new NotAcceptableMissingFields();
      }
      logger.debug(loggerClass + "Valid Reservation Deserialized: " + reservation);
      return reservation;
    } catch (JsonParseException | NumberFormatException e) {
      e.printStackTrace();
      if (e.getMessage().contains(Constants.UNPARSABLE_DATE)) throw new WrongDateFormat();
      else if (e.getMessage().contains(Constants.UNPARSABLE_TIME)) throw new WrongTimeFormat();
      else throw new ParsingError();
    }
  }

  public Subscription getSubscriptionFromString(String string) throws NotAcceptableMissingFields, WrongDateFormat, ParsingError, WrongTimeFormat {
    try {
      logger.debug(loggerClass + "Subscription as String: " + string);
      Subscription subscription = gson.fromJson(string, Subscription.class);
      if (subscription == null) {
        logger.debug(loggerClass + "Subscription is null");
        throw new NotAcceptableMissingFields();
      }
      if (subscription.getTrainee() == null || subscription.getCourseName() == null || subscription.getStartDay() == null) {
        logger.debug(loggerClass + "Subscription has something to null (missing field):" + subscription);
        throw new NotAcceptableMissingFields();
      }
      logger.debug(loggerClass + "Valid Subscription Deserialized: " + subscription);
      return subscription;
    } catch (JsonParseException | NumberFormatException e) {
      e.printStackTrace();
      if (e.getMessage().contains(Constants.UNPARSABLE_DATE)) throw new WrongDateFormat();
      else if (e.getMessage().contains(Constants.UNPARSABLE_TIME)) throw new WrongTimeFormat();
      else throw new ParsingError();
    }
  }
}
