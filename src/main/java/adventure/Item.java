package adventure;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = -3788086098781612036L;
    private int id;
    private String name;
    private String desc;
    private boolean edible;
    private boolean throwable;
    private boolean readable;
    private boolean wearable;


    public Item(){}

    public Item(Item givenItem){
        id = givenItem.getId();
        name = givenItem.getName();
        desc = givenItem.getDesc();
    }

    public Item(int aId, String aName, String aItemLongDescription) {
        id = aId;
        name = aName;
        desc = aItemLongDescription;
    }

    /** Method return name of Item
     * @return name of object
     */
    public String getName(){
        return name;
    }

    /** Method set name of object Item
     *  @param aName String of iame that we want to set to the Item
     */
    public void setName(String aName) {
        name = aName;
    }

    /** Method return id of Item
     * @return id of object
     */
    public int getId() {
        return id;
    }

    /** Method set id of object Item
     *  @param aId String of id that we want to set to the Item
     */
    public void setId(int aId) {
        id = aId;
    }

    /** Method return description of Item
     * @return description of object
     */
    public String getDesc(){
        return desc;
    }

    /** Method set description of object Item
     *  @param aItemLongDescription String of description that we want to set to the Item
     */
    public void setDesc(String aItemLongDescription) {
        desc = aItemLongDescription;
    }

    /** Method return boolean if is it Edible or not
     * @return boolean from object
     */
    public boolean isEdible() {
        return edible;
    }

    /** Method return boolean if is it Throwable or not
     * @return boolean from object
     */
    public boolean isThrowable() {
        return throwable;
    }

    /** Method return boolean if is it Readable or not
     * @return boolean from object
     */
    public boolean isReadable() {
        return readable;
    }

    /** Method return boolean if is it Wearable or not
     * @return boolean from object
     */
    public boolean isWearable() {
        return wearable;
    }

    /** Method set boolean if is it Edible or not
     * @param aEdible sets if it is edible or not
     */
    public void setEdible(boolean aEdible) {
        edible = aEdible;
    }

    /** Method set boolean if is it Throwable or not
     * @param aThrowable sets if it is throwable or not
     */
    public void setThrowable(boolean aThrowable) {
        throwable = aThrowable;
    }

    /** Method set boolean if is it Readable or not
     * @param aReadable sets if it is readable or not
     */
    public void setReadable(boolean aReadable) {
        readable = aReadable;
    }

    /** Method set boolean if is it Wearable or not
     * @param aWearable sets if it is wearable or not
     */
    public void setWearable(boolean aWearable) {
        wearable = aWearable;
    }

    /** Method return String with name of Item
     * @return Name in String;
     */
    @Override
    public String toString() {
        return " " + name + " ";
    }
}
