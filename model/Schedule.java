package model;

import java.time.LocalDateTime;

public class Schedule{
    private int id;
    private int facultyId;
    private LocalDateTime start;
    private LocalDateTime end;
    private int courseCode;
    private int roomId;


    public Schedule(int facultyId, LocalDateTime start, LocalDateTime end, int courseCode, int roomId){
        this.facultyId = facultyId;
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


    public LocalDateTime getStart(){
        return start;
    }


    public LocalDateTime getEnd(){
        return end;
    }


    public int getCourseCode(){
        return courseCode;
    }


    public int getRoomId(){
        return roomId;
    }

}