package app.controller;
import java.sql.*;

public class Controllers {

  // Query Execution Method
  
  public static ResultSet resQuery(String query, Connection c) throws ClassNotFoundException, SQLException {
    // Class.forName("org.sqlite.JDBC");
    Statement stmt = c.createStatement();
    ResultSet result = stmt.executeQuery(query);
    return result;
  }

  public static int noresQuery(String query) throws ClassNotFoundException, SQLException {
    // Class.forName("org.sqlite.JDBC");
    Connection c = DriverManager.getConnection("jdbc:sqlite:database.db");
    Statement stmt = c.createStatement();
    int result = stmt.executeUpdate(query);
    stmt.close();
    c.close();
    return result;
  }

}
