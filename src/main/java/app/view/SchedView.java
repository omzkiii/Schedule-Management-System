package app.view;

import app.controller.ScheduleController;
import app.model.Course;
import app.model.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

public class SchedView {
  public static void setSchedCols(Pane view, TableView<Schedule> schedTbl){
    schedTbl = (TableView<Schedule>) view.lookup("#schedList");
    TableColumn<Schedule, String> day = (TableColumn<Schedule, String>) schedTbl.getColumns().get(0);
    TableColumn<Schedule, String> time = (TableColumn<Schedule, String>) schedTbl.getColumns().get(1);
    TableColumn<Schedule, String> course = (TableColumn<Schedule, String>) schedTbl.getColumns().get(2);
    TableColumn<Schedule, String> faculty =  (TableColumn<Schedule, String>) schedTbl.getColumns().get(3);
    TableColumn<Schedule, Integer> room =  (TableColumn<Schedule, Integer>) schedTbl.getColumns().get(4);
    TableColumn<Schedule, Void> actions =  (TableColumn<Schedule, Void>) schedTbl.getColumns().get(5);

    day.setCellValueFactory(new PropertyValueFactory<>("day"));
    time.setCellValueFactory(new PropertyValueFactory<>("duration"));
    course.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
    faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
    room.setCellValueFactory(new PropertyValueFactory<>("roomId"));
    actions.setCellFactory((setSchedBtn()));
    
    for(Schedule schedule: ScheduleController.getAllSchedule()){
      schedTbl.getItems().add(schedule);
    }

  }


  public static Callback<TableColumn<Schedule, Void>, TableCell<Schedule, Void>> setSchedBtn() {
    return new Callback<TableColumn<Schedule, Void>, TableCell<Schedule, Void>>() {
      @Override
      public TableCell<Schedule, Void> call(TableColumn<Schedule, Void> param) {
        return new TableCell<Schedule, Void>() {
          private final Button editBtn = new Button("Edit");
          private final Button delBtn = new Button("Delete");
          private final HBox buttonsBox = new HBox(editBtn, delBtn);

        {
            editBtn.setOnAction(event -> {
              Schedule schedule = getTableView().getItems().get(getIndex());
              System.out.println("Edit button for: " + schedule.getId() + " " + schedule.getId());
            });

            delBtn.setOnAction(event -> {
              Schedule schedule = getTableView().getItems().get(getIndex());
              System.out.println("Delete button for: " + schedule.getId() + " " + schedule.getId());
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

  public static void openAddDialog(ActionEvent event, Stage stage, FXMLLoader loader){
    System.out.println("JOY TO THE WORLD");
    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      dialog.show();
      // TODO: UPDATE DATA
      // TextField addSched = (TextField) dp.lookup("#addCourseCode");
      // TextField addCourseDesc = (TextField) dp.lookup("#addCourseDesc");
      // Spinner<Integer> addLecUnits = (Spinner<Integer>) dp.lookup("#addLecUnits"); 
      // Spinner<Integer> addLabUnits = (Spinner<Integer>) dp.lookup("#addLabUnits");
      // Spinner<Integer> addHPWeek = (Spinner<Integer>) dp.lookup("#addHPWeek");
      // ComboBox<String> addFaculty = (ComboBox<String>) dp.lookup("#addFaculty");
      //
      // dialog.showAndWait().ifPresent((btnType) -> {
      //   if(btnType ==ButtonType.OK){
      //     String code = addSched.getText();
      //     String desc = addCourseDesc.getText();
      //     int lecUnits = addLecUnits.getValue();
      //     int labUnits = addLabUnits.getValue();
      //     String faculty = addFaculty.getValue();
      //     int facultyId = Integer.parseInt(faculty.strip().replaceAll("^\\D*(\\d+).*", "$1"));
      //     
      //     try{
      //       Course course = new Course(code, desc, lecUnits, labUnits, facultyId);
      //       System.out.println(course);
      //     } catch(IllegalArgumentException e) {
      //
      //     }
      //   }
      // });
    } catch(Exception e) {
    }
    
  }
  
}
