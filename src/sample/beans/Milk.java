package sample.beans;

import java.io.Serializable;

public class Milk implements Serializable {

    private Origin origin;

    public Milk() {
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    enum Origin {
        COW,
        OAT,
        SOYBEAN,
        CORN
    }


}
