package app.controller;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;

import app.model.Schedule;

public class ScheduleController {
  public static ArrayList<Schedule> getAllSchedule(){
    try {
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("SCHEDULES"));
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START"));
        LocalTime end = LocalTime.parse(result.getString("END"));
        int room_id = result.getInt("ROOM_ID");
        int course = result.getInt("COURSE");
        Schedule schedule = new Schedule(id, assigned_faculty, day, start, end, course, room_id);
        schedules.add(schedule);
      }
      return schedules;
    } catch (Exception e) {
      System.out.println("Get All Schedules Failed: " + e.getMessage());
      return null;

    }
  }
  
}
