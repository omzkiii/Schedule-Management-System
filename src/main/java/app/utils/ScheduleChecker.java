package app.utils;

import java.util.ArrayList;

import app.controller.CourseControllers;
import app.controller.ScheduleController;
import app.model.Course;
import app.model.Duration;
import app.model.Schedule;

public class ScheduleChecker {

    
    /*
     * utility method to check if course has not exceeded hrs per week
     */
    public static Boolean willCourseExceedHrs(Schedule schedule){
        String courseCode = schedule.getCourseCode();
        ArrayList<Schedule> scheduleMatch = ScheduleController.getCourseSchedule(courseCode);
        if(scheduleMatch.isEmpty()){
            return false;
        }    

        float currentHrs = 0;
        for(Schedule s: scheduleMatch){
            if(schedule.getId() != s.getId()){
                currentHrs += s.getDuration().durationBetween();
            }
        }
        float newHrs = currentHrs + schedule.getDuration().durationBetween();

        Course course = CourseControllers.getCourse(courseCode);

        if(newHrs > course.getHrsPerWeek()){
            return true;
        }

        return false;

    }

    public static Boolean courseHasSchedule(String courseCode){
        ArrayList<Schedule> scheduleMatch = ScheduleController.getCourseSchedule(courseCode);
        if(scheduleMatch.isEmpty()){
            return false;
        } else {
            return true;
        }
    }


    /*
     * utility method to check if faculty has conflicting schedule with the input schedule
     */

    public static Boolean isFacultyScheduled(Schedule schedule){
        int faculty = schedule.getFacultyId();
        String day = schedule.getDay();
        Duration duration = schedule.getDuration();

        ArrayList<Schedule> scheduleMatch = ScheduleController.getFacultySchedule(faculty, day);
        if(scheduleMatch.isEmpty()){
            return false;
        }

        for(Schedule s: scheduleMatch){
            if(Duration.isOverlapping(duration, s.getDuration()) && schedule.getId() != s.getId()){
                return true;
            }
        }
        return false;

    }


    /*
     * utility method to check if room has conflicting schedule with the new schedule
     */
    public static Boolean isRoomOccupied(Schedule schedule){
        int room = schedule.getRoomId();
        String day = schedule.getDay();
        Duration duration = schedule.getDuration();

        ArrayList<Schedule> scheduleMatch = ScheduleController.getRoomSchedule(day, room);
        if(scheduleMatch.isEmpty()){
            return false;
        }

        for(Schedule s: scheduleMatch){
            if(Duration.isOverlapping(duration, s.getDuration()) && schedule.getId() != s.getId()){
                return true;
            }
        }
        return false;

    }

    
}
