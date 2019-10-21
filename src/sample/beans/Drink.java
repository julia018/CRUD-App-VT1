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

    public String getVolume() {
        return volume.toString();
    }

    public void setVolume(String volume) {
        this.volume = Volume.valueOf(volume);
    }

    public String getContainer() {
        return container.toString();
    }

    public void setContainer(String container) {
        this.container = Container.valueOf(container);
    }

    //method for overriding
    public void nullifyInnerObjects() {
        return;
    }

}
