package sample.beans;

import java.io.Serializable;

public class Sugar implements Serializable {

    private Kind kind;
    private int spoonCount;

    public Sugar() {
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public int getSpoonCount() {
        return spoonCount;
    }

    public void setSpoonCount(int spoonCount) {
        this.spoonCount = spoonCount;
    }

    enum Kind {
        WHITE,
        REED,
        MAPLE
    }



}
