package resource;

import java.util.Objects;

/**
 * This Java Bean contains all the info about a course edition
 *
 * @author Tumiati Riccardp
 * @author Francesco Caldivezzi
 */

public class CourseEdition {

    private final int ID;
    private final String courseName;

    /**
     * Default constructor, with all parameters
     *
     * @param ID         the version of the course edition
     * @param courseName the name of the course
     */
    public CourseEdition(int ID, String courseName) {
        this.ID = ID;
        this.courseName = courseName;
    }

    public final int getId() {
        return ID;
    }

    public final String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "CourseEdition{" +
            "ID=" + ID +
            ", courseName='" + courseName + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEdition that = (CourseEdition) o;
        return ID == that.ID && Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, courseName);
    }
}
