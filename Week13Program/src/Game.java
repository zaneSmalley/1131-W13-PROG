import com.sun.javaws.security.AppContextUtil;

/**
 * Last modified on 12/9/2019
 *
 * @author Zane Smalley
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */

// Keep track of the game state and set up relationship with rooms and items and such

public class Game {

    private Player player;
    private Room currentRoom;
    private Room start;

    public Game( Player player) {

        this.player = player;
        start = new Room("Main Courtyard");
        start.setRoomDescription( "Main Courtyard..." );

    }

    public void start(Player player) {



    }

}
