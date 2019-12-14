import java.io.Serializable;
import java.util.ArrayList;

/**
 * Date last modified: 12/14/2019
 *
 * @author Zane Smalley, Cooper Tyson, Jack Grantham, Tom Clark
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Room implements Serializable {
    private String roomDescription;
    private String roomName;

    public ArrayList<Item> getItems() {
        return items;
    }

    private ArrayList<Item> items;

    public Room(String roomName) {
        setRoomName(roomName);
        items = new ArrayList<>();
    }

    private Room roomNorth = null;
    private Room roomSouth = null;
    private Room roomWest = null;
    private Room roomEast = null;

    public Room getRoomNorth() {
        return roomNorth;
    }

    public Room getRoomSouth() {
        return roomSouth;
    }

    public Room getRoomWest() {
        return roomWest;
    }

    public Room getRoomEast() {
        return roomEast;
    }

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

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public String printItems() {
        return items.toString();
    }

    public String printDescription() {
        return roomDescription;
    }
}
