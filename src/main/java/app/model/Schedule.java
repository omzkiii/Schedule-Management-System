package app.model;

import java.time.LocalTime;

public class Schedule{
    private int id;
    private int facultyId;
    private String day;
    private Duration duration;
    private String courseCode;
    private int roomId;


    public Schedule(int id, int facultyId, String day, Duration duration, String courseCode, int roomId){
        this.id = id;
        this.facultyId = facultyId;
        this.day = day;
        this.duration = duration;
        this.courseCode = courseCode;
        this.roomId = roomId;
    }

    public Schedule(int facultyId, String day, Duration duration, String courseCode, int roomId){
        this.id = 0;
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

}
