package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

import app.model.Faculty;
import app.model.Schedule;
import app.view.CourseView;
import app.view.FacultyView;
import app.view.SchedView;
import app.view.SimulateView;
import app.model.Course;

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

  @FXML
  private static ListView<String> availList;

  public void start(Stage primaryStage) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
    mainPane = fxmlLoader.load();
    scene = new Scene(mainPane, 800, 400);
    stage = primaryStage;
    Image icon = new Image("Images/calendar-lines.png");
    primaryStage.getIcons().add(icon);
    primaryStage.setTitle("FACFLOW");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void render(ActionEvent event) throws IOException{
    System.out.println(App.subPane);
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    mainPane = fxmlLoader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(mainPane);
    stage.setMinHeight(600);
    stage.setMinWidth(1400);
    stage.setScene(scene);
    stage.show();
    App.subPane = (BorderPane) mainPane.lookup("#subPane");
  }

  public void switchToScene1(ActionEvent event) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setHeight(500);
    stage.setWidth(800);
    stage.setMinHeight(500);
    stage.setMinWidth(800);
    start(stage);
    if (SimulateView.backgroundTask != null) {
      SimulateView.backgroundTask.cancel();
    }
  }

  public void switchToScene2Faculty(ActionEvent event) throws IOException {
    render(event);
    Platform.runLater(new Runnable() {
      public void run() {
        facultyScene(event);
      }
    });
  }

  public void switchToScene2Course(ActionEvent event) throws IOException {
    render(event);
    Platform.runLater(new Runnable() {
      public void run() {
        courseScene(event);
      }
    });
  }

  public void switchToScene2Schedules(ActionEvent event) throws IOException {
    render(event);
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
    SimulateView.startDatabaseChecking(SimulateView.setSimCols(view, simTbl), SimulateView.setSimList(view, availList));
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
