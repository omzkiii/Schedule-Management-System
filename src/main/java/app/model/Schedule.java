package app.model;

import java.util.Arrays;
import java.util.List;

import app.controller.CourseControllers;

public class Schedule{
    private int id;
    private int facultyId;
    private String day;
    private Duration duration;
    private String courseCode;
    private int roomId;
    public static final List<String> DAYS = Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");


    /*
     * Constructor for initial schedule creation
     */
    public Schedule(String day, Duration duration, String courseCode, int roomId){
        this.id = 0;

        Course course = CourseControllers.getCourse(courseCode);
        if (course == null){
            throw new IllegalArgumentException("Course code does not exist");
        }
 
        if(!DAYS.contains(day.toUpperCase())){
            throw new IllegalArgumentException("Invalid day");
        }
        

        this.facultyId = course.getFacultyId();
        this.day = day.toUpperCase();
        this.duration = duration;
        this.courseCode = courseCode;
        this.roomId = roomId;
    }


    /*
     * Constructor for schedule from db
     */
    public Schedule(int id, int facultyId, String day, Duration duration, String courseCode, int roomId){
        this.id = id;
        this.facultyId = facultyId;
        this.day = day;
        this.duration = duration;
        this.courseCode = courseCode;
        this.roomId = roomId;
    }



    
    public int getId(){
        return id;
    }


    public int getFacultyId(){
        return facultyId;
    }

    public String getDay(){
        return day;
    }

    public Duration getDuration() {
        return duration;
    }


    public String getCourseCode(){
        return courseCode;
    }


    public int getRoomId(){
        return roomId;
    }

    @Override
    public String toString(){
        return String.format("%s, %tR - %tR - %s (Room %d, Faculty: %d)", day, duration.getStart(), duration.getEnd(), courseCode, roomId, facultyId);
    }

}
