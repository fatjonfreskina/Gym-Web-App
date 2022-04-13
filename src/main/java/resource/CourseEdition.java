package resource;

/**
 * This Java Bean contains all the info about a course edition
 *
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
}
