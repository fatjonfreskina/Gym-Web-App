package resource;

public class Course {

    private final String name;
    private final String description;

    public Course(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public final String getName(){ return name; }

    public final String getDescription() { return description; }

}
