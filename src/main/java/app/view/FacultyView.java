package app.view;

import app.controller.FacultyControllers;
import app.model.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class FacultyView {

  public static void setFacCols(Pane view, TableView<Faculty> facultyTbl){
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
            });

            delBtn.setOnAction(event -> {
              Faculty faculty = getTableView().getItems().get(getIndex());
              System.out.println("Delete button for: " + faculty.getId() + " " + faculty.getName());
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


  public static void loadFacData(Pane view, TableView<Faculty> facultyTbl){
    ObservableList<Faculty> data = FXCollections.observableArrayList();
    for(Faculty f: FacultyControllers.getAllFaculty()){
      data.add(f);
    }
    facultyTbl.getItems().addAll(data);
  }
}
