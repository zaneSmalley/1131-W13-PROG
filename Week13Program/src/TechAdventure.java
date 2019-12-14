import java.io.*;
import java.util.Scanner;

/**
 * Last modified on 12/14/2019
 *
 * @author Zane Smalley, Cooper Tyson, Jack Grantham, Tom Clark
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */
public class TechAdventure implements ConnectionListener {
    private AdventureServer adventureServer = null;
    private static int port = 2112;
    private Game currentGame;
    private Player currentPlayer;
    private File saveFile = new File("savedata.dat");
    private static final String openingMessage =
                    "Today is a sunny day in the village of Cullfield, and your best friend has challenged you to a duel!" +
                    " He’s a lot stronger than you are, so you’re going to have to explore the town to gather items" +
                    " and companions to take him on in an all out battle of Make Believe!\n" +
                    "\n" +
                    "After getting dressed and eating a healthy breakfast, as that’s the most important meal of the day," +
                    " you take stock of your current situation. Simply put, at your current level, your friend" +
                    " will destroy you. He could hit you with approximately 14% of his total power and it will" +
                    " still send you flying to the ground. So, to remedy this enormous gap, you’ll need to find" +
                    " some items to protect yourself and amplify your abilities. You figure that a “sword”, or " +
                    "wooden stick, will be enough on the attack side to make your blows have more oomph to them," +
                    " and a wooden shield of some sort would be a huge help in keeping yourself protected from" +
                    " his heavy hitting attacks. With both of those items, you may even be able to win!\n";


    private static final String helpMenu =
                    "Get [Item]: Adds the given item to your inventory\n" +
                    "\n" +
                    "Drop [Item]: Removes the item from your inventory, either by just dropping it in that room or giving it to another npc to advance the game.\n" +
                    "\n" +
                    "Go [Direction]: Allows you to move between rooms, in any of the four directions NORTH, SOUTH, EAST, and WEST.\n" +
                    "\n" +
                    "Inventory: Shows the player the items they currently have in their inventory\n" +
                    "\n" +
                    "Save: Saves the game\n" +
                    "\n" +
                    "Restore: Loads the most recently saved state of the game\n" +
                    "\n" +
                    "Look: Gives the room description. \n" +
                    "\n" +
                    "Look [Item]: Gives a description of the room or part of the room your in.\n" +
                    "\n" +
                    "Quit: Turns off the game\n" +
                    "\n" +
                    "Story: Prints the opening plot message.";

    private static final String youWin =
            "\n\n\n" +
            "Y88b   d88P                   888       888 d8b          \n" +
            " Y88b d88P                    888   o   888 Y8P          \n" +
            "  Y88o88P                     888  d8b  888              \n" +
            "   Y888P  .d88b.  888  888    888 d888b 888 888 88888b.  \n" +
            "    888  d88\"\"88b 888  888    888d88888b888 888 888 \"88b \n" +
            "    888  888  888 888  888    88888P Y88888 888 888  888 \n" +
            "    888  Y88..88P Y88b 888    8888P   Y8888 888 888  888 \n" +
            "    888   \"Y88P\"   \"Y88888    888P     Y888 888 888  888 \n";

