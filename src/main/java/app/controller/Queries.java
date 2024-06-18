package app.controller;
public class Queries {
  // static String createFacultyTable = "CREATE TABLE FACULTY " +
  //                     "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
  //                     " NAME           TEXT    NOT NULL)"; 

  // static String createCourseTable = "CREATE TABLE COURSES " +
  //                     "(COURSE_CODE TEXT PRIMARY KEY NOT NULL, " +
  //                     "COURSE_NAME TEXT NOT NULL)";


  // static String createScheduleTable = "CREATE TABLE SCHEDULE " +
  //                     "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + 
  //                     "";
  
  public static String insertFaculty(int id, String name) {
    return String.format("INSERT INTO FACULTY VALUES (%d, '%s');", id, name);
  }
  
  public static String updateFaculty(int id, String name) {
    return String.format("UPDATE FACULTY SET NAME = '%s' WHERE ID=%d;", name, id);
  }

  public static String deleteFaculty(int id){
    return String.format("DELETE FROM FACULTY WHERE ID=%d;", id);
  }

  public static String selectAllFrom(String table){
    String query = "SELECT * FROM " + table + ";";
    return query;
  }

  

  static String selectFaculty =  "SELECT * FROM FACULTY;";

  static String dropTable = "DROP TABLE FACULTY;";
}
