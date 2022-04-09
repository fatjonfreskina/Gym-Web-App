package resource;

/**
 * This Java Bean contains all the info about what course a trainer teach
 *
 * @author Francesco Caldivezzi
 */
public class Teaches {
  private final int courseEdition;
  private final String courseName;
  private final String trainer;

  /**
   * Default constructor, with all parameters
   *
   * @param courseEdition the edition of the course that the trainer will teach
   * @param courseName    the type of the course that the trainer will teach
   * @param trainer       the trainer that will teach such course in for this edition
   */
  public Teaches(int courseEdition, String courseName, String trainer) {
    this.courseEdition = courseEdition;
    this.courseName = courseName;
    this.trainer = trainer;
  }

  public final int getCourseEdition() {
    return courseEdition;
  }

  public final String getCourseName() {
    return courseName;
  }

  public final String getTrainer() {
    return trainer;
  }

  @Override
  public String toString() {
    return "Teaches{" +
        "courseEdition=" + courseEdition +
        ", courseName='" + courseName + '\'' +
        ", trainer='" + trainer + '\'' +
        '}';
  }
}
