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
        0 if creation success
        1 schedule already exist
        2 if adding schedule will exceed course hrs per week
        3 if faculty is already scheduled for given time
        4 if room is occupied for the given time
        -1 for other exceptions
     */
    
     if(ScheduleChecker.willCourseExceedHrs(schedule)){
      return 2;
     }

    if(ScheduleChecker.isFacultyScheduled(schedule)){
      return 3;
    }

    if(ScheduleChecker.isRoomOccupied(schedule)){
      return 4;
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


  public static int modifySchedule(Schedule schedule){
    /*
     returns the following:
        0 if update success
        1 if schedule does not exist
        2 if modifying schedule will exceed course hrs per week
        3 if faculty is already scheduled for modified time
        4 if room is occupied for the modified time
        -1 for other exceptions
     */
    
     if(ScheduleChecker.willCourseExceedHrs(schedule)){
      return 2;
     }

    if(ScheduleChecker.isFacultyScheduled(schedule)){
      return 3;
    }

    if(ScheduleChecker.isRoomOccupied(schedule)){
      return 4;
    }

    try {
      int rowAffected = Controllers.noresQuery(Queries.updateSchedule(schedule));
      System.out.println("Updated " + rowAffected + " row/s for schedule " + schedule.getId());
      if(rowAffected == 1){
        return 0;
      } else if(rowAffected == 0){
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e) {
      System.out.println("Unexpected Exception: " + e.getMessage());
      return -1;
    }

  }


  public static int removeSchedule(Schedule schedule) {
    /*
     returns the following:
        0 if update success
        1 if schedule does not exist
        -1 for other exceptions
     */
    int id = schedule.getId();
    try {
      int rowAffected = Controllers.noresQuery(Queries.deleteSchedule(id));
      System.out.println("Deleted " +  rowAffected + " row/s for schedule " + id);
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


  public static ArrayList<Schedule> getCourseSchedule(String cor){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
    ResultSet result = Controllers.resQuery(Queries.selectScheduleFor(cor), c);
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


  public static ArrayList<Schedule> getTodaySchedule(String today){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")) {
    ResultSet result = Controllers.resQuery(Queries.selectScheduleForThisDay(today), c);
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
      System.out.println("Get All Schedules For This Day Failed: " + e.getMessage());
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
