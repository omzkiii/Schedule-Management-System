package app.model;

import java.time.LocalTime;

public class Duration {
    private LocalTime start;
    private LocalTime end;


    public Duration(LocalTime start, LocalTime end){
        if(!start.isBefore(end)){
            throw new IllegalArgumentException();
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

}
