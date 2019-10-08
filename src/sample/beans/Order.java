package sample.beans;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private Date date;
    private Drink drink;
    private Payment payment;

    public Order() {
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    enum Payment {
        CASH,
        CARD
    }


}
