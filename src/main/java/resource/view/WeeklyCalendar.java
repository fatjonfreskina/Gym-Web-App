package resource.view;

import com.google.gson.Gson;
import resource.LectureTimeSlot;

import java.sql.Time;
import java.util.*;
import java.util.function.Consumer;

/**
 * Java Bean representing a weekly calendar
 * @author Alberto Campeol
 */
public class WeeklyCalendar {
    private List<LectureTimeSlot>[][] output;
    private final int sizeX = 7;
    private final int sizeY = 7;

    /**
     * Constructor for this class
     */
    public WeeklyCalendar() {
        output = new List[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                output[i][j] = new ArrayList<>();
            }
        }
    }

    /**
     * Gets the size X for this calendar
     * @return  the size X
     */
    public int getSizeX() {
        return sizeX;
    }
    /**
     * Gets the size Y for this calendar
     * @return  the size Y
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Gets the lectures in a given slot
     * @param i  the X position
     * @param j  the Y position
     * @return  the lectures in a given slot
     */
    public List<LectureTimeSlot> getTimeSlot(int i, int j) {
        return output[i][j];
    }

    /**
     * Adds a lecture in a given slot
     * @param slot  the slot to which the lecture must be added
     */
    public void addSlot(LectureTimeSlot slot) {
        Time startTime = slot.getStartTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(slot.getDate());
        long s = startTime.getTime();
        int hour_index = (int) ((startTime.getTime() - (1000 * 3600 * 8)) / (7200 * 1000));
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            output[hour_index][0].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            output[hour_index][1].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            output[hour_index][2].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            output[hour_index][3].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            output[hour_index][4].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            output[hour_index][5].add(slot);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            output[hour_index][6].add(slot);
        }
    }

    /**
     * Gets the all the lectures
     * @return  the lectures
     */
    public List<LectureTimeSlot>[][] getOutput() {
        return output;
    }
    /**
     * Gets the all the lectures
     * @return  the lectures
     */
    public List<LectureTimeSlot>[][] getWeeklyCalendar() {
        return output;
    }

    /**
     * Gets the calendar in json format
     * @return  the calendar in json format
     */
    public String toGson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
