package app.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.model.Course;
import app.utils.CourseChecker;

public class CourseControllers {
  public static int createCourse(Course course){
    /*
     * return values:
     * 0 - creation success
     * 1 - course code already exist
     * 2 - faculty max load exceeded
     * -1 - other exception
     */

    if(CourseChecker.willExceedMaxLoad(course)){
      System.out.println("Max Faculty Load Exceeded");
      return 2;
    }

    String code = course.getCode();
    String name = course.getDesc();
    int lec = course.getLecUnits();
    int lab = course .getLabUnits();
    float hrs = course.getHrsPerWeek();
    int fac = course. getFacultyId();


    try {
      int rowAffected = Controllers.noresQuery(Queries.insertCourse(code, name, lec, lab, hrs, fac));
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


  public static int modifyCourse(Course course){
    /*
     * return values:
     * 0 - update success
     * 1 - course code does not exist
     * 2 - faculty max load exceeded
     * -1 - other exception
     */

     if(CourseChecker.willExceedMaxLoad(course)){
      System.out.println("Max Faculty Load Exceeded");
      return 2;
    }

    String code = course.getCode();
    String name = course.getDesc();
    int lec = course.getLecUnits();
    int lab = course .getLabUnits();
    float hrs = course.getHrsPerWeek();
    int fac = course. getFacultyId();

    try {
      int rowAffected = Controllers.noresQuery(Queries.updateCourse(code, name, lec, lab, hrs, fac));
      System.out.println("Updated " + rowAffected + " row/s for course " + code);
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
  
  
  public static int removeCourse(String code) {
    /*
     returns the following:
        0 if update success
        1 if course code does not exist
        -1 for other exceptions
     */
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

  public static Course getCourse(String courseCode){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")){
      ResultSet result = Controllers.resQuery(Queries.selectCourse(courseCode), c);
      while (result.next()) {
        String code = result.getString("COURSE_CODE");
        String desc = result.getString("COURSE_NAME");
        int lecUnits = result.getInt("LEC_UNITS");
        int labUnits = result.getInt("LAB_UNITS");
        int assign_faculty = result.getInt("ASSIGNED_FACULTY");
        Course course = new Course(code, desc, lecUnits, labUnits, assign_faculty);
        return course;
      }
      return null;
      
    } catch (Exception e) {
      System.out.println("Getting Courses Failed: " + e);
      return null;
    }
  }


  public static ArrayList<Course> getCourseFor(int faculty){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")){
      ResultSet result = Controllers.resQuery(Queries.selectCourseFor(faculty), c);
      ArrayList<Course> courses = new ArrayList<>();
      while (result.next()) {
        String code = result.getString("COURSE_CODE");
        String desc = result.getString("COURSE_NAME");
        int lecUnits = result.getInt("LEC_UNITS");
        int labUnits = result.getInt("LAB_UNITS");
        int assign_faculty = result.getInt("ASSIGNED_FACULTY");
        Course course = new Course(code, desc, lecUnits, labUnits, assign_faculty);
        courses.add(course);
      }
      return courses;
      
    } catch (Exception e) {
      System.out.println("Getting Courses Failed: " + e);
      return null;
    }
  }
  
  
  public static ArrayList<Course> getAllCourse(){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")){
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("COURSES"), c);
      ArrayList<Course> courses = new ArrayList<>();
      while (result.next()) {
        String code = result.getString("COURSE_CODE");
        String desc = result.getString("COURSE_NAME");
        int lecUnits = result.getInt("LEC_UNITS");
        int labUnits = result.getInt("LAB_UNITS");
        int assign_faculty = result.getInt("ASSIGNED_FACULTY");
        Course course = new Course(code, desc, lecUnits, labUnits, assign_faculty);
        courses.add(course);
      }
      return courses;
      
    } catch (Exception e) {
      System.out.println("Getting Courses Failed: " + e);
      return null;
    }
  }

}
