package app.controller;

import java.sql.ResultSet;

public class ScheduleController {
  public static int getAllSchedule(){
    try {
      ResultSet result = Controllers.resQuery(Queries.selectAllFrom("SCHEDULE"));
    } catch (Exception e) {
      // TODO: handle exception
    }
    return 0;
  }
  
}
