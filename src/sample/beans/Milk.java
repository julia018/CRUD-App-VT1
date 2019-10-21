package sample.beans;

import java.io.Serializable;

public class Milk implements Serializable, Composition {

    private Origin origin;

    public Milk() {
    }

    public String getOrigin() {
        return origin.toString();
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
