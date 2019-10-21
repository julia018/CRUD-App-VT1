package sample.beans;

import java.io.Serializable;

public class Water extends Drink implements Serializable {

    private TradeMark tradeMark;
    private Type type;

    public Water() {
    }

    public String getTradeMark() {
        return tradeMark.toString();
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = TradeMark.valueOf(tradeMark);
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    enum TradeMark {
        EVIAN,
        DARIDA,
        FROST,
        BORJOMI
    }

    enum Type {
        SPARKLING,
        STILL
    }


}
