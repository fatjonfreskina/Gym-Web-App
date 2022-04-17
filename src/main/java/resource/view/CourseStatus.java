package resource.view;

/**
 * @author Harjot Singh
 */
public class CourseStatus {
  private final String courseName;
  private final int courseEdition;
  private final int traineesNumber;
  private final int currentLessonNumber;
  private final int totalLessonsNumber;

  public CourseStatus(String courseName, int courseEdition, int traineesNumber, int currentLessonNumber, int totalLessonsNumber) {
    this.courseName = courseName;
    this.courseEdition = courseEdition;
    this.traineesNumber = traineesNumber;
    this.currentLessonNumber = currentLessonNumber;
    this.totalLessonsNumber = totalLessonsNumber;
  }

  public String getCourseName() {
    return courseName;
  }

  public int getCourseEdition() {
    return courseEdition;
  }

  public int getTraineesNumber() {
    return traineesNumber;
  }

  public int getCurrentLessonNumber() {
    return currentLessonNumber;
  }

  public int getTotalLessonsNumber() {
    return totalLessonsNumber;
  }

  @Override
  public String toString() {
    return "CourseStatusPair{" +
        "courseName='" + courseName + '\'' +
        ", courseEdition=" + courseEdition +
        ", traineesNumber=" + traineesNumber +
        ", currentLessonNumber=" + currentLessonNumber +
        ", totalLessonsNumber=" + totalLessonsNumber +
        '}';
  }
}
