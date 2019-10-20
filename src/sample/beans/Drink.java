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

    public void setVolume(String volume) {
        this.volume = Volume.valueOf(volume);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = Container.valueOf(container);
    }

}
