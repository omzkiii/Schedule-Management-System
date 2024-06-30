package app.view;

import java.util.ArrayList;

import app.App;
import app.FxmlLoader;
import app.controller.CourseControllers;
import app.controller.FacultyControllers;
import app.model.Course;
import app.model.Faculty;
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
import javafx.scene.control.SpinnerValueFactory;
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
  public static TableView<Course> localCourseTbl;

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
    
    localCourseTbl = courseTbl;

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
              Course course = getTableView().getItems().get(getIndex());
              openEditDialog(event, course);
            });

            deleteButton.setOnAction(event -> {
              String course = getTableView().getItems().get(getIndex()).getCode();
              Alert a = new Alert(AlertType.CONFIRMATION);
              a.setContentText("Are you sure you want to delete course " + course);
              a.showAndWait().ifPresent((btnType) -> {
                if(btnType ==ButtonType.OK){
                  try{
                    int res = CourseControllers.removeCourse(course);

                    if (res == 0){
                      Alert inf = new Alert(AlertType.INFORMATION);
                      inf.setContentText("Course successfully deleted");
                      inf.show();
                    } else if(res == 1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Course code does not exist");
                      inf.show();
                    } else if(res == -1){
                      Alert inf = new Alert(AlertType.ERROR);
                      inf.setContentText("Some error occured. Course not deleted");
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
            setGraphic(empty ? null : hbox);
          }
        };
      }
    };
  }

  public static void openEditDialog(ActionEvent event, Course course){
    FXMLLoader loader = new FXMLLoader(App.class.getResource("edit-course.fxml"));

    try{
      Dialog<ButtonType> dialog = new Dialog<>();
      DialogPane dp = loader.load();
      dialog.setDialogPane(dp);
      dialog.initModality(Modality.APPLICATION_MODAL);
      Button btn = (Button) event.getSource();
      dialog.initOwner(btn.getScene().getWindow());
      Label courseCode = (Label) dp.lookup("#editCourseCode");
      TextField editCourseDesc = (TextField) dp.lookup("#editCourseDesc");
      Spinner<Integer> editLecUnits = (Spinner<Integer>) dp.lookup("#editLecUnits"); 
      Spinner<Integer> editLabUnits = (Spinner<Integer>) dp.lookup("#editLabUnits");
      ComboBox<String> editFaculty = (ComboBox<String>) dp.lookup("#editFacID");

      courseCode.setText(course.getCode());
      editCourseDesc.setText(course.getDesc());
      editLecUnits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, course.getLecUnits()));
      editLabUnits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, course.getLabUnits()));

      ArrayList<String> faculties = new ArrayList<>();
      for(Faculty f: FacultyControllers.getAllFaculty()){
        faculties.add(f.toString());
      }

      editFaculty.setValue(course.getFaculty().toString());
      editFaculty.getItems().addAll(faculties);

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          String desc = editCourseDesc.getText();
          int lecUnits = editLecUnits.getValue();
          int labUnits = editLabUnits.getValue();
          String faculty = editFaculty.getValue().strip().replaceAll("^\\D*(\\d+).*", "$1");

          if(desc.isBlank() || (lecUnits == 0 && labUnits == 0) || faculty.isBlank()){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
            return;
          }

          if(!(5 > lecUnits && lecUnits >= 0 && lecUnits != 1)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid lecture units. Valid values: 0, 2-4");
            a.show();
            return;
          }

          if(!(labUnits == 3 || labUnits == 1 || labUnits == 0)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid lab units. Valid values: 0, 1, 3");
            a.show();
            return;
          }

          int facultyId = Integer.parseInt(faculty);

          course.setDesc(desc);
          course.setLecUnits(lecUnits);
          course.setLabUnits(labUnits);
          course.setFacultyId(facultyId);

          try{
            int res = CourseControllers.modifyCourse(course);

            if (res == 0){
              updateTable(localCourseTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Course successfully modified");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Course code does not exist");
              a.show();
            } else if(res == 2) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Max load for faculty no. " + faculty + " exceeded");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Course not modified");
              a.show();
            }

          } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }




        }
      });


    } catch (Exception e) {

    }

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
      ComboBox<String> addFaculty = (ComboBox<String>) dp.lookup("#addFaculty");

      addLecUnits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4));
      addLabUnits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3));

      ArrayList<String> faculties = new ArrayList<>();
      for(Faculty f: FacultyControllers.getAllFaculty()){
        faculties.add(f.toString());
      }
      addFaculty.setValue(faculties.getFirst().toString());

      addFaculty.getItems().addAll(faculties);

      dialog.showAndWait().ifPresent((btnType) -> {
        if(btnType ==ButtonType.OK){
          String code = addCourseCode.getText();
          String desc = addCourseDesc.getText();
          int lecUnits = addLecUnits.getValue();
          int labUnits = addLabUnits.getValue();
          String faculty = addFaculty.getValue().strip().replaceAll("^\\D*(\\d+).*", "$1");

          if(code.isBlank() || desc.isBlank() || (lecUnits == 0 && labUnits == 0) || faculty.isBlank()){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
            return;
          }

          if(!(5 > lecUnits && lecUnits >= 0 && lecUnits != 1)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid lecture units. Valid values: 0, 2-4");
            a.show();
            return;
          }

          if(!(labUnits == 3 || labUnits == 1 || labUnits == 0)){
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Invalid lab units. Valid values: 0, 1, 3");
            a.show();
            return;
          }

          int facultyId = Integer.parseInt(faculty);

          try{
            Course course = new Course(code, desc, lecUnits, labUnits, facultyId);
            int res = CourseControllers.createCourse(course);

            if (res == 0){
              updateTable(localCourseTbl);
              Alert a = new Alert(AlertType.INFORMATION);
              a.setContentText("Course successfully added");
              a.show();
            } else if(res == 1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Course code already exist");
              a.show();
            } else if(res == 2) {
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Max load for faculty no. " + faculty + " exceeded");
              a.show();
            } else if(res == -1){
              Alert a = new Alert(AlertType.ERROR);
              a.setContentText("Some error occured. Course not added to database");
              a.show();
            }

          } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }

        }
      });
    } catch(Exception e) {
    }

  }
  public static void updateTable(TableView<Course> courseTbl){
    FxmlLoader object = new FxmlLoader();
    Pane view = object.getPage("course");
    setCourseCols(view, courseTbl);
    App.subPane.setCenter(view);
  }

}
