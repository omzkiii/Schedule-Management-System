public class Classroom{
    private int room_id;
    private String building;

    public Classroom(int room_id, String building){
        this.room_id = room_id;
        this.building = building;
    }

    public int getRoomId(){
        return room_id;
    }

    public String getBuilding(){
        return building;
    }

}