package app.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ManageFaculty extends Application {
  @Override
  public void start(Stage primaryStage) {
    Label label = new Label("MAIN APP");
    StackPane root = new StackPane();
    Button button = new Button();

    Scene scene = new Scene(root, 300, 200);

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
      public void handle(ActionEvent e) {
      };
    };
    
    button.setOnAction(event);
    root.getChildren().add(label);
    root.getChildren().add(button);
    
    primaryStage.setTitle("Hello JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
