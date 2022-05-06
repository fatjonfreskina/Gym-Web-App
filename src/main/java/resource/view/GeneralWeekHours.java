package resource.view;

import java.sql.Time;

/**
 * Java Bean representing a general calendar in a week
 * @author Francesco Caldivezzi
 */
public class GeneralWeekHours
{
    private final String day;
    private final Time startTime;
    private final Integer courseEditionId;

    /**
     * Constructor for this class
     * @param day  the day
     * @param startTime  the start time
     * @param courseEditionId  the course edition id
     */
    public GeneralWeekHours(String day,Time startTime, Integer courseEditionId)
    {
        this.day = day;
        this.startTime = startTime;
        this.courseEditionId = courseEditionId;
    }

    /**
     * Gets the start time
     * @return  the start time
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Gets the course edition id
     * @return  the course edition id
     */
    public Integer getCourseEditionId() {
        return courseEditionId;
    }

    /**
     * Gets the day
     * @return  the day
     */
    public String getDay() {
        return day;
    }
}