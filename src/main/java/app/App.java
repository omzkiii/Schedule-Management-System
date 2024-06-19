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
import app.model.Faculty;
import app.model.Schedule;
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
    // launch(args);
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    Schedule schedule = new Schedule(4, 1, "Monday", LocalTime.parse("07:30:00",formatter), LocalTime.parse("09:30:00",formatter), "CITE001", 301);
    // ScheduleController.createSchedule(schedule);

    try {
      Controllers.noresQuery(Queries.deleteSchedule(schedule));
      ArrayList<Schedule> scheds = ScheduleController.getAllSchedule();
      ListIterator<Schedule> iterator = scheds.listIterator();

      while (iterator.hasNext()) {
        System.out.println(iterator.next().getStart());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
