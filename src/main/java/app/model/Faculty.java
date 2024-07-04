package app.model;

import app.utils.CourseChecker;

public class Faculty{
  // Full time - 8 hrs per day: 6 hrs for classes, 2 hrs for admin (6*5days)
  // Part time - 4 hrs per day: 3 hrs for classes, 1 hr for admin (3*5days)
  public static final int MAX_HRS_LOAD_FULL = 30;
  public static final int MAX_HRS_LOAD_PART = 15;
  private int id;
  private String name;
  private float currentLoad;
  private int maxLoad;

  public Faculty(int id, String name, int maxLoad){
    this.id = id;
    this.name = name;
    if(maxLoad == MAX_HRS_LOAD_FULL || maxLoad == MAX_HRS_LOAD_PART){
      this.maxLoad = maxLoad;
    } else {
      throw new IllegalArgumentException("Invalid Max Load Value. Acceptable values: 30, 15");
    }
    this.currentLoad = 0;

  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public float getCurrentLoad() {
    return currentLoad;
  }

  public void setCurrentLoad(float currentLoad){
    this.currentLoad = currentLoad;
  }

  public int getMaxLoad() {
    return maxLoad;
  }

  public void setMaxLoad(int maxLoad) {
    if(maxLoad == MAX_HRS_LOAD_FULL || maxLoad == MAX_HRS_LOAD_PART){
      this.maxLoad = maxLoad;
    } else {
      throw new IllegalArgumentException("Invalid Max Load Value. Acceptable values: 30, 15");
    }
  }

  @Override
  public String toString(){
    return String.format("%d - %s", id, name);
  }

}
