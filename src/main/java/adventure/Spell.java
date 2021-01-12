package adventure;

import java.io.Serializable;

public class Spell extends Item implements Readable, Serializable {
    private static final long serialVersionUID = -3788086098781612036L;

    public Spell(Item givenItem){
        super(givenItem.getId(), givenItem.getName(), givenItem.getDesc());
    }

    /** Method return Id of object Spell
     *  @return id of object
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /** Method return description of object Spell
     *  @return description of object
     */
    @Override
    public String getDesc() {
        return super.getDesc();
    }

    /** Method return name of object Spell
     *  @return name of object
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /** Method read given Item
     *
     * @param givenItem Item, that player want to read
     * @return String that player read was written on givenItem
     */
    @Override
    public String read(Item givenItem) {
        return "You read what was written on " + givenItem.getName();
    }
}
