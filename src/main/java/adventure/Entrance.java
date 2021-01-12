package adventure;

import java.io.Serializable;

public final class Entrance implements Serializable {
    private int id;
    private String dir;

    public Entrance() {
    }

    public Entrance(int aId, String aDir) {
        id = aId;
        dir = aDir;
    }

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String aDir) {
        dir = aDir;
    }

    @Override
    public String toString() {
        return "Entrance{"
                + "id=" + id
                + ", dir='" + dir + '\'' + '}';
    }
}
