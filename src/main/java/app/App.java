package app;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;

import app.controller.Controllers;
import app.controller.FacultyControllers;
import app.controller.Queries;
import app.controller.ScheduleController;
import app.controller.CourseControllers;
import app.model.Faculty;
import app.model.Schedule;
import app.model.Course;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
//    String text = Controllers.getAllFaculty();
//    System.out.println(text);
//    Label label = new Label(text);
//    StackPane root = new StackPane();
//    root.getChildren().add(label);
    Scene scene = new Scene(fxmlLoader.<Parent>load(), 600, 400);
    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {

    /*For Testing */

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;


    CourseControllers.removeCourse("CS302");
    FacultyControllers.removeFaculty(12121);
    ArrayList<Course> scheds = CourseControllers.getAllCourse();
    ListIterator<Course> iterator = scheds.listIterator();

    while (iterator.hasNext()) {
      System.out.println(iterator.next().getDesc());
    }

    /*For Testing */
    
    launch(args);
  
  }
}
