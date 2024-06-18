package app;

import app.controller.Controllers;
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
    launch();
  }
}
