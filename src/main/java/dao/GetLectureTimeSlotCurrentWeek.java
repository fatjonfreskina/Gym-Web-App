package dao;

import resource.Person;

import java.sql.*;

public class GetLectureTimeSlotCurrentWeek {
    private final String statement = "SELECT * FROM lecturetimeslot WHERE date >= ? AND date <= ?";

}
