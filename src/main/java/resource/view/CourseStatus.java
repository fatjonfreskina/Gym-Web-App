package resource.view;

/**
 * Java Bean used to represent a course status
 * @author Harjot Singh
 */
public class CourseStatus {
  private final String courseName;
  private final int courseEdition;
  private final int traineesNumber;
  private final int currentLessonNumber;
  private final int totalLessonsNumber;

  /**
   * Constructor for this class
   * @param courseName  the course name
   * @param courseEdition  the course edition
   * @param traineesNumber  the number of trainees
   * @param currentLessonNumber  the current lesson for this course
   * @param totalLessonsNumber  the total number of lessons for this course
   */
  public CourseStatus(String courseName, int courseEdition, int traineesNumber, int currentLessonNumber, int totalLessonsNumber) {
    this.courseName = courseName;
    this.courseEdition = courseEdition;
    this.traineesNumber = traineesNumber;
    this.currentLessonNumber = currentLessonNumber;
    this.totalLessonsNumber = totalLessonsNumber;
  }

  /**
   * Gets the course name
   * @return  the course name
   */
  public String getCourseName() {
    return courseName;
  }

  /**
   * Gets the course edition
   * @return  the course edition
   */
  public int getCourseEdition() {
    return courseEdition;
  }

  /**
   * Gets the number of trainees
   * @return  the number of trainees
   */
  public int getTraineesNumber() {
    return traineesNumber;
  }

  /**
   * Gets the current lesson number
   * @return the current lesson number
   */
  public int getCurrentLessonNumber() {
    return currentLessonNumber;
  }

  /**
   * Gets the total number of lessons
   * @return  the total number of lessons
   */
  public int getTotalLessonsNumber() {
    return totalLessonsNumber;
  }


  @Override
  public String toString() {
      StringBuilder sb=new StringBuilder();
      sb.append("CourseStatusPair{courseName='");
      sb.append(courseName);
      sb.append("', courseEdition='");
      sb.append(courseEdition);
      sb.append(", traineesNumber=");
      sb.append(traineesNumber);
      sb.append(", currentLessonNumber=");
      sb.append(currentLessonNumber);
      sb.append(", totalLessonsNumber=");
      sb.append(totalLessonsNumber);
      sb.append("}");
      return sb.toString();
  }
}
