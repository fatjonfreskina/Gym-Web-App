package resource;

public class Teaches
{
    private final int courseEdition;
    private final String courseName;
    private final String trainer;

    public Teaches(int courseEdition, String courseName, String trainer)
    {
        this.courseEdition = courseEdition;
        this.courseName = courseName;
        this.trainer = trainer;
    }

    public int getCourseEdition()
    {
        return courseEdition;
    }
    public String getCourseName()
    {
        return  courseName;
    }
    public String getTrainer()
    {
        return trainer;
    }
}
