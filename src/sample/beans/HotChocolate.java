package sample.beans;

import java.io.Serializable;

public class HotChocolate extends Drink implements Serializable {

    private CocoaPercentage cocoaPercentage;

    public HotChocolate() {
    }

    public String getCocoaPercentage() {
        return cocoaPercentage.toString();
    }

    public void setCocoaPercentage(String cocoaPercentage) {
        this.cocoaPercentage = CocoaPercentage.valueOf(cocoaPercentage);
    }

    enum CocoaPercentage {
        BITTER,
        MILK
    }


}
