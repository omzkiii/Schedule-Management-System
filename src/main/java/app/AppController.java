package app;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;


import app.controller.CourseControllers;
import app.model.Course;

public class AppController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private BorderPane subPane;
  @FXML
  private TableView<Course> courseTable;
  
  


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
        setCourseCols(view);
        subPane.setCenter(view);
      }
    });
  }


  public void setCourseCols(Pane view){
    courseTable = (TableView<Course>) view.lookup("#courseTable");
    TableColumn<Course, String> codeCol = (TableColumn<Course, String>) courseTable.getColumns().get(0);
    codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
    
    TableColumn<Course, String> desCol = (TableColumn<Course, String>) courseTable.getColumns().get(1);
    desCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
    
    TableColumn<Course, String> lecUnitsCol = (TableColumn<Course, String>) courseTable.getColumns().get(2);
    lecUnitsCol.setCellValueFactory(new PropertyValueFactory<>("lecUnits"));
    
    TableColumn<Course, String> labUnitsCol = (TableColumn<Course, String>) courseTable.getColumns().get(3);
    labUnitsCol.setCellValueFactory(new PropertyValueFactory<>("labUnits"));


    TableColumn<Course, String> hpweekCol = (TableColumn<Course, String>) courseTable.getColumns().get(4);
    hpweekCol.setCellValueFactory(new PropertyValueFactory<>("hrsPerWeek"));
    
    TableColumn<Course, String> facultyCol = (TableColumn<Course, String>) courseTable.getColumns().get(5);
    facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));

    TableColumn<Course, Void> actionCol = (TableColumn<Course, Void>) courseTable.getColumns().get(6);
    actionCol.setCellFactory(setCourseButton());

    for (Course course : CourseControllers.getAllCourse()) {
      courseTable.getItems().add(course);
    }
  }

  private Callback<TableColumn<Course, Void>, TableCell<Course, Void>> setCourseButton() {
    return new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
      @Override
      public TableCell<Course, Void> call(TableColumn<Course, Void> param) {
        return new TableCell<Course, Void>() {
          private final Button button1 = new Button("Button 1");
          private final Button button2 = new Button("Button 2");
          private final HBox hbox = new HBox(button1, button2);
        {
            button1.setOnAction(event -> {
              Course course = getTableView().getItems().get(getIndex());
              System.out.println("Button 1 clicked for course: " + course);
            });
            button2.setOnAction(event -> {
              Course course = getTableView().getItems().get(getIndex());
              System.out.println("Button 2 clicked for course: " + course);
            });
          }

          @Override
          protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : hbox);
          }
        };
      }
    };
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
        subPane.setCenter(view);
      }
    });
  }


  public void facultyScene(ActionEvent event) {
    System.out.println("You clicked me! faculty");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("faculty");
    subPane.setCenter(view);
  }

  public void courseScene(ActionEvent event) {
    System.out.println("You clicked me! course");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("course");
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
  protected void onClickButtonTest() {welcomeText.setText("Button_Test_Clicked"); }
  
}
