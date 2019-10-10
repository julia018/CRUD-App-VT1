package sample.beans;

import java.io.Serializable;

public abstract class Drink implements Serializable {

    private Volume volume;
    private Container container;

    enum Volume {
        SMALL,
        MIDDLE,
        BIG
    }

    enum Container {
        STATIOARY,
        TAKEAWAY
    }

    public Drink() {
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }


}
