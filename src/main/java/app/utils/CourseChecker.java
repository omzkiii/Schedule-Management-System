package app.utils;

import java.util.ArrayList;

import app.controller.CourseControllers;
import app.controller.FacultyControllers;
import app.model.Course;
import app.model.Faculty;

public class CourseChecker {
    
    /*
     * checks if adding course will result to faculty exceeding max load
     */
    public static Boolean willExceedMaxLoad(Course course){
        Faculty faculty = FacultyControllers.getFaculty(course.getFacultyId());

        ArrayList<Course> courses = CourseControllers.getCourseFor(faculty.getId());

        if(courses.isEmpty()){
            return false;
        }

        float currentLoad = 0;
        for(Course c: courses){
            if(!(c.getCode().equals(course.getCode()))){
                currentLoad += c.getHrsPerWeek();
            }

        }
        System.out.println("Current Load: " + currentLoad);

        if((currentLoad+course.getHrsPerWeek()) > faculty.getMaxLoad()){
            return true;
        }

        return false;

    }


}
