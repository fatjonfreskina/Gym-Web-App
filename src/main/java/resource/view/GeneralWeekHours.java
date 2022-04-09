package resource.view;

import java.sql.Time;

public class GeneralWeekHours
{
    private final String day;
    private final Time startTime;
    private final Integer courseEditionId;

    public GeneralWeekHours(String day,Time startTime, Integer courseEditionId)
    {
        this.day = day;
        this.startTime = startTime;
        this.courseEditionId = courseEditionId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Integer getCourseEditionId() {
        return courseEditionId;
    }

    public String getDay() {
        return day;
    }
}