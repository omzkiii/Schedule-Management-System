package app.view;

import app.controller.ScheduleController;
import app.model.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

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
    // actions.setCellFactory((setSchedBtn()));
    
    for(Schedule schedule: ScheduleController.getAllSchedule()){
      schedTbl.getItems().add(schedule);
    }

  }

  public static void loadSchedData(Pane view, TableView<Schedule> schedTbl){
    ObservableList<Schedule> data = FXCollections.observableArrayList();
    for(Schedule s: ScheduleController.getAllSchedule()){
      data.add(s);
    }
    schedTbl.getItems().addAll(data);

  }    

  
}
