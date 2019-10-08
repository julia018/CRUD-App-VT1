package sample.beans;

import java.io.Serializable;

public class HotChocolate implements Serializable {

    private CocoaPercentage cocoaPercentage;

    public HotChocolate() {
    }

    public CocoaPercentage getCocoaPercentage() {
        return cocoaPercentage;
    }

    public void setCocoaPercentage(CocoaPercentage cocoaPercentage) {
        this.cocoaPercentage = cocoaPercentage;
    }

    enum CocoaPercentage {
        BITTER,
        MILK
    }


}
