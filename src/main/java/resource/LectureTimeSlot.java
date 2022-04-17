package resource;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Francesco Caldivezzi
 * @author Harjot Singh
 */
public class LectureTimeSlot {
  @Expose
  private final String roomName;
  @Expose
  private final Date date;
  @Expose
  private final Time startTime;
  @Expose
  private final Integer courseEditionId;
  @Expose
  private final String courseName;
  @Expose
  private final String substitution;

  public LectureTimeSlot(String roomName, Date date, Time startTime, Integer courseEditionId, String courseName, String substitution) {
    this.roomName = roomName;
    this.date = date;
    this.startTime = startTime;
    this.courseEditionId = courseEditionId;
    this.courseName = courseName;
    this.substitution = substitution;
  }

  public LectureTimeSlot(int courseEditionId, String courseName) {
    this.roomName = null;
    this.date = null;
    this.startTime = null;
    this.courseEditionId = courseEditionId;
    this.courseName = courseName;
    this.substitution = null;
  }

  public LectureTimeSlot(LectureTimeSlot l) {
    this.roomName = l.roomName;
    this.date = l.date;
    this.startTime = l.startTime;
    this.courseEditionId = l.courseEditionId;
    this.courseName = l.courseName;
    this.substitution = l.substitution;
  }

  public final String getRoomName() {
    return roomName;
  }

  public final Date getDate() {
    return date;
  }

  public final Time getStartTime() {
    return startTime;
  }

  public final int getCourseEditionId() {
    return courseEditionId;
  }

  public final String getCourseName() {
    return courseName;
  }

  public final String getSubstitution() {
    return substitution;
  }

  @Override
  public String toString() {
    //return new Gson().toJson(this,LectureTimeSlot.class);
    return "LectureTimeSlot{" +
        "roomName='" + roomName + '\'' +
        ", date='" + date + '\'' +
        ", startTime='" + startTime + '\'' +
        ", courseEditionId=" + courseEditionId +
        ", courseName='" + courseName + '\'' +
        ", substitution='" + substitution + '\'' +
        '}';
  }
}
