package adventure;

import java.io.Serializable;

public class Food extends Item implements Edible, Serializable {
      private static final long serialVersionUID = -3788086098781612036L;

    public Food(Item givenItem) {
        super(givenItem);
    }

    /** Method return Id of object Food
     *  @return id of object
     */
    @Override
    public int getId() {
        return super.getId(); }

    /** Method return description of object Food
     *  @return description of object
     */
    @Override
    public String getDesc() {
        return super.getDesc();
    }

    /** Method return name of object Food
     *  @return name of object
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /** Method eat givenItem item and deletes it from game
     * @param givenItem Item to eat.
     * @param givenPlayer Player, which eat given Item.
     * @return String, that givenPlayer ate givenItem.
     */
    @Override
    public String eat(Item givenItem, Player givenPlayer) {
            givenPlayer.removeItemFromInventory(givenItem);
            return "You ate " + givenItem.getName();
        }
    }

