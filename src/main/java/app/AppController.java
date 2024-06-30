package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import app.model.Faculty;
import app.model.Schedule;
import app.view.CourseView;
import app.view.FacultyView;
import app.view.SchedView;
import app.view.SimulateView;
import app.model.Course;
import app.model.Duration;
import app.controller.ScheduleController;

public class AppController {

  private Stage stage;
  private Scene scene;
  private Parent mainPane;

  @FXML
  private Button addFacultyButton;

  @FXML
  private static TableView<Course> courseTbl;

  @FXML
  private static TableView<Faculty> facultyTbl;

  @FXML
  private static TableView<Schedule> schedTbl;

  @FXML
  private static TableView<Schedule> simTbl;

  FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));

  public void start(Stage primaryStage) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
    App.greeting = "BYE BYE!";
    mainPane = fxmlLoader.load();
    scene = new Scene(mainPane, 600, 400);
    stage = primaryStage;
    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public void render(ActionEvent event) throws IOException{
    System.out.println(App.subPane);
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    mainPane = fxmlLoader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(mainPane);
    stage.setScene(scene);
    stage.show();
    App.subPane = (BorderPane) mainPane.lookup("#subPane");
  }

  public void switchToScene1(ActionEvent event) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    start(stage);
    if (SimulateView.backgroundTask != null) {
      SimulateView.backgroundTask.cancel();
    }
  }

  public void switchToScene2Faculty(ActionEvent event) throws IOException {
    render(event);
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        facultyScene(event);
      }
    });
  }

  public void switchToScene2Course(ActionEvent event) throws IOException {
    render(event);
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        courseScene(event);
      }
    });
  }

  public void switchToScene2Schedules(ActionEvent event) throws IOException {
    LocalTime time1 = LocalTime.of(11, 30);
    LocalTime time2 = LocalTime.of(12, 30);
    LocalTime time3 = LocalTime.of(11, 30);
    LocalTime time4 = LocalTime.of(13, 30);
    Duration d1 = new Duration(time1, time2);
    Duration d2 = new Duration(time3, time4);
    Schedule schedule1 = new Schedule("THURSDAY", d2, "CCS301", 212);
    Schedule schedule2 = new Schedule("THURSDAY", d1, "CCS311", 300);
    Schedule schedule3 = new Schedule("THURSDAY", d2, "CCS303", 212);
    System.out.println("SIMULATION!!!!!!!!!!!!!!!!!");

    Thread simulateDatabaseChange = new Thread(() -> {
      System.out.println("SIMULATION!!!!!!!!!!!!!!!!!");
      try {
        while (true) {
          ScheduleController.createSchedule(schedule1);
          ScheduleController.createSchedule(schedule2);
          ScheduleController.createSchedule(schedule3);
          System.out.println("THIS FEELS LIKE MOLLY!!!!!!!!!!!!!!!!!");
          Thread.sleep(3000);
          if (ScheduleController.getAllSchedule().size() > 1) {
            ScheduleController.removeSchedule(ScheduleController.getAllSchedule().getLast());
          }
          Thread.sleep(3000);
        }
      } catch (Exception e) {
        System.out.println(e);
      }

    });
    simulateDatabaseChange.start();
    // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    render(event);
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        scheduleScene(event);
      }
    });
  }

  public void facultyScene(ActionEvent event) {
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("faculty");
    FacultyView.setFacCols(view, facultyTbl);
    App.subPane.setCenter(view);
    if (SimulateView.backgroundTask != null) {
      SimulateView.backgroundTask.cancel();
    }
  }

  public void courseScene(ActionEvent event) {
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("course");
    CourseView.setCourseCols(view, courseTbl);
    App.subPane.setCenter(view);
    if (SimulateView.backgroundTask != null) {
      SimulateView.backgroundTask.cancel();
    }
  }

  public void scheduleScene(ActionEvent event) {
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("schedules");
    SchedView.setSchedCols(view, schedTbl);
    App.subPane.setCenter(view);
    if (SimulateView.backgroundTask != null) {
      SimulateView.backgroundTask.cancel();
    }
  }

  public void simulateScene(ActionEvent event) {
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("simulate");
    App.subPane.setCenter(view);
    SimulateView.startDatabaseChecking(SimulateView.setSimCols(view, simTbl));
  }


  @FXML
  private Label welcomeText;

  @FXML
  protected void onClickButtonTest() {
    welcomeText.setText("Button_Test_Clicked");
  }

  // Dialog boxes
  @FXML
  public void openAddFacultyDialog(ActionEvent event){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-faculty.fxml"));
    FacultyView.openAddDialog(event, stage, loader);
  }

  @FXML
  private void openAddCourseDialog(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-course.fxml"));
    CourseView.openAddDialog(event, stage, loader);

  }

  @FXML
  private void openAddSchedDialog(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-schedule.fxml"));
    SchedView.openAddDialog(event, stage, loader);
  }
}
