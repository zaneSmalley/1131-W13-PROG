import java.io.*;

/**
 * Last modified on 12/14/2019
 *
 * @author Zane Smalley, Cooper Tyson, Jack Grantham, Tom Clark
 * <p>
 * CS1131 Fall 2019
 * Lab Section 2
 */

// Keep track of the game state and set up relationship with rooms and items and such

public class Game implements Serializable {

    Player player;
    Room mainCourtyard;
    Room roadToForest;
    Room candyShop;
    Room forest;
    Room grannysHouse;
    Room playground;
    Room yourHouse;
    Room yourRoom;
    Room attic;

    Item goodies;
    Item coupon1;
    Item coupon2;
    Item coupon3;
    Item coupon4;
    Item meat;
    Item sword;
    Item shield;
    Item magicTome;
    Item toys;


    public Game(Player player) {

        this.player = player;
        mainCourtyard = new Room("Main Courtyard");
        roadToForest = new Room("Road to Forest");
        candyShop = new Room("Candy Shop");
        forest = new Room("Mystery Forest");
        grannysHouse = new Room("Granny's House");
        playground = new Room("Playground");
        yourHouse = new Room("Your House");
        yourRoom = new Room("Your Room");
        attic = new Room("The Attic");

        goodies = new Item("Tasty Goodies");
        coupon1 = new Item("Coupon");
        coupon2 = new Item("Coupon");
        coupon3 = new Item("Coupon");
        coupon4 = new Item("Coupon");
        meat = new Item("Da Meat");
        sword = new Item("A Mighty Sword (stick)");
        shield = new Item("Wooden Shield");
        magicTome = new Item("Magic Tomes");
        toys = new Item("Some Toys");

        player.setCurrentRoom(mainCourtyard);
        player.addItemToInventory(coupon4);


        mainCourtyard.setRoomNorth(grannysHouse);
        mainCourtyard.setRoomSouth(playground);
        mainCourtyard.setRoomEast(yourHouse);
        mainCourtyard.setRoomWest(roadToForest);
        mainCourtyard.setRoomDescription("The courtyard that the rest of the village is built around. To the east is" +
                " your very own home, to the west is the road that leads to the Mystery Forest, to the south is the" +
                " playground, where kids spend their time playing tag or quizzing each other about legends of old," +
                " and to the north is old Granny’s house.");

        grannysHouse.setRoomSouth(mainCourtyard);
        grannysHouse.addItem(coupon1);
        coupon1.setItemDescription("A 25% off coupon, maybe 4 of them will equal 100% off?");
        grannysHouse.setRoomDescription("Walking north from the main courtyard brings you up to a small collection of" +
                " houses, particularly Granny’s house, with Granny sitting on the porch swing just outside her abode." +
                " Her real name is Johanna Nerversmile, but she yells at anyone who calls her anything but Granny." +
                " Regardless, you’ve heard from around the playground that she has a coupon for the local candy shop," +
                " but being the hoarder that she is, you’ll have to think of a sneaky way to get it from her." +
                " Otherwise, walking south again will bring you back to the main courtyard.");

        playground.setRoomNorth(mainCourtyard);
        playground.addItem(shield);
        shield.setItemDescription("A piece of wood shaped like a great shield");
        playground.addItem(coupon2);
        playground.setRoomDescription("Down the path below the main courtyard, a couple groups of kids have gathered " +
                "around a small collection of slides, monkey bars and sand pits that makes up the local playground. " +
                "To your left, a group of kids giggle to themselves as they test their knowledge of the local legends " +
                "against one another. On the other side of the playground a game of tag is beginning to form, though" +
                " no one has volunteered to take the position of “it” quite yet. However, your eyes wander towards " +
                "one particularly interesting item the kids to the left are all gathered around. A small wooden " +
                "shield that would be perfect for your quest. They likely won’t give it up freely however, and " +
                "will want to trade for another item, most likely some GOODIES");

        yourHouse.setRoomWest(mainCourtyard);
        yourHouse.setRoomNorth(yourRoom);
        yourHouse.setRoomDescription("After moving Friend out of the way, you finally manage to get back into your own" +
                " home. To your left is a kitchen area where meals are prepared and eaten, with an island in the center" +
                " that has a 25% off coupon on top. To the right there is an open area for people to just kinda stand" +
                " around and hang out, ya know? Farther north in the house is your own room, which is filled with" +
                " possible goodies that could help you on your current quest.");

        yourRoom.setRoomNorth(attic);
        yourRoom.setRoomSouth(yourHouse);
        yourRoom.addItem(toys);
        toys.setItemDescription("A few toys that you enjoy playing with, they probably wont help in a fight though.");
        yourRoom.setRoomDescription("It’s your room! It’s hasn’t changed much over the last several years, outside of" +
                " the bed slowly being replaced every few years to keep up with your own growth. To the left, there is" +
                " a drawer with your small handful of clothes, on top of which are some homemade toys that represent" +
                " heroes and villains from classic stories and legends. To your right is your bed, with only slightly" +
                " less bed bugs than there is straw to fill it up. Above you is a door to the attic, though you’re" +
                " still too young to reach its string without help from someone else. Out through the window in front" +
                " of you, you can see your friend waiting patiently for you to finish preparations for your final duel," +
                " playing a simple game with some of the pebbles in the courtyard. Back to the south is the room’s" +
                " exit and the stairs leading back to the main floor of the house.");

        attic.setRoomSouth(yourRoom);
        attic.addItem(coupon3);
        attic.addItem(magicTome);
        magicTome.setItemDescription("Ancient texts said to contain knowledge of the mystical forest");
        attic.setRoomDescription("As you climb up into the attic of your home, a wave of musty air hits your nose, " +
                "making you flinch as you finish climbing up the last few steps. There’s a window on the far wall of" +
                " the attic, illuminating a small handful of crates to your right, one of which has a 25% off coupon" +
                " inside, and a bookshelf practically overflowing with old books and MAGIC TOMES. However, outside of" +
                " these two things and the intricate cobwebs plastered across the room’s corners, there isn’t much of" +
                " interest to look at. Going south ( or back down the stairs as normal people would call it)" +
                " will bring you back to your room.");

        roadToForest.setRoomNorth(candyShop);
        roadToForest.setRoomWest(forest);
        roadToForest.setRoomEast(mainCourtyard);
        roadToForest.addItem(meat);
        meat.setItemDescription("Its meat.");
        roadToForest.setRoomDescription("Heading west of the courtyard and narrowly avoiding stepping on a strange piece of " +
                "meat laying slightly off to the left of the road, you begin to follow a narrow dirt path that leads " +
                "into the Mystery Forest; a strange and fantastical part of the country that the elders of your village" +
                " absolutely FORBID anyone from entering. But you’re a little kid, so the rules are more of a passing" +
                " suggestion than an actual hard set thing to follow. Walking down this path you see it also forks" +
                " slightly to the north, where a small candy shop was recently opened for business. You heard that" +
                " the store distributed a small amount of discount coupons as marketing for its grand opening, but" +
                " it seems pretty empty from your position.");

        candyShop.setRoomSouth(roadToForest);
        candyShop.addItem(goodies);
        goodies.setItemDescription("A bag of tasty goodies");
        candyShop.setRoomDescription("The candy shop, aptly named “Local Candy Shop”, is a quaint little building just" +
                " off the road to Mystery Forest. It’s shelves are lined with candies from all across the land, each" +
                " with their own unique and enticing smells and flavors. Outside of the solemn looking cashier at the" +
                " far end of the store, it’s a pretty happy-go lucky place. You can give the cashier any coupons you’ve" +
                " received on your adventure to get a discount on any candy in the store, and exiting back through the" +
                " store’s entrance will lead you south back towards the main road towards the Mystery Forest.");

        forest.setRoomEast(roadToForest);
        forest.addItem(sword);
        sword.setItemDescription("Yeah its just a stick, but with the power of imagination it is the most powerful greatsword ever forged!");
        forest.setRoomDescription("Finally, you arrive at the Mystery Forest, a terrifying part of the village that" +
                " few dare to enter. However, it’s also the perfect place to find “swords” that you could use on" +
                " your adventure, so you’ll have to face your fears and enter regardless of your apprehensions.");
    }


}
