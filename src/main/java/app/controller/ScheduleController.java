package app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import app.model.Duration;
import app.model.Schedule;

import app.utils.ScheduleChecker;


public class ScheduleController {
  public static int createSchedule(Schedule schedule){
    /*
     returns the following:
        0 if update success
        1 if faculty is already scheduled for given time
        2 if room is occupied for the given time
        3 if course is already scheduled for given time
        -1 for other exceptions
     */  

    if(ScheduleChecker.isFacultyScheduled(schedule)){
      return 1;
    }

    if(ScheduleChecker.isRoomOccupied(schedule)){
      return 2;
    }

    if(ScheduleChecker.isCourseScheduled(schedule)){
      return 3;
    }


    try {
      int rowAffected = Controllers.noresQuery(Queries.insertSchedule(schedule));
      System.out.println("Inserted " + rowAffected + " row/s for schedule " + schedule.getId());
      return 0;
    } catch (SQLException e) {
      if(e.getErrorCode() == 19){
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


  public static ArrayList<Schedule> getFacultySchedule(int faculty, String d){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
    ResultSet result = Controllers.resQuery(Queries.selectScheduleFor(faculty, d), c);
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START_TIME"));
        LocalTime end = LocalTime.parse(result.getString("END_TIME"));
        Duration dur = new Duration(start, end);
        int room_id = result.getInt("ROOM_ID");
        String course = result.getString("COURSE");
        Schedule schedule = new Schedule(id, assigned_faculty, day, dur, course, room_id);
        schedules.add(schedule);
      }
      return schedules;

    } catch (Exception e) {
      System.out.println("Get All Schedules Failed: " + e.getMessage());
      return null;

    }
  }


  public static ArrayList<Schedule> getRoomSchedule(String d, int room){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
    ResultSet result = Controllers.resQuery(Queries.selectScheduleFor(d, room), c);
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START_TIME"));
        LocalTime end = LocalTime.parse(result.getString("END_TIME"));
        Duration dur = new Duration(start, end);
        int room_id = result.getInt("ROOM_ID");
        String course = result.getString("COURSE");
        Schedule schedule = new Schedule(id, assigned_faculty, day, dur, course, room_id);
        schedules.add(schedule);
      }
      return schedules;

    } catch (Exception e) {
      System.out.println("Get All Schedules Failed: " + e.getMessage());
      return null;

    }
  }

  public static ArrayList<Schedule> getCourseSchedule(String d, String cor){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
    ResultSet result = Controllers.resQuery(Queries.selectScheduleFor(d, cor), c);
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START_TIME"));
        LocalTime end = LocalTime.parse(result.getString("END_TIME"));
        Duration dur = new Duration(start, end);
        int room_id = result.getInt("ROOM_ID");
        String course = result.getString("COURSE");
        Schedule schedule = new Schedule(id, assigned_faculty, day, dur, course, room_id);
        schedules.add(schedule);
      }
      return schedules;

    } catch (Exception e) {
      System.out.println("Get All Schedules Failed: " + e.getMessage());
      return null;

    }
  }


  public static ArrayList<Schedule> getAllSchedule(){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("SCHEDULES"), c);
      ArrayList<Schedule> schedules = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        int assigned_faculty = result.getInt("ASSIGNED_FACULTY");
        String day = result.getString("DAY");
        LocalTime start = LocalTime.parse(result.getString("START_TIME"));
        LocalTime end = LocalTime.parse(result.getString("END_TIME"));
        Duration dur = new Duration(start, end);
        int room_id = result.getInt("ROOM_ID");
        String course = result.getString("COURSE");
        Schedule schedule = new Schedule(id, assigned_faculty, day, dur, course, room_id);
        schedules.add(schedule);
      }
      return schedules;
    } catch (Exception e) {
      System.out.println("Get All Schedules Failed: " + e.getMessage());
      return null;

    }
  }
  
}
