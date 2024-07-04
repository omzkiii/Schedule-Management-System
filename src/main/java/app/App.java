package app;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ListIterator;

import app.controller.Controllers;
import app.controller.FacultyControllers;
import app.controller.Queries;
import app.controller.ScheduleController;
import app.controller.CourseControllers;
import app.model.Faculty;
import app.model.Schedule;
import app.utils.ScheduleChecker;
import app.model.Course;
import app.model.Duration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import app.view.SimulateView;

import java.io.IOException;

public class App extends Application {
  public static BorderPane subPane;
  @Override
  public void start(Stage primaryStage) throws IOException {
    AppController app = new AppController();
    primaryStage.setOnHidden((e) -> {
      if (SimulateView.backgroundTask != null) {
        SimulateView.backgroundTask.cancel();
      }
    });;
    app.start(primaryStage);
  }


  public static void main(String[] args) {

    /*For Testing */
  
    /*
     * Testing Faculty functions
     */
    // Creating
    // Faculty faculty = new Faculty(2025, "Chappell Roan", 30);
    // FacultyControllers.createFaculty(faculty);


    // Modifying
    // Faculty faculty = FacultyControllers.getFaculty(2025);
    // faculty.setMaxLoad(30);
    // faculty.setName("Kayleigh");

    // Faculty faculty = new Faculty(2025, "Sabrina", 15);
    // System.out.println("Modify result: " + FacultyControllers.modifyFaculty(faculty));


    // Deleting
    // System.out.println("Delete result: " + FacultyControllers.removeFaculty(2025));


    // Selecting
    // for(Faculty fac: FacultyControllers.getAllFaculty()){
    //   System.out.println(fac);
    // }



    /*
     * Testing Course functions
     */
    // Creating
    // Course course = new Course("CCS316", "Parallel", 3, 0, 12323);
    // System.out.println("Create res: " + CourseControllers.createCourse(course));

    // Modifying
    // Course course = CourseControllers.getCourse("CCS311");
    // course.setLabUnits(1);
    // course.setLecUnits(0);
    // course.setFacultyId(15523);

    // Course course = new Course("CCS200", "OOP", 3, 0, 124423);
    // System.out.println("Modify res: " + CourseControllers.modifyCourse(course));


    // Deleting
    // System.out.println("Deleting res: " + CourseControllers.removeCourse("CCS315"));
    

    // Selecting
    // for(Course course: CourseControllers.getCourseFor(15523)){
    //   System.out.println(course);
    // }

    // for(Course course: CourseControllers.getAllCourse()){
    //   System.out.println(course);
    // }



    /*
     * Testing Schedule functions 
     */

    // DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    // CourseControllers.removeCourse("CS302");
    // FacultyControllers.removeFaculty(12121);
    // ArrayList<Course> scheds = CourseControllers.getAllCourse();
    // ListIterator<Course> iterator = scheds.listIterator();

    // while (iterator.hasNext()) {
    //   System.out.println(iterator.next().getDesc());
    // }


    // Checking Duration
    // LocalTime time1 = LocalTime.of(21, 30);
    // LocalTime time2 = LocalTime.of(22, 30);

    // LocalTime time3 = LocalTime.of(12, 00);
    // LocalTime time4 = LocalTime.of(13, 30);

    // Duration d1 = new Duration(time1, time2);
    // Duration d2 = new Duration(time3, time4);

    // Creating
    // Schedule schedule1 = new Schedule("wednesday", d1, "CCS314", 212);
    // System.out.println(schedule1);
    // Schedule schedule2 = new Schedule(Schedule.DAYS.get(2), d2, "CCS303", 221);
    // System.out.println(schedule2);
    // Schedule schedule3 = new Schedule(Schedule.DAYS.get(2), d2, "CCS310", 221);

    // System.out.println("Adding schedule res: " + ScheduleController.createSchedule(schedule1));

    // Modifying
    // Schedule schedule = new Schedule(5, 1213, "MONDAY", d2, "CCS305", 221);
    // schedule.setDuration(d1);
    // schedule.setDay("tuesday");

    // System.out.println("Modifying res: " + ScheduleController.modifySchedule(schedule));

    // Deleting
    // Schedule schedule = new Schedule(5, 1213, "MONDAY", d2, "CCS305", 221);
    // System.out.println("Deleting res: " + ScheduleController.removeSchedule(schedule));

    // Selecting
    // for(Schedule sched: ScheduleController.getFacultySchedule(1213, "TUESDAY")){
    //   System.out.println(sched);
    // }

    // for(Schedule sched: ScheduleController.getRoomSchedule("TUESDAY", 221)){
    //   System.out.println(sched);
    // }

    // for(Schedule sched: ScheduleController.getCourseSchedule("CCS303")){
    //   System.out.println(sched);
    // }

    // for(Schedule sched: ScheduleController.getAllSchedule()){
    //   System.out.println(sched);
    // }
  
    /*End For Testing */
    
    launch(args);
  
  }
}
