package adventure;

import java.io.Serializable;

public class Weapon extends Item implements Tossable, Serializable {
    private static final long serialVersionUID = -3788086098781612036L;

    public Weapon(Item givenItem){
        super(givenItem);
    }

    /** Method return Id of object Weapon
     *  @return id of object
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /** Method return description of object Weapon
     *  @return description of object
     */
    @Override
    public String getDesc() {
        return super.getDesc();
    }

    /** Method return name of object Weapon
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
    @Override
    public String toss(Item givenItem, Player givenPlayer) {
            givenPlayer.removeItemFromInventory(givenItem);
            givenPlayer.getCurrentRoom().addItemToLoot(givenItem);
            return "You dropped " + givenItem.getName() + " on the floor";
    }
}
