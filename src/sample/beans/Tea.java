package sample.beans;

import java.io.Serializable;

public class Tea implements Serializable {

    private ColorKind colorKind;
    private LeafKind leafKind;
    private Sugar sugar;
    private TradeMark tradeMark;

    public Tea() {
    }

    public ColorKind getColorKind() {
        return colorKind;
    }

    public void setColorKind(ColorKind colorKind) {
        this.colorKind = colorKind;
    }

    public LeafKind getLeafKind() {
        return leafKind;
    }

    public void setLeafKind(LeafKind leafKind) {
        this.leafKind = leafKind;
    }

    public Sugar getSugar() {
        return sugar;
    }

    public void setSugar(Sugar sugar) {
        this.sugar = sugar;
    }

    public TradeMark getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(TradeMark tradeMark) {
        this.tradeMark = tradeMark;
    }

    enum TradeMark {
        GREENFIELD,
        TESS,
        LIPTON
    }
    enum ColorKind {
        BLACK,
        GREEN,
        RED
    }

    enum LeafKind {
        LARGE,
        SMALL
    }



}
