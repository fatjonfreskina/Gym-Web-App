package resource;

import java.sql.Date;
import java.sql.Time;

public class LectureTimeSlot {
  private final String roomName;
  private final Date date;
  private final Time startTime;
  private final int courseEditionId;
  private final String courseName;
  private final String substitution;

  public LectureTimeSlot(String roomName, Date date, Time startTime, int courseEditionId, String courseName, String substitution) {
    this.roomName = roomName;
    this.date = date;
    this.startTime = startTime;
    this.courseEditionId = courseEditionId;
    this.courseName = courseName;
    this.substitution = substitution;
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
    return "LectureTimeSlot{" +
        "roomName='" + roomName + '\'' +
        ", date=" + date +
        ", startTime=" + startTime +
        ", courseEditionId=" + courseEditionId +
        ", courseName='" + courseName + '\'' +
        ", substitution='" + substitution + '\'' +
        '}';
  }
}
