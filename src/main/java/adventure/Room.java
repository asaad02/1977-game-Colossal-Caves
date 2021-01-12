package adventure;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Room implements Serializable {
    private static final long serialVersionUID = -3788086098781612036L;
    @SerializedName("id")
    private int id;
    @SerializedName("start")
    private String start;
    @SerializedName("name")
    private String name;
    @SerializedName("short_description")
    private String shortdescription;
    @SerializedName("long_description")
    private String longdescription;
    @SerializedName("entrance")
    private ArrayList<Entrance> entrance;
    @SerializedName("entrances")
    private Map<Integer, String> entrances = new HashMap<>();
    @SerializedName("loot")
    private ArrayList<Loot> loot;

    /**
     * A default constructor to instantiate all common fields in a Room object specified in the supplied json
     * Fields that are filled are as follows:
     * id Int; The ID of the room
     * roomName String; the name of the room
     * roomDescription String; A short description of the room
     * roomLongDescription String; A long description of the room
     * entrance Map<Integer,String>; a Map of all entrances/exits to neighbouring rooms
     * roomItems ArrayList<Integer>; an ArrayList of the IDs of all items the room contains
     */
    public Room(){
    }

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId; }

    public String isStart() {
        return start;
    }

    public void setStart(String aStart) {
        start = aStart;
    }

    public String getName(){

        return name;
    }

    public void setName(String aNewName){
        name = aNewName;
    }

    public Map<Integer, String> getEntrances() {
        return entrances;
    }

    public void setEntrances(Map<Integer, String> aEntrance) {
        entrances = aEntrance;
    }

    public ArrayList<Entrance> getEntrance() {
        return entrance;
    }

    public void setEntrance(ArrayList<Entrance> aEntrance) {
        entrance = aEntrance;
    }

    public void fillmap(ArrayList<Entrance> aEntrance) {
        Map<Integer, String> methodmap = getEntrances();
        for(Entrance e : aEntrance) {
            methodmap.put(e.getId(), e.getDir());
        }
        entrances = methodmap;
    }

    public String getLongdescription(){

        return longdescription;
    }

    public void setLongdescription(String aRoomLongDescription) {
        longdescription = aRoomLongDescription;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String aRoomDescription) {
        shortdescription = aRoomDescription;
    }

    public ArrayList<Integer> getLoot() {
        ArrayList<Integer> methodloot = new ArrayList<>();
        for(Loot l : loot){
            methodloot.add(l.getId());
        }
        return methodloot;
    }

    public void setLoot(ArrayList<Loot> aRoomItems) {
        loot = aRoomItems;
    }


    /**
     * A method to find a room next to the current room in a specific direction
     * @param direction String; A direction specified by user input
     * @return A Room object, which is a neighbouring room to the current room
     */
    public Room getConnectedRoom(String direction) {
        for(Map.Entry<Integer, String> entry : entrances.entrySet()) {
            if(direction.equals(entry.getValue())) {
                for(Room r : Game.getAdventure().listAllRooms()) {
                    if(entry.getKey()==r.getId()) {
                        return r;
                    }
                }
            }
        }
            //get keyset, loop through it, if direction == direction in the entryset = getkey(),
            // loop through AL of all rooms, if ID == key, return Room
        return null;
    }
    //TODO zkratit
    /**
     * Method to find out the items the room contains
     * @return ArrayList<Item> of Items the room contains
     */
    public ArrayList<Item> fetchRoomItems() {
        ArrayList<Item> itemlist = new ArrayList<>();
        for(Item i : Game.getAdventure().listAllItems()) {
            itemListAdd(itemlist, i);
        }
        return itemlist;
    }

    private void itemListAdd(ArrayList<Item> itemList, Item givenItem){
        if(loot !=null) {
            for (Loot l : loot) {
                if (l.getId() == givenItem.getId()) {
                    itemList.add(givenItem);
                }
            }
        }
    }

    public void addItemToLoot(Item givenItem){
        ArrayList<Loot> tempLoot = new ArrayList<>();
        Loot addedItem = new Loot();
        addedItem.setId(givenItem.getId());
        tempLoot.add(addedItem);
        setLoot(tempLoot);
    }

    /**
     * Method to remove an Item from a room, usually after taking it into personal inventory
     * @param itemid Integer; ID of the Item to remove from the room
     */
    public void removeItem(Integer itemid){
        int methodid = 0;
        ArrayList<Integer> methodLoot = methodLootSet();
        ArrayList<Item> tempRoomItems = fetchRoomItems();
        for (Item item : tempRoomItems) {
            if (item.getId() == itemid) {
                loot.remove(methodid);
                break;
            }
            methodid++;
        }
    }

    private ArrayList<Integer> methodLootSet(){
        ArrayList<Integer> methodLoot = new ArrayList<>();
        for(Loot l : loot){
            methodLoot.add(l.getId());
        }
        Collections.sort(methodLoot);
        return  methodLoot;
    }

    /**
     * Method to check whether an item is in current room or not
     * @param givenName String; a name of an item to check
     * @return boolean; whether this room contains the item
     */
    public boolean thereIsItem(String givenName){
        for(Item itemInRoom : fetchRoomItems()){
            if (itemInRoom.getName().equalsIgnoreCase(givenName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Prints out the Entrance Map in a readable form
     * @param adventure Adventure object that entails the list of all rooms
     * @return HashMap<String, String> of all rooms
     */
    public HashMap<String, String> fetchListEntrance(Adventure adventure){
        HashMap<String, String> directions = new HashMap<>();
        for(Map.Entry<Integer, String> entry : entrances.entrySet()) {
            for (Room room : adventure.listAllRooms()) {
                if(entry.getKey() == room.id){
                    directions.put(room.getName(), entry.getValue());
                }
            }
        }
        return directions;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", start=" + start + ", roomName='" + name + '\''
                + ", roomDescription='" + shortdescription + '\'' + ", roomLongDescription='" + longdescription
                + '\'' + ", entrance=" + entrances + ", roomItems=" + loot + '}';
    }

}
