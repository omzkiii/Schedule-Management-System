package app;
import app.controller.Controllers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
  @Override
  public void start(Stage primaryStage) {
    String text = Controllers.getAllFaculty();
    System.out.println(text);
    Label label = new Label(text);
    StackPane root = new StackPane();
    root.getChildren().add(label);

    Scene scene = new Scene(root, 300, 200);

    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
      launch(args);
  }
}
