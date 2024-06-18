package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class AppController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToScene1(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((Parent) fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void switchToScene2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene((Parent) fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Label welcomeText;
    @FXML
    protected void onClickButtonTest() {welcomeText.setText("Button_Test_Clicked"); }
}
