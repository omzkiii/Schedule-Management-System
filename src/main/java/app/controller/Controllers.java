package app.controller;
import java.sql.*;

public class Controllers {

  // Query Execution Methods
  public static ResultSet resQuery(String query) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    ResultSet result = stmt.executeQuery(query);
    stmt.close();
    c.close();
    return result;
  }

  public static int noresQuery(String query) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    int result = stmt.executeUpdate(query);
    stmt.close();
    c.close();
    return result;
  }

  


  // Faculty Table Methods
  public static int createFaculty(int id, String name){
    /* 
    *returns the following:
        0 if success
        1 if id already exists
        -1 for other exceptions
    */

    try {
      int rowAffected = noresQuery(Queries.insertFaculty(id, name));
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
      int rowAffected = noresQuery(Queries.updateFaculty(id, name));
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
      int rowAffected = noresQuery(Queries.deleteFaculty(id));
      System.out.println("Deleted " + rowAffected + " row/s for employee " + id);
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
  


  public static String getAllFaculty(){
    try {
      ResultSet result = resQuery(Queries.selectFaculty);
      while (result.next()) {
          String name = result.getString("NAME");
          return name;
      }
    } catch (Exception e) {
      System.out.println("Get All Faculty Failed: " + e.getMessage());
    }
    return null;
  }
  
  


  
  // Course Table Methods
  
  // Classroom Table Methods
  
  // Schedule Table Methods

}
