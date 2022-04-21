package constants;

import java.text.SimpleDateFormat;

/**
 * This class contains the date and time format constants used in the application
 */
public class DateTimeFormats {

    public static final String dateFormatPattern = "yyyy-MM-dd";
    public static final String timeFormatPattern = "HH:mm:ss";

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
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
