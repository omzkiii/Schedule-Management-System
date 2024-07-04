package app.controller;

import app.model.Faculty;
import app.utils.CourseChecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacultyControllers {

  // Faculty Table Methods
  public static int createFaculty(Faculty faculty){
    /* 
    *returns the following:
        0 if success
        1 if id already exists
        -1 for other exceptions
    */

    try {
      int rowAffected = Controllers.noresQuery(Queries.insertFaculty(faculty.getId(), faculty.getName(), faculty.getMaxLoad()));
      System.out.println("Inserted " + rowAffected + " row/s for employee " + faculty.getId());
      return 0;
      
    } catch (SQLException e) {
      if(e.getErrorCode() == 19){
        System.out.println("Employee no. " + faculty.getId() + " already exists");
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e){
      System.out.println("Unexpected error");
      return -1;
    }
  }
  

  public static int modifyFaculty(Faculty faculty){
    /*
     returns the following:
        0 if update success
        1 if employee id does not exist
        2 if editing max load will result in conflict with existing course tagging
        -1 for other exceptions
     */
    try {
      Faculty origFac = getFaculty(faculty.getId());
      int rowAffected = Controllers.noresQuery(Queries.updateFaculty(faculty.getId(), faculty.getName(), faculty.getMaxLoad()));
      System.out.println("Updated " + rowAffected + " row/s for employee " + faculty.getId());
      if(rowAffected == 1){
        
        //To check if new max load will conflict existing course tagging
        float currentLoad = CourseChecker.currentFacLoad(faculty.getId());

        if(currentLoad > faculty.getMaxLoad()){
          Controllers.noresQuery(Queries.updateFaculty(origFac.getId(), origFac.getName(), origFac.getMaxLoad()));
          return 2;
        }

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


  public static int modifyFacultyName(Faculty faculty){
    /*
     returns the following:
        0 if update success
        1 if employee id does not exist
        -1 for other exceptions
     */
    try {
      int rowAffected = Controllers.noresQuery(Queries.updateFaculty(faculty.getId(), faculty.getName()));
      System.out.println("Updated " + rowAffected + " row/s for employee " + faculty.getId());
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


  public static int modifyFacultyLoad(Faculty faculty){
    /*
     returns the following:
        0 if update success
        1 if employee id does not exist
        -1 for other exceptions
     */
    try {
      int rowAffected = Controllers.noresQuery(Queries.updateFaculty(faculty.getId(), faculty.getMaxLoad()));
      System.out.println("Updated " + rowAffected + " row/s for employee " + faculty.getId());
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
  

  public static int removeFaculty(int id){
    /*
     returns the following:
        0 if update success
        1 if employee id does not exist
        -1 for other exceptions
     */    
    try {
      int rowAffected = Controllers.noresQuery(Queries.deleteFaculty(id));
      System.out.println("Deleted " + rowAffected + " row/s for employee " + id);
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

  public static Faculty getFaculty(int emp_id){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")){
      ResultSet result = Controllers.resQuery(Queries.getFaculty(emp_id), c);
      while (result.next()) {
        int id = result.getInt("ID");
        String name = result.getString("NAME");
        int maxLoad = result.getInt("MAX_LOAD");
        Faculty faculty = new Faculty(id, name, maxLoad);
        return faculty; 
      }

      return null;

    } catch (Exception e) {
      System.out.println("Get Faculty Failed: " + e.getMessage());
      return null;
    }
  }
  

  public static ArrayList<Faculty> getAllFaculty(){
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:database.db")){
      ResultSet result = Controllers.resQuery(Queries.selectFaculty, c);
      ArrayList<Faculty> faculties = new ArrayList<>();
      while (result.next()) {
        int id = result.getInt("ID");
        String name = result.getString("NAME");
        int maxLoad = result.getInt("MAX_LOAD");
        Faculty faculty = new Faculty(id, name, maxLoad); 
        faculties.add(faculty);
      }
      return faculties;
      
    } catch (Exception e) {
      System.out.println("Get All Faculty Failed: " + e.getMessage());
      return null;
    }
  }
}
