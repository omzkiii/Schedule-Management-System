package app.controller;

import java.time.LocalTime;

import app.model.Duration;
import app.model.Schedule;

public class Queries {
  // public static String createFacultyTable = "CREATE TABLE FACULTY " +
  //                     "(ID INTEGER PRIMARY KEY," +
  //                     " NAME           TEXT    NOT NULL)"; 

  // public static String createCourseTable = "CREATE TABLE COURSES " +
  //                     "(COURSE_CODE TEXT PRIMARY KEY NOT NULL, " +
  //                     "COURSE_NAME TEXT NOT NULL)";


  // public static String createScheduleTable = "CREATE TABLE SCHEDULES " +
  //                     "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + 
  //                     "ASSIGNED_FACULTY INT NOT NULL, " +
  //                     "DAY TEXT NOT NULL, " +
  //                     "START_TIME TIME NOT NULL, " +
  //                     "END_TIME TIME NOT NULL, " +
  //                     "COURSE TEXT NOT NULL, " +
  //                     "ROOM_ID INT NOT NULL, " +
  //                     "FOREIGN KEY(ASSIGNED_FACULTY) REFERENCES FACULTY(ID), " +
  //                     "FOREIGN KEY(COURSE) REFERENCES COURSES(COURSE_CODE)); ";

  
  // FACULTY QUERIES
  public static String insertFaculty(int id, String name, int maxLoad) {
    return String.format("INSERT INTO FACULTY VALUES (%d, '%s', %d);", id, name, maxLoad);
  }
  
  public static String updateFaculty(int id, String name, int maxLoad) {
    return String.format("UPDATE FACULTY SET NAME = '%s', MAX_LOAD=%d WHERE ID=%d;", name, maxLoad, id);
  }

  public static String updateFaculty(int id, String name) {
    return String.format("UPDATE FACULTY SET NAME = '%s' WHERE ID=%d;", name, id);
  }

  public static String updateFaculty(int id, int maxLoad) {
    return String.format("UPDATE FACULTY SET MAX_LOAD = %d WHERE ID=%d;", maxLoad, id);
  }


  public static String deleteFaculty(int id){
    return String.format("DELETE FROM FACULTY WHERE ID=%d;", id);
  }

  public static String getFaculty(int id){
    return String.format("SELECT * FROM FACULTY WHERE ID=%d;", id);
  }


  // COURSES QUERIES
  public static String insertCourse(String code, String name, int lec, int lab, float hrs, int fac) {
    return String.format("INSERT INTO COURSES VALUES ('%s', '%s', %d, %d, %.2f, %d);", code, name, lec, lab, hrs, fac);
  }

  public static String updateCourse(String code, String name, int lec, int lab, float hrs, int fac) {
    return String.format("UPDATE COURSES SET COURSE_NAME = '%s', LEC_UNITS = %d, LAB_UNITS = %d, HRS_PER_WK = %.2f, ASSIGNED_FACULTY = %d WHERE COURSE_CODE='%s';", name, lec, lab, hrs, fac, code);
  }

  public static String deleteCourse(String code){
    return String.format("DELETE FROM COURSES WHERE COURSE_CODE='%s';", code);
  }

  public static String selectCourseFor(int faculty){
    return String.format("SELECT * FROM COURSES WHERE ASSIGNED_FACULTY='%d';", faculty);
  }




  // SCHEDULES QUERIES
  public static String insertSchedule(Schedule schedule) {
    int assigned_faculty = schedule.getFacultyId();
    String day = schedule.getDay();
    Duration dur = schedule.getDuration();
    LocalTime start = dur.getStart();
    LocalTime end = dur.getEnd();
    int room_id = schedule.getRoomId();
    String course = schedule.getCourseCode();
    return String.format("INSERT INTO SCHEDULES " + 
      "(ASSIGNED_FACULTY, DAY, START_TIME, END_TIME, ROOM_ID, COURSE) VALUES ('%d', '%s', '%s', '%s', '%d', '%s');", 
      assigned_faculty, day, start, end, room_id, course);
  }

  public static String deleteSchedule(Schedule schedule){
    int id = schedule.getId();
    return String.format("DELETE FROM SCHEDULES WHERE ID='%d';", id);
  }


  public static String selectScheduleFor(int faculty, String day){
    return String.format("SELECT * FROM SCHEDULES WHERE ASSIGNED_FACULTY=%d AND DAY='%s';", faculty, day);
  }


  public static String selectScheduleFor(String day, int room){
    return String.format("SELECT * FROM SCHEDULES WHERE ROOM_ID=%d AND DAY='%s';", room, day);
  }


  public static String selectScheduleFor(String day, String course){
    return String.format("SELECT * FROM SCHEDULES WHERE COURSE='%s' AND DAY='%s';", course, day);
  }

  public static String selectScheduleFor(String course){
    return String.format("SELECT * FROM SCHEDULES WHERE COURSE='%s'", course);
  }


  
  // SELECT ALL QUERY
  public static String selectAllFrom(String table){
    String query = "SELECT * FROM " + table + ";";
    return query;
  }


  // TABLE QUERIES
  static String selectFaculty =  "SELECT * FROM FACULTY;";

  public static String dropTable = "DROP TABLE SCHEDULES;";
}
