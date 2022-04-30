package constants;

import java.text.SimpleDateFormat;

/**
 * This class contains the date and time format constants used in the application
 *
 * @author Riccardo Forzan
 */
public class DateTimeFormats {

    /**
     * String representation of the pattern associated to date
     */
    public static final String dateFormatPattern = "yyyy-MM-dd";

    /**
     * String representation of the pattern associated to time
     */
    public static final String timeFormatPattern = "HH:mm:ss";

    /**
     * Static object used to format dates
     */
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

    /**
     * Static object used to format times
     */
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatPattern);

    /**
     * Convert from java.sql.Date to java.util.Date
     * @param date date object to convert
     * @return java.util.Date object
     */
    public static java.util.Date dateConvert(java.sql.Date date){
        return new java.util.Date(date.getTime());
    }

    /**
     * Convert from java.util.Date to java.sql.Date
     * @param date date object to convert
     * @return java.sql.Date object
     */
    public static java.sql.Date dateConvert(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }

}
