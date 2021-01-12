package adventure;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RoomTest{

    Game testGame = new Game();
    Room testRoom1 = new Room();
    Room testRoom2 = new Room();
    Adventure testAdventure = new Adventure();
    ArrayList<Room> testRoomList = new ArrayList<>();

    @Before
    public void setValues (){
        //testRoom1
        Map<Integer, String> mapOfValues = new HashMap<>();
        mapOfValues.put(106, "up");
        mapOfValues.put(105, "down");
        testRoom1.setName("Ballroom");
        testRoom1.setEntrances(mapOfValues);
        //testRoom2
        testRoom2.setId(106);
        testRoom2.setName("Roof");
        //Add room into list
        testRoomList.add(testRoom1);
        testRoomList.add(testRoom2);
        testAdventure.setRoom(testRoomList);
        //Initializing adventure
        testGame.setAdventure(testAdventure);
    }

    /** Sending valid dir and connectedRoom name into getConnectedRoom.
     *  We should get the correct room, which we are going to test with room name that we expect
     */
    @Test
    public void validDirIntoExistingRoom () {
        String direction = "up";
        String expectedRoom = "Roof";
        assertEquals(expectedRoom , testRoom1.getConnectedRoom(direction).getName());
    }

    /** Sending valid dir but invalid connectedRoom name into getConnectedRoom.
     *  We should get the NullPointerException, because we are trying to use method getName() on not existing object.
     */
    @Test (expected = NullPointerException.class)
    public void validDirIntoNotExistingRoom () {
        String direction = "down";
        String expectedRoom = "Basement";
        assertEquals(expectedRoom , testRoom1.getConnectedRoom(direction).getName());
    }

    /** Sending invalid dir but valid connectedRoom name into getConnectedRoom.
     *  We should get "null", because we can never find any room in our direction.
     */
    @Test
    public void invalidDirIntoExistingRoom() {
        String direction = "S";
        String expectedRoom = "Roof";
        assertEquals(null , testRoom1.getConnectedRoom(direction));
    }

    /** Sending invalid dir and connectedRoom name into getConnectedRoom.
     *  We should get "null", because we can never find any room in our direction.
     *  And even if we found it, our room does not exist.
     */
    @Test
    public void invalidDirIntoNotExistingRoom() {
        String direction = "S";
        String expectedRoom = "Bathroom";
        assertEquals(null , testRoom1.getConnectedRoom(direction));
    }

    /** Sending empty dir String and valid connectedRoom name into getConnectedRoom.
     *  We should get the NullPointerException, because we are sending nothing to work with.
     */
    @Test (expected = NullPointerException.class)
    public void invalidDatatypeOfInput(){
        String direction = null;
        String expectedRoom = "Bathroom";
        assertEquals(expectedRoom , testRoom1.getConnectedRoom(direction));
    }
}