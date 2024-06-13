package controller;
import java.sql.*;
public class Controllers {
  // Query Execution Method
  public static ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    // TODO Handle exception when sql returns nothing
    System.out.println("hello");
    ResultSet result = stmt.executeQuery(query);
    System.out.println("result: " + result);
    return result;
  }


  // Faculty Table Methods
  public static void getAllFaculty(){
    try {
      ResultSet result = executeQuery(Queries.selectFaculty);
      while (result.next()) {
          String name = result.getString("NAME");
          System.out.println(name);
      }
    } catch (Exception e) {
      System.out.println("Get All Faculty Failed: " + e.getMessage());
    }
  }
  
  public static void deleteFaculty(String name){
    try {
      executeQuery(Queries.deleteFaculty(name));
    } catch (Exception e) {
      System.out.println("Delete Faculty Failed: " + e.getMessage());
    }
  }

  public static void insertFaculty(String name){
    try {
      executeQuery(Queries.insertFaculty(name));
    } catch (Exception e) {
      System.out.println("Insert Faculty Failed: " + e.getMessage());
    }
  }

  
  // Course Table Methods
  
  // Classroom Table Methods
  
  // Schedule Table Methods

}
