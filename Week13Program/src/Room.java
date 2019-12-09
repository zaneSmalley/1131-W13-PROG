import java.util.ArrayList;

/**
 * Date last modified: 12/9/2019
 *
 * @author Zane Smalley
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Room {
    private String roomDescription;
    private String roomName;

    public Room(String roomName) {
        setRoomName(roomName);
    }

    private Room roomNorth = null;
    private Room roomSouth = null;
    private Room roomWest = null;
    private Room roomEast = null;

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomEast(Room roomEast) {
        this.roomEast = roomEast;
    }

    public void setRoomNorth(Room roomNorth) {
        this.roomNorth = roomNorth;
    }

    public void setRoomSouth(Room roomSouth) {
        this.roomSouth = roomSouth;
    }

    public void setRoomWest(Room roomWest) {
        this.roomWest = roomWest;
    }
}
