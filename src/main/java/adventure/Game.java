package adventure;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Game extends JFrame implements Serializable {

    private static Adventure adventure;
    private static GUIImplementation gui;
    private static final long serialVersionUID = -3788086098781612036L;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InvalidCommandException {
        Game theGame = new Game();
        Player player = new Player();
        gui = new GUIImplementation(theGame);
        gui.setVisible(true);
        System.out.println("Welcome Player! The game awaits you!");
        theGame.setAdventure(gui.getGame(), "//src//resources//defaultadventure.json");
        theGame.gameStarter(player, theGame);
        gui.setPlayer(player);
        theGame.printmenu();
        theGame.roomInfo(player.getCurrentRoom());
    }

    /**
     * The central method to handle the user actions
     *
     * @param theGame Game object of the current game to use the non-static methods
     * @param player  Player object to use the non-static methods and find out the
     *                player's current location
     * @param userinput String that player wrote in command line
     * @throws InvalidCommandException Throws a custom-made exception when the
     *                                 provided command is not included in the
     *                                 static array of viable commands
     */
    public void doInstruction(Game theGame, Player player, String userinput) throws InvalidCommandException {
        Parser parser = new Parser();
        Command command = parser.parseUserCommand(userinput);
        System.out.println("");
        switch (command.getActionWord()) {
            case "inventory":
                System.out.println("The items: " + player.getInventory().toString() + " are in your inventory");
                break;
            case "look":
                theGame.lookInstruction(command, player);
                break;
            case "take":
                theGame.takeInstruction(command, player);
                break;
            case "go":
                theGame.goInstruction(command, player);
                break;
            case "eat":
                theGame.eatInstruction(command, player);
                break;
            case "wear":
                theGame.wearInstruction(command, player);
                break;
            case "toss":
                theGame.tossInstruction(command, player);
                break;
            case "read":
                theGame.readInstruction(command, player);
                break;
            default:
                throw new InvalidCommandException();
        }
        theGame.roomInfo(player.getCurrentRoom());
    }

    private void readInstruction(Command givenCommand, Player givenPlayer) throws InvalidCommandException {
        Item tempItem = givenPlayer.getItemFromInventory(givenCommand.getNoun());
        if (hasPlayerItem(givenCommand, givenPlayer)) {
            if (tempItem instanceof Readable) {
                System.out.println(((Readable) tempItem).read(tempItem));
            } else {
                System.out.println("Nothing to read...");
            }
        } else {
            throw new InvalidCommandException();
        }
    }

    private void tossInstruction(Command givenCommand, Player givenPlayer) throws InvalidCommandException {
        Item tempItem = givenPlayer.getItemFromInventory(givenCommand.getNoun());
        if (hasPlayerItem(givenCommand, givenPlayer)) {
            if (tempItem instanceof Tossable) {
                System.out.println(((Tossable) tempItem).toss(tempItem, givenPlayer));
            } else {
                System.out.println("You can not drop this!");
            }
        } else {
            throw new InvalidCommandException();
        }
    }

    private void wearInstruction(Command givenCommand, Player givenPlayer) throws InvalidCommandException {
        Item tempItem = givenPlayer.getItemFromInventory(givenCommand.getNoun());
        if (hasPlayerItem(givenCommand, givenPlayer)) {
            if (tempItem instanceof Wearable) {
                System.out.println(((Wearable) tempItem).wear(tempItem, givenPlayer));
            } else {
                System.out.println("You can not just wear this!");
            }
        } else {
            throw new InvalidCommandException();
        }
    }

    private void eatInstruction(Command givenCommand, Player givenPlayer) throws InvalidCommandException {
        Item tempItem = givenPlayer.getItemFromInventory(givenCommand.getNoun());
        if (hasPlayerItem(givenCommand, givenPlayer)) {
            if (tempItem instanceof Edible) {
                System.out.println(((Edible) tempItem).eat(tempItem, givenPlayer));
            } else {
                System.out.println("You should not eat this item for sure!");
            }
        } else {
            throw new InvalidCommandException();
        }
    }



    /**
     * Method to handle the "go" instructions to change rooms
     *
     * @param command the command received from the parser in the readInstruction
     *                method
     * @param player  Player object to use the non-static methods and find out the
     *                player's current location
     */
    private void goInstruction(Command command, Player player) throws InvalidCommandException {
        if(player.getCurrentRoom().getConnectedRoom(command.getNoun())!=null) {
            player.changeRoom(command.getNoun());
        } else {
            throw new InvalidCommandException();
        }
    }

    /**
     * Method to handle the "take" instruction and obtain items from rooms
     *
     * @param command the command received from the parser in the readInstruction
     *                method
     * @param player  Player object to use the non-static methods and find out the
     *                player's current location
     */
    private void takeInstruction(Command command, Player player) throws InvalidCommandException {
        if(player.getCurrentRoom().thereIsItem(command.getNoun())) {
            for (Item i : player.getCurrentRoom().fetchRoomItems()) {
                if (command.getNoun().equalsIgnoreCase(i.getName())) {
                    takeToInventory(player, i);
                    break;
                }
            }
        } else {
            throw new InvalidCommandException();
        }
    }

    private void takeToInventory(Player givenPlayer, Item givenItem){
        givenPlayer.getInventory().add(givenItem);
        System.out.println(givenItem.getName() + " has been added to the inventory.");
        givenPlayer.getCurrentRoom().removeItem(givenItem.getId());
    }

    /**
     * Method to handle the look instructions to see descriptions of rooms or of
     * items
     *
     * @param command the command received from the parser in the readInstruction
     *                method
     * @param player  Player object to use the non-static methods and find out the
     *                player's current location
     */
    private void lookInstruction(Command command, Player player) {
        if (!command.hasSecondWord()) {
            System.out.println(player.getCurrentRoom().getLongdescription());
        } else {
            for (Item i : player.getCurrentRoom().fetchRoomItems()) {
                if (i.getName().equalsIgnoreCase(command.getNoun())) {
                    System.out.println(i.getDesc());
                }
            }
        }
    }

    /**
     * A method to set up the adventure, either new or load it from a JSON File
     *
     * @param theGame Game; The game object, upon which methods are called
     * @param path    String with path to your Adventure
     */
    public void setAdventure(Game theGame, String path) throws InvalidCommandException {
        try {
            setAdventureObject(theGame,path);
        }catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    private void setAdventureObject(Game theGame, String path) throws IOException, ParseException {
        JSONObject jo;
        String filepath = new File("").getAbsolutePath();
        if (path.equals("//src//resources//defaultadventure.json")) {
            jo = theGame.loadAdventureJson(filepath + path);
            adventure = generateAdventure(jo);
        } else {
            jo = loadAdventureJson(filepath + "//src//resources//" + path);
            adventure = generateAdventure(jo);
            gui = new GUIImplementation(theGame);
        }
    }

    /**
     * A method to generate a JSON Object from a JSON File
     *
     * @param filename String; name of the file to read from
     * @return JSONObject to be used to generate an Adventure Object
     * @throws IOException    throws IOEx due to working with files
     * @throws ParseException throws a PE due to the usage of the JSONParser
     */
    public JSONObject loadAdventureJson(String filename) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;

        try (Reader reader = new FileReader(filename)) {
            jsonObject = (JSONObject) parser.parse(reader);
        }
        jsonObject = (JSONObject) jsonObject.get("adventure");
        return jsonObject;
    }
    /**
     * A method to generate an Adventure from a JSON Object
     * @param obj JSONObject created from the loadAdventureJson method
     * @return An Adventure Object with rooms and items
     */
    public Adventure generateAdventure(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), Adventure.class);
    }

    /**
     * Save the gave to a .ser object using Serialization
     *
     * @param aAdventure Adventure, the Adventure file that is to be saved
     * @param filename   String, the name of the file to write into
     */
    public void saveGame(Adventure aAdventure, String filename) throws InvalidCommandException {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);

            objOutputStream.writeObject(aAdventure);

            objOutputStream.close();
            outputStream.close();
        }catch(IOException e){
            throw new InvalidCommandException();
        }
    }

    /**
     * Loads the .ser file to load the saved adventure
     * @param filename String; gets the name of the file to load
     * @return Adventure; returns a new Adventure object
     */
    public Adventure loadGame(String filename) throws InvalidCommandException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Adventure) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new InvalidCommandException();
        }
    }
    /**
     * A method to initialize the game and set the name, set of items and starting room of the
     * player
     * @param givenGame A Game object on which all game-based methods are called
     * @param givenPlayer A Player object on which all player-based methods are called
     */
    public void gameStarter(Player givenPlayer, Game givenGame) {
        givenGame.setItems(adventure.listAllItems(), adventure);
        Room startRoom = startRoom();
        givenPlayer.setCurrentRoom(startRoom);
    }

    /**
     * A method that loops through all rooms and looks for the starting room
     *
     * @return Room object, being the starting room for the adventure
     */
    private Room startRoom() {
        ArrayList<Room> startRoom = adventure.listAllRooms();
        for (Room gameRoomStarter : startRoom) {
            if (gameRoomStarter.isStart().equals("true")) {
                return gameRoomStarter;
            }
        }
        return null;
    }

    private void setItems(ArrayList<Item> listAllItems, Adventure givenAdventure) {
        ArrayList<Item> tempItemList = new ArrayList<>();
        for (Item item : listAllItems) {
            tempItemList.add(recognizeObject(item));
        }
        givenAdventure.setItem(tempItemList);
    }

    public Item recognizeObject(Item givenItem) {
        if (givenItem.isReadable() || givenItem.isThrowable()) {
            return readAndToss(givenItem);
        }

        if (givenItem.isEdible() || givenItem.isWearable()) {
         return  eatAndWear(givenItem);
        }
        return null;
    }

    private Item readAndToss(Item givenItem){
        if (givenItem.isReadable()) {
            return readable(givenItem);
        }
        if (givenItem.isThrowable()) {
            return throwable(givenItem);
        }
        return null;
    }
    private Item eatAndWear(Item givenItem){
        if (givenItem.isEdible()) {
            return  edible(givenItem);
        }
        if (givenItem.isWearable()) {
            return wearable(givenItem);
        }
        return null;
    }


    private Item readable(Item givenItem){
        if (givenItem.isWearable()) {
            return new BrandedClothing(givenItem);
        } else {
            return new Spell(givenItem);
        }
    }

    private Item throwable(Item givenItem){
        if (givenItem.isEdible()) {
            return new SmallFood(givenItem);
        } else {
            return new Weapon(givenItem);
        }
    }

    private Item edible(Item givenItem){
        return new Food(givenItem);
    }

    private Item wearable(Item givenItem){
        return new Clothing(givenItem);
    }

    /**
     * Method to grant information about a room
     *
     * @param room A room object, (where mostly the current room of the person is
     *             used) to access information about that room
     */
    private void roomInfo(Room room) {
        System.out.printf("%nYou are in the room : [%s]%n", room.getName());
        System.out.println(printRoomItems(room));
        room.fillmap(room.getEntrance());
        System.out.println("Entrances are: ");
        HashMap<String, String> dirs = room.fetchListEntrance(adventure);
        for (Map.Entry<String, String> entry : dirs.entrySet()) {
            System.out.println("    " + entry.getKey() + " in direction: " + entry.getValue());
        }
    }

    private String printRoomItems(Room givenRoom){
        if (givenRoom.fetchRoomItems().isEmpty()) {
            return "There are no items";
        } else {
            return "There are the following items: " + givenRoom.fetchRoomItems();
        }
    }

    /**
     * Method to print a menu of possible moves the player can make
     */
    private void printmenu() {
        System.out.println("What would you like to do?");
        System.out.print("\t Move to a different room (\"go N/E/S/W/up/down\")\n");
        System.out.print("\t See a longer description of the room (\"look\")\n");
        System.out.print("\t See a longer description of an item in the room (\"look <itemname>\")\n");
        System.out.print("\t Pick up an item (\"take <itemname>\")\n");
        System.out.print("\t View your inventory (\"inventory\")\n");
        System.out.print("\t Eat/Read/Wear/Toss item from your inventory (\"eat/read/wear/toss <itemname>\")\n");
        System.out.print("\t Quit the game (\"quit\")\n");
    }

    private boolean hasPlayerItem(Command givenCommand, Player givenPlayer) {
        return givenPlayer.hasPlayerItem(givenCommand.getNoun());
    }

    public static Scanner getSc() {
        return sc;
    }

    public static void setSc(Scanner aSc) {
        sc = aSc;
    }

    public static Adventure getAdventure() {
        return adventure;
    }

    public void setAdventure(Adventure aAdventure) {
        adventure = aAdventure;
    }
}
