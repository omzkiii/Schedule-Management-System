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

import app.model.Faculty;
import app.model.Schedule;
import app.view.CourseView;
import app.view.FacultyView;
import app.view.SchedView;
import app.model.Course;

public class AppController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private Button addFacultyButton;

  @FXML
  private BorderPane subPane;
  @FXML
  private TableView<Course> courseTbl;

  @FXML
  private TableView<Faculty> facultyTbl;

  @FXML
  private TableView<Schedule> schedTbl;


  public void switchToScene1(ActionEvent event) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene((Parent) fxmlLoader.load());
    stage.setScene(scene);
    stage.show();
  }
  public void switchToScene2Faculty(ActionEvent event) throws IOException {
    // Load the main scene
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    Parent mainPane = fxmlLoader.load();

    // Get the current stage and set the scene
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(mainPane);
    stage.setScene(scene);
    stage.show();

    final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("faculty");
        FacultyView.setFacCols(view, facultyTbl);
        // FacultyView.loadFacData(view, facultyTbl);

        subPane.setCenter(view);
      }
    });
  }


  public void switchToScene2Course(ActionEvent event) throws IOException {
    // Load the main scene
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    Parent mainPane = fxmlLoader.load();

    // Get the current stage and set the scene
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(mainPane);
    stage.setScene(scene);
    stage.show();

    final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("course");
        CourseView.setCourseCols(view, courseTbl);
        subPane.setCenter(view);
      }
    });
  }

  public void switchToScene2Schedules(ActionEvent event) throws IOException {
    // Load the main scene
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
    Parent mainPane = fxmlLoader.load();

    // Get the current stage and set the scene
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(mainPane);
    stage.setScene(scene);
    stage.show();

    final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
    // Asynchronously load additional components into subPane
    Platform.runLater(new Runnable() {
      public void run() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("schedules");
        SchedView.setSchedCols(view, schedTbl);
        // loadSchedData(view);
        subPane.setCenter(view);
      }
    });
  }

  public void facultyScene(ActionEvent event) {
    System.out.println("You clicked me! faculty");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("faculty");
    FacultyView.setFacCols(view, facultyTbl);
    // FacultyView.loadFacData(view, facultyTbl);
    subPane.setCenter(view);
  }

  public void courseScene(ActionEvent event) {
    System.out.println("You clicked me! course");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("course");
    CourseView.setCourseCols(view, courseTbl);
    subPane.setCenter(view);
  }

  public void scheduleScene(ActionEvent event) {
    System.out.println("You clicked me!");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("schedules");
    SchedView.setSchedCols(view, schedTbl);
    subPane.setCenter(view);
  }

  public void simulateScene(ActionEvent event) {
    System.out.println("You clicked me!");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("simulate");
    subPane.setCenter(view);
  }

  public void populateCourseTable(){
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("course"));
    System.out.println(fxmlLoader.getResources());

  }


  @FXML
  private Label welcomeText;
  @FXML
  protected void onClickButtonTest() {welcomeText.setText("Button_Test_Clicked");}


  // Dialog boxes
  @FXML
  private void openAddFacultyDialog(ActionEvent event){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-faculty.fxml"));
    FacultyView.openAddDialog(event, stage, loader);
  }

  @FXML
  private void openAddCourseDialog(ActionEvent event){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-course.fxml"));
    CourseView.openAddDialog(event, stage, loader);
    
  }

  @FXML
  private void openAddSchedDialog(ActionEvent event){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-schedule.fxml"));

  }
}
