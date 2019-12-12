import java.util.ArrayList;

/**
 * Last modified on 12/9/2019
 *
 * @author Zane Smalley
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Player {
    public long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(long connectionId) {
        this.connectionId = connectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    private long connectionId;
    private String name;

    private Room currentRoom;

    private ArrayList<Item> inventory;

    public Player(long connectionId, String name, Room startingRoom) {
        setConnectionId(connectionId);
        setName(name);
        setCurrentRoom(startingRoom);
    }
}
