package sample.beans;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Order implements Serializable {

    private LocalDateTime date;
    private Drink drink;

    public Order() {

    }

    public Order(LocalDateTime date, Drink drink) {
        this.date = date;
        this.drink = drink;
    }

    public LocalDateTime getDate() {
        return date;
    }


    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss  dd/MM/yyyy");
        this.date = LocalDateTime.parse(date);
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

    public void deleteObject() {
        drink.nullifyInnerObjects();
        drink = null;
    }

}
