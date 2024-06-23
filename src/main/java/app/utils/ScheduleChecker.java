package app.utils;

import java.time.LocalTime;
import java.util.ArrayList;

import app.controller.ScheduleController;
import app.model.Duration;
import app.model.Schedule;

public class ScheduleChecker {


    public static Boolean isOverlapping(Duration d1, Duration d2){
        LocalTime d1Start = d1.getStart();
        LocalTime d1End = d1.getEnd();

        LocalTime d2Start = d2.getStart();
        LocalTime d2End = d2.getEnd();

        if(d1Start.equals(d2Start) || d1End.equals(d2End)){
            return true;
        }

        if(d1Start.isBefore(d2Start) && d2Start.isBefore(d1End)){
            return true;
        }

        if(d2Start.isBefore(d1Start) && d1Start.isBefore(d2End)){
            return true;
        }
        
        return false;
    }


    public static Boolean isFacultyScheduled(Schedule schedule){
        int faculty = schedule.getFacultyId();
        String day = schedule.getDay();
        Duration duration = schedule.getDuration();

        ArrayList<Schedule> scheduleMatch = ScheduleController.getFacultySchedule(faculty, day);
        if(scheduleMatch == null){
            return false;
        }

        for(Schedule s: scheduleMatch){
            if(isOverlapping(duration, s.getDuration())){
                return true;
            }
        }
        return false;

    }


    public static Boolean isRoomOccupied(Schedule schedule){
        int room = schedule.getRoomId();
        String day = schedule.getDay();
        Duration duration = schedule.getDuration();

        ArrayList<Schedule> scheduleMatch = ScheduleController.getRoomSchedule(day, room);
        if(scheduleMatch == null){
            return false;
        }

        for(Schedule s: scheduleMatch){
            if(isOverlapping(duration, s.getDuration())){
                return true;
            }
        }
        return false;

    }


    public static Boolean isCourseScheduled(Schedule schedule){
        String course = schedule.getCourseCode();
        String day = schedule.getDay();
        Duration duration = schedule.getDuration();

        ArrayList<Schedule> scheduleMatch = ScheduleController.getCourseSchedule(day, course);
        if(scheduleMatch == null){
            return false;
        }

        for(Schedule s: scheduleMatch){
            if(isOverlapping(duration, s.getDuration())){
                return true;
            }
        }
        return false;

    }

    
}
