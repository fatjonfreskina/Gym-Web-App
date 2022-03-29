package resource;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class LectureTimeSlot
{
    private final String roomName;
    private final Date date;
    private final Time startTime;
    private final int courseEditionId;
    private final String courseName;
    private final String substitution;

    public LectureTimeSlot(String roomName, Date date, Time startTime, int courseEditionId, String courseName, String substitution)
    {
        this.roomName = roomName;
        this.date = date;
        this.startTime = startTime;
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.substitution = substitution;
    }


    public String getRoomName(){ return roomName; }
    public Date getDate(){ return date; }
    public Time getStartTime(){ return startTime; }
    public int getCourseEditionId(){ return courseEditionId; }
    public String getCourseName() { return  courseName; }
    public String getSubstitution() { return getSubstitution(); }

}
