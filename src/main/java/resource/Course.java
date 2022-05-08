package resource;

/**
 * Java Bean representing a course
 *
 * @author Francesco Caldivezzi
 */
public class Course {

    private final String name;
    private final String description;

    /**
     * Constructor for this class
     *
     * @param name        the course name
     * @param description the course's description
     */
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the course name
     *
     * @return the course name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the description of the course
     *
     * @return the description of the course
     */
    public final String getDescription() {
        return description;
    }

}
