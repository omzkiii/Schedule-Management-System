package app.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import app.model.Schedule;

public class ScheduleController {
  public static int createSchedule(Schedule schedule){
    try {
      int rowAffected = Controllers.noresQuery(Queries.insertSchedule(schedule));
      System.out.println("Inserted " + rowAffected + " row/s for schedule " + schedule.getId());
      return 0;
    } catch (SQLException e) {
      if(e.getErrorCode() == 19){
        // TODO: Add handle conflicting schedule class or methods
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e){
      System.out.println("Unexpected error");
      return -1;
    }
  }

  public static int removeSchedule(Schedule schedule) {
    try {
      int rowAffected = Controllers.noresQuery(Queries.deleteSchedule(schedule));
      System.out.println("Deleted " +  rowAffected + " row/s for schedule " + schedule.getId());
      if(rowAffected == 1){
        return 0;
      } else if(rowAffected == 0){
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception: " + e);
      return -1;
    }
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
