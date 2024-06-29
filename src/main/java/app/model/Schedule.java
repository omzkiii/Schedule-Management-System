package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.controller.CourseControllers;
import app.controller.FacultyControllers;

public class Schedule{
    private int id;
    private int facultyId;
    private String day;
    private Duration duration;
    private String courseCode;
    private int roomId;
    private Faculty faculty;
    
    // Class fields
    private static final int ROOM_COUNT = 45;
    public static final List<String> DAYS = Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
    public static final List<String> ROOMS = generateRooms(ROOM_COUNT);


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

        this.faculty = FacultyControllers.getFaculty(this.facultyId);

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
        this.faculty = FacultyControllers.getFaculty(facultyId);
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

    public void setDay(String day) {
        if(!DAYS.contains(day.toUpperCase())){
            throw new IllegalArgumentException("Invalid day");
        }
        this.day = day.toUpperCase();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }


    public String getCourseCode(){
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        Course course = CourseControllers.getCourse(courseCode);
        if (course == null){
            throw new IllegalArgumentException("Course code does not exist");
        }

        this.courseCode = courseCode;
        this.facultyId = course.getFacultyId();
        this.faculty = FacultyControllers.getFaculty(this.facultyId);
    }


    public int getRoomId(){
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public String toString(){
        return String.format("%s, %tR - %tR - %s (Room %d, Faculty: %d)", day, duration.getStart(), duration.getEnd(), courseCode, roomId, facultyId);
    }


    private static ArrayList<String> generateRooms(int n){
        ArrayList<String> rooms = new ArrayList<>();
        int floor = 200;
        int room = 0;

        while(n != 0){
            if(room > 15) {
                floor += 100;
                room = 0;

            }

            int roomNum = floor + room;
            rooms.add(String.valueOf(roomNum));
            room++;
            n--;
        }

        return rooms;

    }

}
