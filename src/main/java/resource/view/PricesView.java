package resource.view;

import java.sql.Date;

public class PricesView {
    private final Integer courseEditionId;
    private final String courseName;
    private final Integer duration;
    private final float cost;
    private final Date min;
    private final Date max;


    public PricesView(Integer courseEditionId,String courseName, Integer duration, float cost, Date min, Date max)
    {
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.duration = duration;

        this.cost = cost;
        this.min = min;
        this.max = max;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getDuration() {
        return duration;
    }

    public float getCost() {
        return cost;
    }

    public Date getMin() {
        return min;
    }

    public Date getMax() {
        return max;
    }

    public Integer getCourseEditionId() {
        return courseEditionId;
    }
}
