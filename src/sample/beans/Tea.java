package sample.beans;

import java.io.Serializable;

public class Tea extends Drink implements Serializable {

    private ColorKind colorKind;
    private LeafKind leafKind;
    private Sugar sugar;
    private TradeMark tradeMark;

    public Tea() {
    }

    public String getColorKind() {
        return colorKind.toString();
    }

    public void setColorKind(String colorKind) {
        this.colorKind = ColorKind.valueOf(colorKind);
    }

    public String getLeafKind() {
        return leafKind.toString();
    }

    public void setLeafKind(String leafKind) {
        this.leafKind = LeafKind.valueOf(leafKind);
    }

    public Sugar getSugar() {
        return sugar;
    }

    public void setSugar(Sugar sugar) {
        this.sugar = sugar;
    }

    public String getTradeMark() {
        return tradeMark.toString();
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = TradeMark.valueOf(tradeMark);
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

    @Override
    public void nullifyInnerObjects() {
        sugar = null;
    }

}
