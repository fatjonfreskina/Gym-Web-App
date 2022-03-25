package resource;

public class Teaches
{
    private int courseEdition;
    private String courseName;
    private String trainer;

    public Teaches(int courseEdition, String courseName, String trainer)
    {
        this.courseEdition = courseEdition;
        this.courseName = courseName;
        this.trainer = trainer;
    }
    public void setCourseEdition(int courseEdition)
    {
        this.courseEdition = courseEdition;
    }
    public void  setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
    public void  setTrainer(String trainer)
    {
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
