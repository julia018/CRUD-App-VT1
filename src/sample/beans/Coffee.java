package sample.beans;

import java.io.Serializable;

public class Coffee extends Drink implements Serializable {

    private Trademark trademark;
    private Milk milk;
    private Sugar sugar;

    enum Trademark {
        NESKAFE,
        JACOBS,
        LAVAZZA
    }

    public Coffee() {
    }

    public Trademark getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = Trademark.valueOf(trademark);
    }

    public Milk getMilk() {
        return milk;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    public Sugar getSugar() {
        return sugar;
    }

    public void setSugar(Sugar sugar) {
        this.sugar = sugar;
    }


}
