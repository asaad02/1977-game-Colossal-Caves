package adventure;

import java.io.Serializable;

public final class Loot implements Serializable {
    private static final long serialVersionUID = -3788086098781612036L;
    private int id;

    public Loot(){
    }

    public Loot(int aId) {
        id = aId;
    }

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }
}
