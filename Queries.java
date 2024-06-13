public class Queries {
  static String createFacultyTable = "CREATE TABLE FACULTY " +
                      "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                      " NAME           TEXT    NOT NULL)"; 
  
  public static String insertFaculty(String name) {
    String query = "INSERT INTO FACULTY ( NAME ) " +
                      "VALUES ('" + name + "');";
    return query;
  }

  public static String selectAllFrom(String table){
    String query = "SELECT * FROM " + table + ";";
    return query;
  }

  public static String deleteFaculty(String name){
    String query = "DELETE FROM FACULTY WHERE NAME='" + name + "'";
    return query;
  }

  static String selectFaculty =  "SELECT * FROM FACULTY;";

  static String dropTable = "DROP TABLE FACULTY;";
}
