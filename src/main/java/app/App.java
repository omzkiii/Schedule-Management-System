package app;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import app.view.SimulateView;

import java.io.IOException;

public class App extends Application {
  public static BorderPane subPane;
  @Override
  public void start(Stage primaryStage) throws IOException {
    AppController app = new AppController();
    primaryStage.setOnHidden((e) -> {
      if (SimulateView.backgroundTask != null) {
        SimulateView.backgroundTask.cancel();
      }
    });;
    app.start(primaryStage);
  }


  public static void main(String[] args) {
       
    launch(args);
  
  }
}
