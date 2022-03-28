package resource;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class LectureTimeSlot
{
    private String roomName;
    private Date date;
    private Time startTime;
    private int courseEditionId;
    private String courseName;
    private String substitution;

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
    public void setRoomName(String roomName){ this.roomName = roomName; }
    public void setDate(Date date) { this.date = date; }
    public void setStartTime(Time startTime){ this.startTime = startTime; }
    public void setCourseEditionId(int courseEditionId){ this.courseEditionId = courseEditionId; }
    public void setCourseName(String courseName){ this.courseName = courseName; }
    public void setSubstitution(String substitution){ this.substitution = substitution; }
}
