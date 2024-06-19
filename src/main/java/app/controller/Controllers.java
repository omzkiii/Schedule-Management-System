package app.controller;
import java.sql.*;

public class Controllers {

  // Query Execution Method
  
  public static ResultSet resQuery(String query) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    ResultSet result = stmt.executeQuery(query);
    return result;
  }

  public static int noresQuery(String query) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    int result = stmt.executeUpdate(query);
    return result;
  }

  
  // Classroom Table Methods
  
  // Schedule Table Methods

}
