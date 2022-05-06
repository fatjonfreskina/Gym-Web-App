package resource.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import resource.Person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Java Bean used to represent the general information such as prices, trainer, lectures per
 * week for a given course
 *
 * @author Francesco Caldivezzi
 * @author Andrea Pasin
 */
public class PricesView {
    private final Integer courseEditionId;
    private final String courseName;
    private final Integer duration;
    private final float cost;
    private final Date min;
    private final Date max;
    private final Integer lecturesPerWeek;
    private final List<String> trainers;

    /**
     * Constructor for this class
     * @param courseEditionId  the course edition id
     * @param courseName  the course name
     * @param duration  the duration
     * @param cost  the price
     * @param min  the first lecture date
     * @param max  the last lecture date
     * @param lecturesPerWeek  the number of lectures per week
     */
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

    /**
     * Constructor for this class
     * @param courseEditionId  the course edition id
     * @param courseName  the course name
     * @param duration  the duration
     * @param cost  the price
     * @param min  the first lecture date
     * @param max  the last lecture date
     * @param trainers the list of trainers for the given course
     * @param lecturesPerWeek  the number of lectures per week
     */
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

    /**
     * Gets the course name
     * @return  the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets the duration
     * @return  the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Gets the cost
     * @return  the cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * Gets the start date
     * @return  the start date
     */
    public Date getMin() {
        return min;
    }

    /**
     * Gets the last date
     * @return  the last date
     */
    public Date getMax() {
        return max;
    }

    /**
     * Gets the course edition id
     * @return  the course edition id
     */
    public Integer getCourseEditionId() {
        return courseEditionId;
    }

    /**
     * Gets the weekly number of lectures
     * @return  the weekly number of lectures
     */
    public Integer getLecturesPerWeek() {
        return lecturesPerWeek;
    }

    /**
     * Gets the trainers for the given course
     * @return  the trainers for the given course
     */
    public List<String> getTrainers(){
        return trainers;
    }



}
