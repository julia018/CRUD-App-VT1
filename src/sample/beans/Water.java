package sample.beans;

import java.io.Serializable;

public class Water implements Serializable {

    private TradeMark tradeMark;
    private Type type;

    public Water() {
    }

    public TradeMark getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(TradeMark tradeMark) {
        this.tradeMark = tradeMark;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
