package app.model;

import java.time.LocalTime;

public class Schedule{
    private int id;
    private int facultyId;
    private String day;
    private LocalTime start;
    private LocalTime end;
    private int courseCode;
    private int roomId;


    public Schedule(int facultyId, String day, LocalTime start, LocalTime end, int courseCode, int roomId){
        this.facultyId = facultyId;
        this.day = day;
        this.start = start;
        this.end = end;
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

    public LocalTime getStart(){
        return start;
    }


    public LocalTime getEnd(){
        return end;
    }


    public int getCourseCode(){
        return courseCode;
    }


    public int getRoomId(){
        return roomId;
    }

}
