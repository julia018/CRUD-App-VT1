package sample.beans;

import java.io.Serializable;

public class Milk implements Serializable, Composition {

    private Origin origin;

    public Milk() {
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = Origin.valueOf(origin);
    }

    enum Origin {
        COW,
        OAT,
        SOYBEAN,
        CORN
    }


}
