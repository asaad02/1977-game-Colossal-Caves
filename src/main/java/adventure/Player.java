package adventure;

import java.io.Serializable;
import java.util.ArrayList;

public final class Player implements Serializable {
    private static final long serialVersionUID = -3788086098781612036L;
    private String name;
    private ArrayList<Item> inventory;
    private Room currentRoom;
    private String saveGameName;
    private int idOfWornItem;

    public Player(){
        inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public String readInventory() {
       String tempString = "You have:";
        if (inventory.isEmpty()){
            return "You do not have any items";
        }
        return stringInventory(tempString);
    }

    private String stringInventory(String tempString){
        for(Item inventoryItem : inventory){
            if (inventoryItem.getId() == idOfWornItem){
                tempString = tempString + " [ " + inventoryItem.getName() + "-(worn) ]";
            } else {
                tempString = tempString + " [ " + inventoryItem.getName() + " ]";
            }
        }
        return tempString;
    }

    public void setInventory(ArrayList<Item> aInventory) {
        inventory = aInventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room aCurrentRoom) {
        currentRoom = aCurrentRoom; }

    public String getSaveGameName() {
        return saveGameName;
    }

    public boolean hasPlayerItem(String givenItem) {
        for (Item item: inventory){
            if (givenItem.equals(item.getName())){
                return true;
            }
        }
        return false;
    }

    public Item getItemFromInventory(String givenName){
        for (Item item : inventory){
            if (givenName.equals(item.getName())){
                return item;
            }
        }
        return null;
    }

    public void removeItemFromInventory(Item givenItem){
        int index = 0;
        ArrayList<Item> tempInventory = getInventory();
        for (Item item : tempInventory) {
            if (item == givenItem) {
                tempInventory.remove(index);
                break;
            }
            index++;
        }
        setInventory(tempInventory);
    }

    public int getIdOfWornItem() {
        return idOfWornItem;
    }

    public void setIdOfWornItem(int aIdOfWornItem) {
        this.idOfWornItem = aIdOfWornItem;
    }

    public void setSaveGameName(String aSaveGameName) {
        saveGameName = aSaveGameName;
    }
    /**
     * Method to change the current room of the player
     * @param direction String; is the direction to which the room is supposed to be moved
     */
    public void changeRoom(String direction) {
        currentRoom = currentRoom.getConnectedRoom(direction);
    }
    @Override
    public String toString() {
        return "Player{" + "name='" + name + '\'' + ", inventory=" + inventory + ", currentRoom=" + currentRoom
                + ", saveGameName='" + saveGameName + '\'' + '}';
    }
}
