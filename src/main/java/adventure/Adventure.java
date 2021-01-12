package adventure;

import java.io.Serializable;
import java.util.ArrayList;

public final class Adventure implements Serializable {
    private static final long serialVersionUID = -3788086098781612036L;
    private ArrayList<Room> room;
    private ArrayList<Item> item;

    public Adventure() {
    }

    public ArrayList<Room> getRoom() {
        return room;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setRoom(ArrayList<Room> aRooms) {
        room = aRooms;
    }

    public void setItem(ArrayList<Item> aItems) {
        item = aItems;
    }

    public ArrayList<Room> listAllRooms(){
        return room;
    }

    public ArrayList<Item> listAllItems(){
        return item;
    }
}
