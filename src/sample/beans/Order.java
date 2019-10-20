package sample.beans;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private Date date;
    private Drink drink;

    public Order() {

    }

    public Order(Date date, Drink drink) {
        this.date = date;
        this.drink = drink;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    enum Payment {
        CASH,
        CARD
    }


}
