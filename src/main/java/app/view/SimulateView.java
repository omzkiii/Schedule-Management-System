package app.view; import java.time.DayOfWeek; import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import app.FxmlLoader;
import app.controller.ScheduleController;
import app.model.Schedule;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SimulateView{
  private static ScheduledExecutorService scheduler;
  // private static int lastSchedId = ScheduleController.getAllSchedule().getLast().getId();
  private static int lastSchedId = 0;

  public static void startDatabaseChecking(TableView<Schedule> simTbl, BorderPane subPane){

    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
    }

    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(() -> {
      try {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("simulate");
        setSimCols(view, simTbl);
        subPane.setCenter(view);
        boolean changesDetected = isDatabaseSame();
        System.out.println("WHO WATCHES THE WATCHMEN?" + Thread.currentThread().getName());
        if(!changesDetected) {
          System.out.println("RUNLATER MUST RUN!");
          Platform.runLater(()->{updateSimTable(simTbl);});
        }

      } catch (Exception e) {
        System.out.println(e);
      }
    }, 0, 3, TimeUnit.SECONDS);

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
    // setSimTable(simTbl);
    
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

  public static void updateSimTable(TableView<Schedule> simTbl){
    System.out.println("WUBALLUBADUBDUB");
    simTbl.getItems().clear();
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
        simTbl.getItems().add(schedule);
      }
    }
  }

}