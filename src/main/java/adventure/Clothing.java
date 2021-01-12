package adventure;

import java.io.Serializable;

public class Clothing extends Item implements Wearable, Serializable {
    private static final long serialVersionUID = -3788086098781612036L;

    public Clothing(){}

    public Clothing(Item givenItem) {
        super(givenItem);
    }

//    public Clothing(int id, String name, String desc) {
//    }

    /** Method return Id of object Clothing
     *  @return id of object
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /** Method return description of object Clothing
     *  @return description of object
     */
    @Override
    public String getDesc() {
        return super.getDesc();
    }

    /** Method return name of object Clothing
     *  @return name of object
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /** Method set worn item for player
     * @param givenItem Item, that player want to wear.
     * @param givenPlayer Player, who is to wear the Item.
     *  @return String, that givenPlayer worn givenItem.
     */
    @Override
    public String wear(Item givenItem, Player givenPlayer) {
            givenPlayer.setIdOfWornItem(givenItem.getId());
            return "You put " + givenItem.getName() + " on yourself";
    }
}


