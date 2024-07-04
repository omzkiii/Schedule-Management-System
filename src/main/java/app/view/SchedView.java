package app.view;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

import app.App;
import app.FxmlLoader;
import app.controller.CourseControllers;
import app.controller.ScheduleController;
import app.model.Course;
import app.model.Duration;
import app.model.Schedule;
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
  public static TableView<Schedule> localSchedTbl;
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
              openEditDialog(event, schedule);
            });

            delBtn.setOnAction(event -> {
              Schedule schedule = getTableView().getItems().get(getIndex());
              System.out.println("Delete button for: " + schedule.getId() + " " + schedule.getId());
              Alert a = new Alert(AlertType.CONFIRMATION);
              a.setContentText("Are you sure you want to delete schedule " + schedule);
              a.showAndWait().ifPresent((btnType) -> {
                if(btnType ==ButtonType.OK){
                  try{
                    int res = ScheduleController.removeSchedule(schedule);
        
                    if (res == 0){
                      updateTable(localSchedTbl);
                      Alert inf = new Alert(AlertType.INFORMATION);
                      inf.setContentText("Schedule successfully deleted");
                      inf.show();
                    } else if(res == 1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Schedule does not exist");
                      inf.show();
                    } else if(res == -1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Some error occured. Schedule not deleted");
                      inf.show();
                    }
        
                    
                  } catch(IllegalArgumentException e) {
                    System.out.println(e.getMessage());
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

  
  public static void openEditDialog(ActionEvent event, Schedule schedule){
    FXMLLoader loader = new FXMLLoader(App.class.getResource("edit-schedule.fxml"));

    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      Button btn = (Button) event.getSource();
      dialog.initOwner(btn.getScene().getWindow());
      Label schedId = (Label) dp.lookup("#editSched");
      ComboBox<String> schedCourse = (ComboBox<String>) dp.lookup("#editCourseCode");
      ComboBox<String> schedDay = (ComboBox<String>) dp.lookup("#editSchedDay");
      ComboBox<String> schedRoom = (ComboBox<String>) dp.lookup("#editRoomID");
      TextField schedStart = (TextField) dp.lookup("#editSchedStart");
      TextField schedEnd = (TextField) dp.lookup("#editSchedEnd");

      schedId.setText(String.valueOf(schedule.getId()));

      ArrayList<String> courses = new ArrayList<>();
      for(Course c: CourseControllers.getAllCourse()){
        courses.add(c.toString());
      }

      schedCourse.setValue(CourseControllers.getCourse(schedule.getCourseCode()).toString());
      schedCourse.getItems().addAll(courses);

      schedDay.setValue(schedule.getDay());
      schedDay.getItems().addAll(Schedule.DAYS);

      schedRoom.setValue(String.valueOf(schedule.getRoomId()));
      schedRoom.getItems().addAll(Schedule.ROOMS);

      schedStart.setText(schedule.getDuration().getStart().toString());
      schedEnd.setText(schedule.getDuration().getEnd().toString());
      
      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          String code = schedCourse.getValue().split(" ")[0];
          String day = schedDay.getValue();
          int room = Integer.parseInt(schedRoom.getValue());

          String rawStart = schedStart.getText();
          String rawEnd = schedEnd.getText();

          if(rawStart.isBlank() || rawEnd.isBlank()){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
            return;
          }

          String timeRegex1 = "[0-1]{0,1}\\d{1}:[0-5]{1}\\d{1}";
          String timeRegex2 = "[2]{1}[0-3]{1}:[0-5]{1}\\d{1}";

          if((!Pattern.matches(timeRegex1, rawStart) && !Pattern.matches(timeRegex2, rawStart))  || (!Pattern.matches(timeRegex1, rawEnd) && !Pattern.matches(timeRegex2, rawEnd))){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid time format");
            a.show();
            return;
          }

          String[] start = rawStart.split(":",2);
          String[] end = rawEnd.split(":",2);

          LocalTime time1 = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
          LocalTime time2 = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));

          try{
            Duration dur = new Duration(time1, time2);
            schedule.setCourseCode(code);
            schedule.setDay(day);
            schedule.setRoomId(room);
            schedule.setDuration(dur);

            int res = ScheduleController.modifySchedule(schedule);

            if (res == 0){
              updateTable(localSchedTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Schedule successfully modified");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Schedule does not exist");
              a.show();
            } else if(res == 2) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Max hours for course exceeded");
              a.show();
            } else if(res == 3) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Conflicting schedule for faculty assigned to course");
              a.show();
            } else if(res == 4) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Room already occupied for time provided");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Course not added to database");
              a.show();
            }

          } catch(IllegalArgumentException e) {
            Alert a = new Alert(AlertType.ERROR);
            switch (e.getMessage()) {
              case "End time cannot be before start time":
                a.setContentText("End time cannot be before start time");
                a.show();
                break;
              case "Start time too early":
                a.setContentText("Start time too early");
                a.show();
                break;
              case "End time too late":
                a.setContentText("End time too late");
                a.show();
                break;
              default:
                System.out.println(e.getMessage());
            }
          }


        }
      
      });

    } catch (Exception e){
      
    }


  }


  public static void openAddDialog(ActionEvent event, Stage stage, FXMLLoader loader){
    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      ComboBox<String> addSchedCourseCode = (ComboBox<String>) dp.lookup("#addSchedCourseCode");
      ComboBox<String> addSchedDay = (ComboBox<String>) dp.lookup("#addSchedDay");
      ComboBox<String> addSchedRoomId = (ComboBox<String>) dp.lookup("#addSchedRoomID");
      TextField addSchedStart = (TextField) dp.lookup("#addSchedStart");
      TextField addSchedEnd = (TextField) dp.lookup("#addSchedEnd");

      ArrayList<String> courses = new ArrayList<>();
      for(Course c: CourseControllers.getAllCourse()){
        courses.add(c.toString());
      }

      addSchedCourseCode.setValue(courses.getFirst().toString());
      addSchedCourseCode.getItems().addAll(courses);

      addSchedDay.setValue(Schedule.DAYS.get(1));
      addSchedDay.getItems().addAll(Schedule.DAYS);

      addSchedRoomId.setValue(Schedule.ROOMS.getFirst());
      addSchedRoomId.getItems().addAll(Schedule.ROOMS);

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          String code = addSchedCourseCode.getValue().split(" ")[0];
          String day = addSchedDay.getValue();
          int room = Integer.parseInt(addSchedRoomId.getValue());
          
          
          String rawStart = addSchedStart.getText();
          String rawEnd = addSchedEnd.getText();

          if(code.isBlank() || day.isBlank() || rawStart.isBlank() || rawEnd.isBlank()){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
            return;
          }

          String timeRegex1 = "[0-1]{0,1}\\d{1}:[0-5]{1}\\d{1}";
          String timeRegex2 = "[2]{1}[0-3]{1}:[0-5]{1}\\d{1}";

          if((!Pattern.matches(timeRegex1, rawStart) && !Pattern.matches(timeRegex2, rawStart))  || (!Pattern.matches(timeRegex1, rawEnd) && !Pattern.matches(timeRegex2, rawEnd))){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid time format");
            a.show();
            return;
          }

          String[] start = rawStart.split(":",2);
          String[] end = rawEnd.split(":",2);

          LocalTime time1 = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
          LocalTime time2 = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));

          try{
            Duration dur = new Duration(time1, time2);
            Schedule schedule = new Schedule(day, dur, code, room);
            int res = ScheduleController.createSchedule(schedule);

            if (res == 0){
              updateTable(localSchedTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Schedule successfully added");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Schedule already exist");
              a.show();
            } else if(res == 2) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Max hours for course exceeded");
              a.show();
            } else if(res == 3) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Conflicting schedule for faculty assigned to course");
              a.show();
            } else if(res == 4) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Room already occupied for time provided");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Course not added to database");
              a.show();
            }
          } catch(IllegalArgumentException e) {
            Alert a = new Alert(AlertType.ERROR);
            switch (e.getMessage()) {
              case "End time cannot be before start time":
                a.setContentText("End time cannot be before start time");
                a.show();
                break;
              case "Start time too early":
                a.setContentText("Start time too early");
                a.show();
                break;
              case "End time too late":
                a.setContentText("End time too late");
                a.show();
                break;
              default:
                System.out.println(e.getMessage());
            }
          }
        }
      });

    } catch(Exception e) {
    }
    
  }
  public static void updateTable(TableView<Schedule> schedTbl){
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("schedules");
    setSchedCols(view, schedTbl);
    App.subPane.setCenter(view);
  }
  
}
