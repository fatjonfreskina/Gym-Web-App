package resource.view;

import com.google.gson.Gson;
import resource.LectureTimeSlot;

import java.sql.Time;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author Alberto Campeol
 */
public class WeeklyCalendar {
    private List<LectureTimeSlot>[][] output;
    private final int sizeX = 7;
    private final int sizeY = 7;

    public WeeklyCalendar() {
        output = new List[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                output[i][j] = new ArrayList<>();
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public List<LectureTimeSlot> getTimeSlot(int i, int j) {
        return output[i][j];
    }

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

    public List<LectureTimeSlot>[][] getOutput() {
        return output;
    }

    public List<LectureTimeSlot>[][] getWeeklyCalendar() {
        return output;
    }

    public String toGson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
