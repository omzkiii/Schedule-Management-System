package app;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import app.controller.FacultyControllers;
import app.controller.ScheduleController;
import app.model.Faculty;
import app.model.Schedule;
import app.view.CourseView;
import app.view.FacultyView;
import app.controller.CourseControllers;
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
        setSchedCols(view);
        loadSchedData(view);
        subPane.setCenter(view);
      }
    });
  }

  private void setSchedCols(Pane view){
    schedTbl = (TableView<Schedule>) view.lookup("#schedList");
    TableColumn<Schedule, String> day = (TableColumn<Schedule, String>) schedTbl.getColumns().get(0);
    TableColumn<Schedule, String> time = (TableColumn<Schedule, String>) schedTbl.getColumns().get(1);
    TableColumn<Schedule, String> course = (TableColumn<Schedule, String>) schedTbl.getColumns().get(2);
    TableColumn<Schedule, String> faculty =  (TableColumn<Schedule, String>) schedTbl.getColumns().get(3);
    TableColumn<Schedule, Integer> room =  (TableColumn<Schedule, Integer>) schedTbl.getColumns().get(4);
    TableColumn<Schedule, Void> actions =  (TableColumn<Schedule, Void>) schedTbl.getColumns().get(5);

    day.setCellValueFactory(new PropertyValueFactory<>("day"));
    time.setCellValueFactory(new PropertyValueFactory<>("duration"));
    course.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
    faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
    room.setCellValueFactory(new PropertyValueFactory<>("roomId"));
    // actions.setCellFactory((setSchedBtn()));

  }

  private void loadSchedData(Pane view){
    ObservableList<Schedule> data = FXCollections.observableArrayList();
    for(Schedule s: ScheduleController.getAllSchedule()){
      data.add(s);
    }
    schedTbl.getItems().addAll(data);

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
  private void openAddDialog(ActionEvent event){
    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog-faculty.fxml"));
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      TextField addFacId = (TextField) dp.lookup("#addFacId");
      TextField addFacName = (TextField) dp.lookup("#addFacName");
      TextField addFacLoad = (TextField) dp.lookup("#addFacLoad");

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          int id = Integer.parseInt(addFacId.getText());
          String name = addFacName.getText();
          int load = Integer.parseInt(addFacLoad.getText());

          if(load != 30 && load != 15){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid max load. Acceptable values: 30, 15");
            a.show();
          }

          try{
            Faculty fac = new Faculty(id, name, load);
            System.out.println(fac);
          } catch(IllegalArgumentException e) {

          }
        }
      });
    } catch(Exception e) {
    }
  }
}
