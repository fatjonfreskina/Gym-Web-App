package resource;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.sql.Time;

/**
 * Java Bean representing a lecture for a course on a given date and time
 *
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

    /**
     * Constructor for this class
     *
     * @param roomName        the room
     * @param date            the date when the lecture is held
     * @param startTime       the starting time of the lecture
     * @param courseEditionId the course edition associated to this lesson
     * @param courseName      the course name
     * @param substitution    the trainer who substitutes the principal trainer
     */
    public LectureTimeSlot(String roomName, Date date, Time startTime, Integer courseEditionId, String courseName, String substitution) {
        this.roomName = roomName;
        this.date = date;
        this.startTime = startTime;
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.substitution = substitution;
    }

    /**
     * Constructor for this class
     *
     * @param courseEditionId the course edition associated to this lesson
     * @param courseName      the course name
     */
    public LectureTimeSlot(int courseEditionId, String courseName) {
        this.roomName = null;
        this.date = null;
        this.startTime = null;
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.substitution = null;
    }

    /**
     * Constructor for this class
     *
     * @param l the lecture
     */
    public LectureTimeSlot(LectureTimeSlot l) {
        this.roomName = l.roomName;
        this.date = l.date;
        this.startTime = l.startTime;
        this.courseEditionId = l.courseEditionId;
        this.courseName = l.courseName;
        this.substitution = l.substitution;
    }

    /**
     * Gets the room
     *
     * @return the room
     */
    public final String getRoomName() {
        return roomName;
    }

    /**
     * Gets the date
     *
     * @return the date
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Gets the start time
     *
     * @return the start time
     */
    public final Time getStartTime() {
        return startTime;
    }

    /**
     * Gets the course edition
     *
     * @return the course edition
     */
    public final int getCourseEditionId() {
        return courseEditionId;
    }

    /**
     * Gets the course name
     *
     * @return the course name
     */
    public final String getCourseName() {
        return courseName;
    }

    /**
     * Gets the substitute
     *
     * @return the substitute
     */
    public final String getSubstitution() {
        return substitution;
    }

    /**
     *  Gets a lecture time slot in String format
     *
     * @return lecture time slot
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LectureTimeSlot{roomName=");
        sb.append(roomName);
        sb.append(", date='");
        sb.append(date);
        sb.append(", startTime='");
        sb.append(startTime);
        sb.append(", courseEditionId='");
        sb.append(courseEditionId);
        sb.append(", courseName='");
        sb.append(courseName);
        sb.append(", substitution='");
        sb.append(substitution);
        sb.append("'}");
        return sb.toString();
    }
}

