package app.controller;
public class Queries {
  public static String createFacultyTable = "CREATE TABLE FACULTY " +
                      "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                      " NAME           TEXT    NOT NULL)"; 

  public static String createCourseTable = "CREATE TABLE COURSES " +
                      "(COURSE_CODE TEXT PRIMARY KEY NOT NULL, " +
                      "COURSE_NAME TEXT NOT NULL)";


  public static String createScheduleTable = "CREATE TABLE SCHEDULES " +
                      "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                      "ASSIGNED_FACULTY INT NOT NULL, " +
                      "DAY TEXT NOT NULL, " +
                      "START TIME NOT NULL, " +
                      "END TIME NOT NULL, " +
                      "COURSE TEXT NOT NULL, " +
                      "ROOM_ID INT NOT NULL, " +
                      "FOREIGN KEY(ASSIGNED_FACULTY) REFERENCES FACULTY(ID), " +
                      "FOREIGN KEY(COURSE) REFERENCES COURSES(COURSE_CODE)); ";
  
  // FACULTY QUERIES
  public static String insertFaculty(int id, String name) {
    return String.format("INSERT INTO FACULTY VALUES (%d, '%s');", id, name);
  }
  
  public static String updateFaculty(int id, String name) {
    return String.format("UPDATE FACULTY SET NAME = '%s' WHERE ID=%d;", name, id);
  }

  public static String deleteFaculty(int id){
    return String.format("DELETE FROM FACULTY WHERE ID=%d;", id);
  }


  // COURSES QUERIES
  public static String insertCourse(String code, String name) {
    return String.format("INSERT INTO COURSES VALUES ('%s', '%s');", code, name);
  }

  public static String updateCourse(String code, String name) {
    return String.format("UPDATE FACULTY SET NAME = '%s' WHERE ID=%s;", name, code);
  }

  public static String deleteCourse(String code){
    return String.format("DELETE FROM COURSES WHERE COURSE_CODE='%s';", code);
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
