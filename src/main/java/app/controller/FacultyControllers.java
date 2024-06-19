package app.controller;

import app.model.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacultyControllers {
  // Faculty Table Methods
  public static int createFaculty(int id, String name){
    /* 
    *returns the following:
        0 if success
        1 if id already exists
        -1 for other exceptions
    */

    try {
      int rowAffected = Controllers.noresQuery(Queries.insertFaculty(id, name));
      System.out.println("Inserted " + rowAffected + " row/s for employee " + id);
      return 0;
      
    } catch (SQLException e) {
      if(e.getErrorCode() == 19){
        System.out.println("Employee no. " + id + " already exists");
        return 1;
      } else {
        return -1;
      }
    } catch (Exception e){
      System.out.println("Unexpected error");
      return -1;
    }
  }
  

  public static int modifyFaculty(int id, String name){
    /*
     returns the following:
        0 if update success
        1 if employee id does not exist
        -1 for other exceptions
     */
    try {
      int rowAffected = Controllers.noresQuery(Queries.updateFaculty(id, name));
      System.out.println("Updated " + rowAffected + " row/s for employee " + id);
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
  

  public static ArrayList<Faculty> getAllFaculty(){
    try {
      ResultSet result = Controllers.resQuery(Queries.selectFaculty);
      ArrayList<Faculty> faculties = new ArrayList<>();
      while (result.next()) {
        String id = result.getString("ID");
        String name = result.getString("NAME");
        Faculty faculty = new Faculty(name, Integer.parseInt(id)); 
        faculties.add(faculty);
      }
      return faculties;
      
    } catch (Exception e) {
      System.out.println("Get All Faculty Failed: " + e.getMessage());
      return null;
    }
  }
}
