package app;

import java.util.ArrayList;
import java.util.ListIterator;

import app.controller.CourseControllers;
import app.controller.FacultyControllers;
import app.model.Course;
import app.model.Faculty;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage primaryStage) {
    ArrayList<Faculty> text = FacultyControllers.getAllFaculty();
    System.out.println("From App.java: " + text);
    Label label = new Label(text.toString());
    StackPane root = new StackPane();
    root.getChildren().add(label);
    Scene scene = new Scene(root, 300, 200);

    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    ArrayList<Course> courses = CourseControllers.getAllCourse();

    ListIterator<Course> courseIterator = courses.listIterator();

    while (courseIterator.hasNext()) {
      System.out.println(courseIterator.next().getCode());
    }
    // launch(args);
  }
}
