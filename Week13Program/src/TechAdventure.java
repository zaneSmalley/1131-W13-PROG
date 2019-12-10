/**
 * Last modified on 12/9/2019
 *
 * @author Zane Smalley
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class TechAdventure implements ConnectionListener {
   AdventureServer adventureServer = null;
   private static int port = 2112;


    public TechAdventure() {
        adventureServer = new AdventureServer();
        adventureServer.setOnTransmission(this);
    }

    private void start(int port) {
        adventureServer.startServer(port);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        TechAdventure techAdventure = new TechAdventure();
        techAdventure.start(port);
    }

    @Override
    public void handle(ConnectionEvent e) {

    }
}
