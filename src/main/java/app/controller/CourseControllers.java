package app.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.model.Course;

public class CourseControllers {
  public static int createCourse(String code, String name){
    try {
      int rowAffected = Controllers.noresQuery(Queries.insertCourse(code, name));
      System.out.println("Inserted " + rowAffected + " row/s for course " + code);
      return 0;
      
    } catch (SQLException e) {
      if(e.getErrorCode() == 19){
        System.out.println("Course code: " + code + " already exists");
        return 1;
      } else {
        System.out.println("Unexpected Error: " + e);
        return -1;
      }
      
    } catch (Exception e) {
      System.out.println("Creating Course Failed: " + e);
      return -1;
    }
  }


  public static int modifyCourse(String code, String name){
    try {
      int rowAffected = Controllers.noresQuery(Queries.updateCourse(code, name));
      System.out.println("Updated " + rowAffected + " row/s for course " + code);
      if(rowAffected == 1){
        return 0;
      } else if(rowAffected == 0){
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e) {;
      return -1;
    }
  }
  
  
  public static int removeCourse(String code) {
    try {
      int rowAffected = Controllers.noresQuery(Queries.deleteCourse(code));
      System.out.println("Deleted " + rowAffected + " row/s for courses " + code);
      if(rowAffected == 1){
        return 0;
      } else if(rowAffected == 0){
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e) {
      return -1;
    }
  }
  
  
  public static ArrayList<Course> getAllCourse(){
    try {
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("COURSES"));
      ArrayList<Course> courses = new ArrayList<>();
      while (result.next()) {
        String code = result.getString("COURSE_CODE");
        String desc = result.getString("COURSE_NAME");
        Course course = new Course(code, desc);
        courses.add(course);
      }
      return courses;
      
    } catch (Exception e) {
      System.out.println("Getting Courses Failed: " + e);
      return null;
    }
  }

}
