package app.view; import java.time.DayOfWeek; import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import app.App;
import app.AppController;
import app.FxmlLoader;
import app.controller.ScheduleController;
import app.model.Schedule;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;



public class SimulateView{
  @FXML 
  // private static TableView<Schedule> simTbl;
  public static Task<Void> backgroundTask;

  private static int lastSchedId = ScheduleController.getAllSchedule().getLast().getId();

  public static void startDatabaseChecking(TableView<Schedule> simTbl){
    if(backgroundTask != null && backgroundTask.isRunning()){
      System.out.println("Background Task is Already Running");
      return;
    }
    backgroundTask = new Task<Void>(){
      @Override
      protected Void call() throws Exception {
        while (!isCancelled()) {
          try {
            boolean noChangesDetected = isDatabaseSame();
            if(!noChangesDetected) {
              Platform.runLater(() -> {
                try {
                  FxmlLoader object = new FxmlLoader();
                  Pane view = object.getPage("simulate");
                  setSimCols(view, simTbl);
                  App.subPane.setCenter(view);
                  // System.out.println("WHO WATCHES THE WATCHMEN?" + Thread.currentThread().getName());

                } catch (Exception e) {
                  System.out.println("FIDDLER:" + e);
                }
              });
            }
            Thread.sleep(1000);
          } catch (Exception e) {
          }
        }
        return null;
      }
    };
    Thread thread = new Thread(backgroundTask);
    // thread.setDaemon(true);
    thread.start();
  }

  private static boolean isDatabaseSame(){
    int newlastSchedId = ScheduleController.getAllSchedule().getLast().getId();
    if (newlastSchedId == lastSchedId) {
      System.out.println("NO CHANGE :(");
    }
    else{
      System.out.println("CHANGE!!!!");
    }
    return newlastSchedId == lastSchedId;
  }


  
  public static TableView<Schedule> setSimCols(Pane view, TableView<Schedule> simTbl ){

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
    // setSimTable(simTbl);
    
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    ArrayList<Schedule> schedules = ScheduleController.getTodaySchedule("THURSDAY");
    LocalTime timeNow = LocalTime.parse("11:59");
    if (schedules.isEmpty()){
      return simTbl;
    }
    for (Schedule schedule : schedules) {
      LocalTime start = schedule.getDuration().getStart(); 
      LocalTime end = schedule.getDuration().getEnd(); 
      System.out.println(start + " " + start.isBefore(timeNow));
      System.out.println(end + " " + end.isAfter(timeNow));
      System.out.println(timeNow);
      if (start.isBefore(timeNow) && end.isAfter(timeNow)) {
        simTbl.getItems().add(schedule);
      }
    }
    System.out.println("SIMTBL STATUS AFTER setSimCols: " + simTbl==null);
    return simTbl;
  }

  public void setSimTable(TableView<Schedule> simTbl){
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    ArrayList<Schedule> schedules = ScheduleController.getTodaySchedule(dayOfWeek.toString());
    LocalTime timeNow = LocalTime.parse("11:59");
    if (schedules.isEmpty()){
      return;
    }
    for (Schedule schedule : schedules) {
      LocalTime start = schedule.getDuration().getStart(); 
      LocalTime end = schedule.getDuration().getEnd(); 
      System.out.println(start + " " + start.isBefore(timeNow));
      System.out.println(end + " " + end.isAfter(timeNow));
      System.out.println(timeNow);
      if (start.isBefore(timeNow) && end.isAfter(timeNow)) {
        simTbl.getItems().add(schedule);
      }
    }
  }

  public static void updateSimTable(){
    System.out.println("WUBALLUBADUBDUB");
    // if(simTbl != null){
    //   simTbl.getItems().clear();
    // }
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    ArrayList<Schedule> schedules = ScheduleController.getTodaySchedule(dayOfWeek.toString());
    LocalTime timeNow = LocalTime.parse("11:59");
    for (Schedule schedule : schedules) {
      LocalTime start = schedule.getDuration().getStart(); 
      LocalTime end = schedule.getDuration().getEnd(); 
      System.out.println(start + " " + start.isBefore(timeNow));
      System.out.println(end + " " + end.isAfter(timeNow));
      System.out.println(timeNow);
      if (start.isBefore(timeNow) && end.isAfter(timeNow)) {
        // AppController.simTbl.getItems().add(schedule);
      }
    }
  }
}
