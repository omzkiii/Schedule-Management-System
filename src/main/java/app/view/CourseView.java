package app.view;

import app.controller.CourseControllers;
import app.model.Course;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

  
}