    private static final String youLose =
            "\n\n\n" +
            "Y88b   d88P                   888                                \n" +
            " Y88b d88P                    888                                \n" +
            "  Y88o88P                     888                                \n" +
            "   Y888P  .d88b.  888  888    888      .d88b.  .d8888b   .d88b.  \n" +
            "    888  d88\"\"88b 888  888    888     d88\"\"88b 88K      d8P  Y8b \n" +
            "    888  888  888 888  888    888     888  888 \"Y8888b. 88888888 \n" +
            "    888  Y88..88P Y88b 888    888     Y88..88P      X88 Y8b.     \n" +
            "    888   \"Y88P\"   \"Y88888    88888888 \"Y88P\"   88888P'  \"Y8888  \n";


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
        System.out.println(String.format("connectionId=%d, data=%s", e.getConnectionID(), e.getData()));
        try {
            switch (e.getCode()) {
                case CONNECTION_ESTABLISHED:
                    currentPlayer = new Player(e.getConnectionID());
                    currentGame = new Game(currentPlayer);
                    adventureServer.sendMessage(e.getConnectionID(), openingMessage);
                    Thread.sleep(1000);
                    adventureServer.sendMessage(e.getConnectionID(), parseCommand("LOOK"));
                    break;
                case TRANSMISSION_RECEIVED:
                    adventureServer.sendMessage(e.getConnectionID(), parseCommand(e.getData()));
                    if (currentPlayer.getCurrentRoom() == currentGame.mainCourtyard &&
                            currentPlayer.getInventory().contains(currentGame.shield) &&
                            currentPlayer.getInventory().contains(currentGame.sword)) {
                        adventureServer.sendMessage(currentPlayer.getConnectionId(), youWin +
                                "\n\n\nYou now have the supplies to have an epic battle with your friend!\n" +
                                "Enjoy your make believe!\n" +
                                "If you wish to play again, quit and reconnect");
                    }
                    break;
                case CONNECTION_TERMINATED:
                    currentPlayer = null;
                    currentGame = null;
                    break;
                default:
                    // Since the switch is on an enum, this won't run
            }
        } catch (UnknownConnectionException | InterruptedException unknownConnectionException) {

        }
    }

    private String parseCommand(String command) {
        Scanner scanner = new Scanner(command);
        switch (scanner.next().toUpperCase()) {
            case "GO":
                switch (scanner.next().toUpperCase()) {
                    case "NORTH":
                        return moveNorth();

                    case "WEST":
                        return moveWest();

                    case "EAST":
                        return moveEast();

                    case "SOUTH":
                        return moveSouth();

                    default:
                        return "INVALID DIRECTION";
                }

            case "SAVE":
                return saveGame();

            case "LOOK":
                if (scanner.hasNext()) {
                    switch (scanner.next().toUpperCase()) {
                        case "DA":
                        case "MEAT":
                            return currentGame.meat.getItemDescription();

                        case "COUPON":
                            return currentGame.coupon1.getItemDescription();

                        case "SHIELD":
                        case "WOODEN":
                            return currentGame.shield.getItemDescription();

                        case "STICK":
                        case "SWORD":
                            return currentGame.sword.getItemDescription();

                        case "TASTY":
                        case "GOODIES":
                            return currentGame.goodies.getItemDescription();

                        case "MAGIC":
                            return currentGame.magicTome.getItemDescription();

                        case "SOME":
                        case "TOYS":
                            return currentGame.toys.getItemDescription();

                        default:
                            return "INVALID OBJECT, try LOOK [ITEM NAME]";
                    }
                } else {
                    return currentPlayer.getCurrentRoom().printDescription() + "\nYOU SEE: " +
                            currentPlayer.getCurrentRoom().printItems();
                }

            case "INVENTORY":
                return currentPlayer.printInventory();

            case "QUIT":
                try {
                    adventureServer.sendMessage(currentPlayer.getConnectionId(), "GOODBYE");
                    adventureServer.disconnect(currentPlayer.getConnectionId());
                    return "";
                } catch (IOException | UnknownConnectionException e1) {
                    e1.printStackTrace();
                }
                return "";

            case "RESTORE":
            case "LOAD":
                return loadGame();

            case "GET":
                switch (scanner.next().toUpperCase()) {
                    case "DA":
                    case "MEAT":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.meat)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.meat);
                            currentPlayer.addItemToInventory(currentGame.meat);
                            return "YOU PICKED UP DA MEAT";
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "COUPON":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon1)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.coupon1);
                            currentPlayer.addItemToInventory(currentGame.coupon1);
                            return "YOU PICKED UP A COUPON";
                        } else if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon2)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.coupon2);
                            currentPlayer.addItemToInventory(currentGame.coupon2);
                            return "YOU PICKED UP A COUPON";
                        } else if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon3)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.coupon3);
                            currentPlayer.addItemToInventory(currentGame.coupon3);
                            return "YOU PICKED UP A COUPON";
                        } else if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon4)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.coupon4);
                            currentPlayer.addItemToInventory(currentGame.coupon4);
                            return "YOU PICKED UP A COUPON";
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "SHIELD":
                    case "WOODEN":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.shield)) {
                            if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.goodies)) {
                                currentPlayer.getCurrentRoom().removeItem(currentGame.shield);
                                currentPlayer.addItemToInventory(currentGame.shield);
                                return "YOU PICKED UP THE SHIELD";
                            }
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "SWORD":
                    case "STICK":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.sword)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.sword);
                            currentPlayer.addItemToInventory(currentGame.sword);
                            return "YOU PICKED UP THE SWORD";
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "TASTY":
                    case "GOODIES":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.goodies)) {
                            if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon1) &&
                                    currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon2) &&
                                    currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon3) &&
                                    currentPlayer.getCurrentRoom().getItems().contains(currentGame.coupon4)) {
                                currentPlayer.getCurrentRoom().removeItem(currentGame.goodies);
                                currentPlayer.addItemToInventory(currentGame.goodies);
                                return "YOU PICKED UP THE GOODIES";
                            }
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "MAGIC":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.magicTome)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.magicTome);
                            currentPlayer.addItemToInventory(currentGame.magicTome);
                            return "YOU PICKED UP THE MAGIC TOMES";
                        }
                        return "YOU CAN'T PICK THAT UP";

                    case "SOME":
                    case "TOYS":
                        if (currentPlayer.getCurrentRoom().getItems().contains(currentGame.toys)) {
                            currentPlayer.getCurrentRoom().removeItem(currentGame.toys);
                            currentPlayer.addItemToInventory(currentGame.toys);
                            return "YOU PICKED UP SOME TOYS";
                        }
                        return "YOU CAN'T PICK THAT UP";

                    default:
                        return "INVALID OBJECT";
                }

            case "DROP":
                switch (scanner.next().toUpperCase()) {
                    case "DA":
                    case "MEAT":
                        if (currentPlayer.getInventory().contains(currentGame.meat)) {
                            currentPlayer.dropItem(currentGame.meat);
                            currentPlayer.getCurrentRoom().addItem(currentGame.meat);
                            return "YOU DROPPED DA MEAT";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "COUPON":
                        if (currentPlayer.getInventory().contains(currentGame.coupon1)) {
                            currentPlayer.dropItem(currentGame.coupon1);
                            currentPlayer.getCurrentRoom().addItem(currentGame.coupon1);
                            return "YOU DROPPED A COUPON";
                        } else if (currentPlayer.getInventory().contains(currentGame.coupon2)) {
                            currentPlayer.dropItem(currentGame.coupon2);
                            currentPlayer.getCurrentRoom().addItem(currentGame.coupon2);
                            return "YOU DROPPED A COUPON";
                        } else if (currentPlayer.getInventory().contains(currentGame.coupon3)) {
                            currentPlayer.dropItem(currentGame.coupon3);
                            currentPlayer.getCurrentRoom().addItem(currentGame.coupon3);
                            return "YOU DROPPED A COUPON";
                        } else if (currentPlayer.getInventory().contains(currentGame.coupon4)) {
                            currentPlayer.dropItem(currentGame.coupon4);
                            currentPlayer.getCurrentRoom().addItem(currentGame.coupon4);
                            return "YOU DROPPED A COUPON";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "SHIELD":
                    case "WOODEN":
                        if (currentPlayer.getInventory().contains(currentGame.shield)) {
                            currentPlayer.dropItem(currentGame.shield);
                            currentPlayer.getCurrentRoom().addItem(currentGame.shield);
                            return "YOU DROPPED THE WOODEN SHIELD";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "SWORD":
                    case "STICK":
                        if (currentPlayer.getInventory().contains(currentGame.sword)) {
                            currentPlayer.dropItem(currentGame.sword);
                            currentPlayer.getCurrentRoom().addItem(currentGame.sword);
                            return "YOU DROPPED THE SWORD";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "TASTY":
                    case "GOODIES":
                        if (currentPlayer.getInventory().contains(currentGame.goodies)) {
                            currentPlayer.dropItem(currentGame.goodies);
                            currentPlayer.getCurrentRoom().addItem(currentGame.goodies);
                            return "YOU DROPPED THE TASTY GOODIES";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "MAGIC":
                        if (currentPlayer.getInventory().contains(currentGame.magicTome)) {
                            currentPlayer.dropItem(currentGame.magicTome);
                            currentPlayer.getCurrentRoom().addItem(currentGame.magicTome);
                            return "YOU DROPPED THE MAGIC TOMES";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    case "SOME":
                    case "TOYS":
                        if (currentPlayer.getInventory().contains(currentGame.toys)) {
                            currentPlayer.dropItem(currentGame.toys);
                            currentPlayer.getCurrentRoom().addItem(currentGame.toys);
                            return "YOU DROPPED SOME TOYS";
                        }
                        return "YOU CAN'T DROP WHAT YOU DON'T HAVE";

                    default:
                        return "INVALID OBJECT";
                }

            case "HELP":
            case "LS":
            case "COMMANDS":
                return helpMenu;

            case "STORY":
                return openingMessage;

            default:
                return "INVALID COMMAND, TYPE HELP FOR LIST OF COMMANDS";
        }

    }

    public String saveGame() {
        try {
            FileOutputStream out = new FileOutputStream(saveFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(currentGame);
            objectOutputStream.close();
            out.close();
            return "GAME SAVED SUCCESSFULLY";
        }catch (IOException e) {
            return "ERROR SAVING GAME";
        }
    }

    public String loadGame() {
        try (FileInputStream in = new FileInputStream(saveFile)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            currentGame = (Game) objectInputStream.readObject();
            currentPlayer = currentGame.player;
            return "LOADED SAVE DATA SUCCESSFULLY\n" + parseCommand("look");
        } catch (Exception e) {
            return "ERROR LOADING FROM SAVE FILE";
        }
    }

    public String moveWest() {
        if (currentPlayer.getCurrentRoom() == currentGame.roadToForest) {
            if (!currentPlayer.getInventory().contains(currentGame.magicTome)) {
                try {
                    adventureServer.sendMessage(currentPlayer.getConnectionId(), youLose + "\n\n\nYOU DIDN'T HAVE THE ANCIENT TEXTS TO GUIDE YOU, SO YOU GOT LOST AND STARVED IN THE FOREST");
                    adventureServer.disconnect(currentPlayer.getConnectionId());
                } catch (UnknownConnectionException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (currentPlayer.getCurrentRoom().getRoomWest() != null) {
            currentPlayer.setCurrentRoom(currentPlayer.getCurrentRoom().getRoomWest());
            return parseCommand("LOOK");
        } else {
            return "YOU CAN'T GO WEST FROM HERE";
        }
    }

    public String moveEast() {
        if (currentPlayer.getCurrentRoom() == currentGame.mainCourtyard) {
            if (!currentGame.mainCourtyard.getItems().contains(currentGame.meat)) {
                return "Friend the Dog is blocking your way, maybe some food could distract him?";
            }
        }

        if (currentPlayer.getCurrentRoom().getRoomEast() != null) {
            currentPlayer.setCurrentRoom(currentPlayer.getCurrentRoom().getRoomEast());
            return parseCommand("LOOK");
        } else {
            return "YOU CAN'T GO EAST FROM HERE";
        }
    }

    public String moveNorth() {
        if (currentPlayer.getCurrentRoom().getRoomNorth() != null) {
            currentPlayer.setCurrentRoom(currentPlayer.getCurrentRoom().getRoomNorth());
            return parseCommand("LOOK");
        } else {
            return "YOU CAN'T GO NORTH FROM HERE";
        }
    }

    public String moveSouth() {
        if (currentPlayer.getCurrentRoom().getRoomSouth() != null) {
            currentPlayer.setCurrentRoom(currentPlayer.getCurrentRoom().getRoomSouth());
            return parseCommand("LOOK");
        } else {
            return "YOU CAN'T GO SOUTH FROM HERE";
        }
    }
}
