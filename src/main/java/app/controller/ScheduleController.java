package app.controller;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;

import app.model.Schedule;

public class ScheduleController {
  public static int createSchedule(int assigned_faculty, String day, LocalTime start, LocalTime end, int room_id, String course){
    try {
    } catch (Exception e) {
      // TODO: handle exception
    }
      return 1;
  }

  public static ArrayList<Schedule> getAllSchedule(){
    try {
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("SCHEDULES"));
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START_TIME"));
        LocalTime end = LocalTime.parse(result.getString("END_TIME"));
        int room_id = result.getInt("ROOM_ID");
        String course = result.getString("COURSE");
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
