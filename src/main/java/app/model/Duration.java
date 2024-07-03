package app.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Duration{
    public static final LocalTime EARLIEST_TIME = LocalTime.of(7, 30);
    public static final LocalTime LATEST_TIME = LocalTime.of(21, 00);
    private LocalTime start;
    private LocalTime end;


    public Duration(LocalTime start, LocalTime end){
        if(!start.isBefore(end)){
            throw new IllegalArgumentException("End time cannot be before start time");
        }

        if(start.isBefore(EARLIEST_TIME)){
            throw new IllegalArgumentException("Start time too early");
        }

        if(end.isAfter(LATEST_TIME)){
            throw new IllegalArgumentException("End time too late");
        }

        this.start = start;
        this.end = end;
    }
    
    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public String toString(){
        return String.format("%tR - %tR", start, end);
    }

    public float durationBetween(){
        return (float)(start.until(end, ChronoUnit.MINUTES))/60;
    }


    // Static methods
    /*
     * utility method to check if durations are overlapping
     */
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


}
