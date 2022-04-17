package resource.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import resource.Person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PricesView {
    private final Integer courseEditionId;
    private final String courseName;
    private final Integer duration;
    private final float cost;
    private final Date min;
    private final Date max;
    private final Integer lecturesPerWeek;
    private final List<String> trainers;

    public PricesView(Integer courseEditionId,String courseName, Integer duration, float cost, Date min, Date max, Float lecturesPerWeek)
    {
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.duration = duration;

        this.cost = cost;
        this.min = min;
        this.max = max;

        this.trainers=null;
        this.lecturesPerWeek=Math.round(lecturesPerWeek);
    }

    public PricesView(Integer courseEditionId, String courseName, Integer duration, float cost, Date min, Date max, List<Person> trainers, Float lecturesPerWeek)
    {
        this.courseEditionId = courseEditionId;
        this.courseName = courseName;
        this.duration = duration;

        this.cost = cost;
        this.min = min;
        this.max = max;

        this.trainers=new ArrayList<>();
        for(int i=0;i<trainers.size();i++){
            if(i==trainers.size()-1){
                this.trainers.add(trainers.get(i).getName()+" "+trainers.get(i).getSurname());
            }
            else{
                this.trainers.add(trainers.get(i).getName()+" "+trainers.get(i).getSurname()+",");
            }

        }
        this.lecturesPerWeek=Math.round(lecturesPerWeek);
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

    public Integer getLecturesPerWeek() {
        return lecturesPerWeek;
    }

    public List<String> getTrainers(){
        return trainers;
    }



}
