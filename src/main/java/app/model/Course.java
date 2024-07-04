package app.model;

import app.controller.FacultyControllers;

public class Course {
    private String code;
    private String desc;
    private int lecUnits;
    private int labUnits;
    private float hrsPerWeek;
    private int facultyId;
    private Faculty faculty;


    public Course(String code, String desc, int lecUnits, int labUnits, int facultyId){
        
        if(lecUnits+labUnits == 0){
            throw new IllegalArgumentException("No units specified");
        }
        
        if(5 > lecUnits && lecUnits >= 0 && lecUnits != 1){
            this.lecUnits = lecUnits;
        } else {
            throw new IllegalArgumentException("Invalid lecture units. Valid values: 0, 2-4");
        }

        if(labUnits == 3 || labUnits == 1 || labUnits == 0){
            this.labUnits = labUnits;
        } else {
            throw new IllegalArgumentException("Invalid lab units. Valid values: 0, 1, 3");
        }

        Faculty faculty = FacultyControllers.getFaculty(facultyId);
        
        if(faculty == null){
            throw new IllegalArgumentException("Faculty ID does not exist");
        }

        this.code = code;
        this.desc = desc;
        this.hrsPerWeek = lecUnits + (labUnits*3);
        this.facultyId = facultyId;        
        this.faculty = FacultyControllers.getFaculty(facultyId);
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLabUnits() {
        return labUnits;
    }

    public void setLabUnits(int labUnits) {
        if(this.lecUnits+labUnits == 0){
            throw new IllegalArgumentException("No units specified");
        }

        if(labUnits == 3 || labUnits == 1 || labUnits == 0){
            hrsPerWeek -= this.labUnits*3;
            this.labUnits = labUnits;
            hrsPerWeek += this.labUnits*3;
        } else {
            throw new IllegalArgumentException("Invalid lab units. Valid values: 0, 1, 3");
        }

    }

    public int getLecUnits() {
        return lecUnits;
    }

    public void setLecUnits(int lecUnits) {
        if(this.labUnits+lecUnits == 0){
            throw new IllegalArgumentException("No units specified");
        }

        if(5 > lecUnits && lecUnits >= 0 && lecUnits != 1){
            hrsPerWeek -= this.lecUnits;
            this.lecUnits = lecUnits;
            hrsPerWeek += this.lecUnits;
        } else {
            throw new IllegalArgumentException("Invalid lecture units. Valid values: 0, 2-4");
        }   
    }

    public float getHrsPerWeek() {
        return hrsPerWeek;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFacultyId(int facultyId) {
        Faculty faculty = FacultyControllers.getFaculty(facultyId);
        
        if(faculty == null){
            throw new IllegalArgumentException("Faculty ID does not exist");
        }

        this.facultyId = facultyId;
        this.faculty = faculty;
    
    
    }

    @Override
    public String toString(){
        return String.format("%s - %s", code, desc);
    }

}
