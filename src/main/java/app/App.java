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

    launch(args);
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;


    try {
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
