package app;

import java.util.ArrayList;
import java.util.ListIterator;

import app.controller.Controllers;
import app.controller.CourseControllers;
import app.controller.FacultyControllers;
import app.controller.Queries;
import app.model.Course;
import app.model.Faculty;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
  @Override
<<<<<<< HEAD
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
//    String text = Controllers.getAllFaculty();
//    System.out.println(text);
//    Label label = new Label(text);
//    StackPane root = new StackPane();
//    root.getChildren().add(label);
    Scene scene = new Scene(fxmlLoader.<Parent>load(), 600, 400);
=======
  public void start(Stage primaryStage) {
    ArrayList<Faculty> text = FacultyControllers.getAllFaculty();
    System.out.println("From App.java: " + text);
    Label label = new Label(text.toString());
    StackPane root = new StackPane();
    root.getChildren().add(label);
    Scene scene = new Scene(root, 300, 200);

>>>>>>> 62f3c809028b3bdadb7b0659506ac183dafdc92d
    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
<<<<<<< HEAD
    launch();
=======
    // ArrayList<Course> courses = CourseControllers.getAllCourse();
    //
    // ListIterator<Course> courseIterator = courses.listIterator();
    //
    // while (courseIterator.hasNext()) {
    //   System.out.println(courseIterator.next().getCode());
    // }
    // launch(args);
    try {
      Controllers.noresQuery(Queries.createScheduleTable);
      
    } catch (Exception e) {
      System.out.println(e);
    }
>>>>>>> 62f3c809028b3bdadb7b0659506ac183dafdc92d
  }
}
