package adventure;

import java.io.Serializable;

public class SmallFood extends Food implements Tossable, Serializable {
    private static final long serialVersionUID = -3788086098781612036L;

    public SmallFood(Item givenItem) {
        super(givenItem);
    }

    /** Method return Id of object SmallFood
     *  @return id of object
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /** Method return description of object SmallFood
     *  @return description of object
     */
    @Override
    public String getDesc() {
        return super.getDesc();
    }

    /** Method return name of object SmallFood
     *  @return name of object
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /** Method drop givenItem from givenPlayer and add it do current room loot
     * @param givenItem Item to toss
     * @param givenPlayer Player which toss the givenItem
     * @return String, that givenPlayer dropped givenItem
     */
    public String toss(Item givenItem, Player givenPlayer) {
        givenPlayer.removeItemFromInventory(givenItem);
        givenPlayer.getCurrentRoom().addItemToLoot(givenItem);
        return "You dropped " + givenItem.getName() + " on the floor";
    }
}
