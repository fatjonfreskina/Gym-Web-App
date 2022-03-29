package resource;

public class CourseEdition {
    private final int ID;
    private final String courseName;

    public CourseEdition(int ID, String courseName){
        this.ID = ID;
        this.courseName = courseName;
    }

    public final int getID(){
        return ID;
    }

    public final String getCourseName(){
        return courseName;
    }
}
