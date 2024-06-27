package app.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import app.controller.ScheduleController;
import app.model.Schedule;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class SimulateView {
  public static void Simulate() {
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    LocalTime timeNow = LocalTime.now();
    System.out.println(timeNow);
    System.out.println(dayOfWeek);
    ArrayList<Schedule> schedules = ScheduleController.getTodaySchedule(dayOfWeek.toString());
    for (Schedule schedule : schedules) {
      System.out.println(schedule.toString());
      
    }
  }
  
  public static void setSimCols(Pane view, TableView<Schedule> simTbl ){
    simTbl = (TableView<Schedule>) view.lookup("#simList");
    TableColumn<Schedule, String> room =  (TableColumn<Schedule, String>) simTbl.getColumns().get(0);
    TableColumn<Schedule, String> faculty =  (TableColumn<Schedule, String>) simTbl.getColumns().get(1);
    TableColumn<Schedule, String> course = (TableColumn<Schedule, String>) simTbl.getColumns().get(2);
    TableColumn<Schedule, String> time = (TableColumn<Schedule, String>) simTbl.getColumns().get(3);
    TableColumn<Schedule, String> day = (TableColumn<Schedule, String>) simTbl.getColumns().get(4);

    course.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
    faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
    room.setCellValueFactory(new PropertyValueFactory<>("roomId"));
    time.setCellValueFactory(new PropertyValueFactory<>("duration"));
    day.setCellValueFactory(new PropertyValueFactory<>("day"));
    
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    ArrayList<Schedule> schedules = ScheduleController.getTodaySchedule(dayOfWeek.toString());
    LocalTime timeNow = LocalTime.now();
    if (schedules.isEmpty()){
      return;
    }
    for (Schedule schedule : schedules) {
      LocalTime start = schedule.getDuration().getStart(); 
      LocalTime end = schedule.getDuration().getEnd(); 
      if (start.isBefore(timeNow) && end.isAfter(timeNow)) {
        System.out.println(start + " " + start.isBefore(timeNow));
        System.out.println(end + " " + end.isAfter(timeNow));
        System.out.println(timeNow);
        simTbl.getItems().add(schedule);
      }
    }
  }

}
