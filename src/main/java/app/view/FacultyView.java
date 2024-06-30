package app.view;

import app.App;
import app.FxmlLoader;
import app.controller.FacultyControllers;
import app.model.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FacultyView {
  public static TableView<Faculty> localFacultyTbl;

  public static TableView<Faculty> setFacCols(Pane view, TableView<Faculty> facultyTbl){
    facultyTbl = (TableView<Faculty>) view.lookup("#facList");
    TableColumn<Faculty, Integer> id = (TableColumn<Faculty, Integer>) facultyTbl.getColumns().get(0);
    TableColumn<Faculty, String> name = (TableColumn<Faculty, String>) facultyTbl.getColumns().get(1);
    TableColumn<Faculty, Integer> maxLoad = (TableColumn<Faculty, Integer>) facultyTbl.getColumns().get(2);
    TableColumn<Faculty, Void> actions =  (TableColumn<Faculty, Void>) facultyTbl.getColumns().get(3);

    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    maxLoad.setCellValueFactory(new PropertyValueFactory<>("maxLoad"));
    actions.setCellFactory(setFacBtn());
    

    for(Faculty faculty: FacultyControllers.getAllFaculty()){
      facultyTbl.getItems().add(faculty);
    }
    localFacultyTbl = facultyTbl;
    return facultyTbl;
  }

  public static Callback<TableColumn<Faculty, Void>, TableCell<Faculty, Void>> setFacBtn() {
    return new Callback<TableColumn<Faculty, Void>, TableCell<Faculty, Void>>() {
      @Override
      public TableCell<Faculty, Void> call(TableColumn<Faculty, Void> param) {
        return new TableCell<Faculty, Void>() {
          private final Button editBtn = new Button("Edit");
          private final Button delBtn = new Button("Delete");
          private final HBox buttonsBox = new HBox(editBtn, delBtn);

        {
            editBtn.setOnAction(event -> {
              Faculty faculty = getTableView().getItems().get(getIndex());
              System.out.println("Edit button for: " + faculty.getId() + " " + faculty.getName());
              openEditDialog(event, faculty);
            });

            delBtn.setOnAction(event -> {
              Faculty faculty = getTableView().getItems().get(getIndex());
              System.out.println("Delete button for: " + faculty.getId() + " " + faculty.getName());
              Alert a = new Alert(AlertType.CONFIRMATION);
              a.setContentText("Are you sure you want to delete faculty " + faculty);
              a.showAndWait().ifPresent((btnType) -> {
                if(btnType ==ButtonType.OK){
                  try{
                    int res = FacultyControllers.removeFaculty(faculty.getId());
        
                    if (res == 0){
                      updateTable(localFacultyTbl);
                      Alert inf = new Alert(AlertType.INFORMATION);
                      inf.setContentText("Faculty successfully deleted");
                      inf.show();
                    } else if(res == 1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Faculty ID does not exist");
                      inf.show();
                    } else if(res == -1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Some error occured. Faculty not deleted");
                      inf.show();
                    }
        
                    
                  } catch(IllegalArgumentException e) {
        
                  }
                  
                  
                }

              });

            });
          }

          @Override
          protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(buttonsBox);
            }
          }
        };
      }
    };
  }

  public static void openEditDialog(ActionEvent event, Faculty faculty){
    FXMLLoader loader = new FXMLLoader(App.class.getResource("edit-faculty.fxml"));

    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      Button btn = (Button) event.getSource();
      dialog.initOwner(btn.getScene().getWindow());
      Label facId = (Label) dp.lookup("#editFacId");
      TextField facName = (TextField) dp.lookup("#editFacName");
      ComboBox<String> facLoad = (ComboBox<String>) dp.lookup("#editFacLoad");

      facId.setText(String.valueOf(faculty.getId()));
      facName.setText(faculty.getName());
      facLoad.setValue(String.valueOf(faculty.getMaxLoad()));

      facLoad.getItems().addAll("30", "15");
      

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          if (facName.getText().isBlank() || facLoad.getValue().isBlank()){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
            return;
          }

          String name = facName.getText();
          int load = Integer.parseInt(facLoad.getValue());


          try{
            faculty.setName(name);
            faculty.setMaxLoad(load);
            int res = FacultyControllers.modifyFaculty(faculty);

            if (res == 0){
              updateTable(localFacultyTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Faculty successfully modified");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Faculty ID does not exist");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Faculty not modified");
              a.show();
            }

            
          } catch(IllegalArgumentException e) {

          }
        }
      });
        

    } catch (Exception e) {

    }

  }


  public static void openAddDialog(ActionEvent event, Stage stage, FXMLLoader loader, TableView<Faculty> facultyTbl){
    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      TextField addFacId = (TextField) dp.lookup("#addFacId");
      TextField addFacName = (TextField) dp.lookup("#addFacName");
      ComboBox<String> addFacLoad = (ComboBox<String>) dp.lookup("#addFacLoad");

      addFacLoad.getItems().addAll("30", "15");

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          if (addFacId.getText().isBlank() || addFacName.getText().isBlank() || addFacLoad.getValue().isBlank()){
            Alert a = new Alert(AlertType.ERROR);
              a.setContentText("All fields are required");
              a.show();
              return;
          }

          int facid = Integer.parseInt(addFacId.getText());
          String facname = addFacName.getText();
          int load = Integer.parseInt(addFacLoad.getValue());


          try{
            Faculty fac = new Faculty(facid, facname, load);
            int res = FacultyControllers.createFaculty(fac);

            if (res == 0){
              updateTable(facultyTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Faculty successfully added");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Faculty ID already exist");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Faculty not added to database");
              a.show();
            }

            
          } catch(IllegalArgumentException e) {

          }
        }
      });
    } catch(Exception e) {
    }
    
  }
  public static void updateTable(TableView<Faculty> facultyTbl){
    System.out.println("HELLO");
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("faculty");
    FacultyView.setFacCols(view, facultyTbl);
    App.subPane.setCenter(view);
  }
}
