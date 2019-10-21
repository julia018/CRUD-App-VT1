package sample.beans;

import java.io.Serializable;

public class Sugar implements Serializable, Composition {

    private Kind kind;
    private int spoonCount;

    public Sugar() {
    }

    public String getKind() {
        return kind.toString();
    }

    public void setKind(String kind) {
        this.kind = Kind.valueOf(kind);
    }

    public int getSpoonCount() {
        return spoonCount;
    }

    public void setSpoonCount(String spoonCount) {
        this.spoonCount = Integer.parseInt(spoonCount);
    }

    enum Kind {
        WHITE,
        REED,
        MAPLE
    }

}
