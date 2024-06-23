package app.model;

public class Course {
    private String code;
    private String desc;
    private int lecUnits;
    private int labUnits;
    private float hrsPerWeek;
    private int facultyId;


    public Course(String code, String desc, int lecUnits, int labUnits, int facultyId){
        this.code = code;
        this.desc = desc;
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
        
        this.hrsPerWeek = lecUnits + (labUnits*3);
        this.facultyId = facultyId;        
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

    public int getLabUnits() {
        return labUnits;
    }

    public int getLecUnits() {
        return lecUnits;
    }

    public float getHrsPerWeek() {
        return hrsPerWeek;
    }

    public int getFacultyId() {
        return facultyId;
    }

    @Override
    public String toString(){
        return String.format("%s - %s", code, desc);
    }

}
