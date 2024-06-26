package app.view;

import app.controller.CourseControllers;
import app.model.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
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

public class CourseView {
public static void setCourseCols(Pane view, TableView<Course> courseTbl ){
    courseTbl = (TableView<Course>) view.lookup("#courseTable");
    TableColumn<Course, String> codeCol = (TableColumn<Course, String>) courseTbl.getColumns().get(0);
    codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
    
    TableColumn<Course, String> desCol = (TableColumn<Course, String>) courseTbl.getColumns().get(1);
    desCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
    
    TableColumn<Course, String> lecUnitsCol = (TableColumn<Course, String>) courseTbl.getColumns().get(2);
    lecUnitsCol.setCellValueFactory(new PropertyValueFactory<>("lecUnits"));
    
    TableColumn<Course, String> labUnitsCol = (TableColumn<Course, String>) courseTbl.getColumns().get(3);
    labUnitsCol.setCellValueFactory(new PropertyValueFactory<>("labUnits"));


    TableColumn<Course, String> hpweekCol = (TableColumn<Course, String>) courseTbl.getColumns().get(4);
    hpweekCol.setCellValueFactory(new PropertyValueFactory<>("hrsPerWeek"));
    
    TableColumn<Course, String> facultyCol = (TableColumn<Course, String>) courseTbl.getColumns().get(5);
    facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));

    TableColumn<Course, Void> actionCol = (TableColumn<Course, Void>) courseTbl.getColumns().get(6);
    actionCol.setCellFactory(setCourseButton());

    for (Course course : CourseControllers.getAllCourse()) {
      courseTbl.getItems().add(course);
    }
  }

  private static Callback<TableColumn<Course, Void>, TableCell<Course, Void>> setCourseButton() {
    return new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
      @Override
      public TableCell<Course, Void> call(TableColumn<Course, Void> param) {
        return new TableCell<Course, Void>() {
          private final Button editButton = new Button("Edit");
          private final Button deleteButton = new Button("Delete");
          private final HBox hbox = new HBox(editButton, deleteButton);
        {
            editButton.setOnAction(event -> {
              // TODO: display edit pane
            });
            deleteButton.setOnAction(event -> {
              String course = getTableView().getItems().get(getIndex()).getCode();
              CourseControllers.removeCourse(course);
            });
          }

          @Override
          protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : hbox);
          }
        };
      }
    };
  }

  public static void openAddDialog(ActionEvent event, Stage stage, FXMLLoader loader){
    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      TextField addCourseCode = (TextField) dp.lookup("#addCourseCode");
      TextField addCourseDesc = (TextField) dp.lookup("#addCourseDesc");
      Spinner<Integer> addLecUnits = (Spinner<Integer>) dp.lookup("#addLecUnits");
      Spinner<Integer> addLabUnits = (Spinner<Integer>) dp.lookup("#addLabUnits");
      Spinner<Integer> addHPWeek = (Spinner<Integer>) dp.lookup("#addHPWeek");
      ComboBox<String> addFaculty = (ComboBox<String>) dp.lookup("#addFaculty");

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          String code = addCourseCode.getText();
          String desc = addCourseDesc.getText();
          int lecUnits = addLecUnits.getValue();
          int labUnits = addLabUnits.getValue();
          String faculty = addFaculty.getValue();
          int facultyId = Integer.parseInt(faculty.strip().replaceAll("^\\D*(\\d+).*", "$1"));
          
          try{
            Course course = new Course(code, desc, lecUnits, labUnits, facultyId);
            System.out.println(course);
          } catch(IllegalArgumentException e) {

          }
        }
      });
    } catch(Exception e) {
    }
    
  }
  
}
