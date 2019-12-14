import java.io.Serializable;
import java.util.ArrayList;

/**
 * Last modified on 12/14/2019
 *
 * @author Zane Smalley, Cooper Tyson, Jack Grantham, Tom Clark
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class Player implements Serializable {
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

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    private ArrayList<Item> inventory;

    public Player(long connectionId) {
        setConnectionId(connectionId);
        inventory = new ArrayList<>();
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public String printInventory() {
        return inventory.toString();
    }

    public void dropItem(Item item) {
        inventory.remove(item);
    }
}
