package app;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ListIterator;

import app.controller.Controllers;
import app.controller.FacultyControllers;
import app.controller.Queries;
import app.controller.ScheduleController;
import app.controller.CourseControllers;
import app.model.Faculty;
import app.model.Schedule;
import app.utils.ScheduleChecker;
import app.model.Course;
import app.model.Duration;
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

    // DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;


    // CourseControllers.removeCourse("CS302");
    // FacultyControllers.removeFaculty(12121);
    // ArrayList<Course> scheds = CourseControllers.getAllCourse();
    // ListIterator<Course> iterator = scheds.listIterator();

    // while (iterator.hasNext()) {
    //   System.out.println(iterator.next().getDesc());
    // }
 

    // LocalTime time1 = LocalTime.of(7, 30);
    // LocalTime time2 = LocalTime.of(12, 30);

    // System.out.println("Hours between: " + time1.until(time2, ChronoUnit.HOURS));

    // LocalTime time3 = LocalTime.of(10, 30);
    // LocalTime time4 = LocalTime.of(12, 00);

    // Duration d1 = new Duration(time1, time2);
    // Duration d2 = new Duration(time3, time4);

    // Schedule schedule1 = new Schedule(15523, "Tuesday", d1, "343", 221);
    // // Schedule schedule2 = new Schedule(1213, "Friday", d1, "343", 222);

    // int res = ScheduleController.createSchedule(schedule1);

    // System.out.println("Adding schedule res: " + res);
    
    ArrayList<Course> cs = CourseControllers.getAllCourse();
    for(Course c: cs){
      System.out.println(c);
    }


  
    /*For Testing */
    
    launch(args);
  
  }
}
