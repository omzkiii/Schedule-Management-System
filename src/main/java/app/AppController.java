package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button addFacultyButton;

    @FXML
    private BorderPane subPane;


    public void switchToScene1(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("schedule-management.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((Parent) fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void switchToScene2Faculty(ActionEvent event) throws IOException {
        // Load the main scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
        Parent mainPane = fxmlLoader.load();

        // Get the current stage and set the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();

        final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
        // Asynchronously load additional components into subPane
        Platform.runLater(new Runnable() {
            public void run() {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("faculty");
                subPane.setCenter(view);
            }
        });
    }

    public void switchToScene2Course(ActionEvent event) throws IOException {
        // Load the main scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
        Parent mainPane = fxmlLoader.load();

        // Get the current stage and set the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();

        final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
        // Asynchronously load additional components into subPane
        Platform.runLater(new Runnable() {
            public void run() {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("course");
                subPane.setCenter(view);
            }
        });
    }

    public void switchToScene2Schedules(ActionEvent event) throws IOException {
        // Load the main scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("simulate-management.fxml"));
        Parent mainPane = fxmlLoader.load();

        // Get the current stage and set the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();

        final BorderPane subPane = (BorderPane) mainPane.lookup("#subPane");
        // Asynchronously load additional components into subPane
        Platform.runLater(new Runnable() {
            public void run() {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("schedules");
                subPane.setCenter(view);
            }
        });
    }


    public void facultyScene(ActionEvent event) {
        System.out.println("You clicked me! faculty");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("faculty");
        subPane.setCenter(view);
    }

    public void courseScene(ActionEvent event) {
        System.out.println("You clicked me! course");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("course");
        subPane.setCenter(view);
    }

    public void scheduleScene(ActionEvent event) {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("schedules");
        subPane.setCenter(view);
    }

    public void simulateScene(ActionEvent event) {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("simulate");
        subPane.setCenter(view);
    }


    @FXML
    private Label welcomeText;
    @FXML
    protected void onClickButtonTest() {welcomeText.setText("Button_Test_Clicked");}





}
