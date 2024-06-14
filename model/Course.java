package model;

public class Course {
    private String code;
    private String desc;

    public Course(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }


}