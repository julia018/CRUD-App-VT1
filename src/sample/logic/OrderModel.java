package sample.logic;

import sample.beans.Drink;
import sample.beans.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for order's representation in tableview
 */

public class OrderModel {

    /**
     * The order represented in tableview row.
     */
    private Order order;

    /**
     * The 1st column of tableview: order's date.
     */
    private LocalDateTime orderDate;

    /**
     * The 2nd column of tableview: order's drink.
     */
    private Drink orderDrink;


    OrderModel(Order order) {
        this.order = order;
        this.orderDate = order.getDate();
        this.orderDrink = order.getDrink();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Drink getOrderDrink() {
        return orderDrink;
    }

    public void setOrderDrink(Drink orderDrink) {
        this.orderDrink = orderDrink;
    }

    /**
     * Generates formatted date and time for showing in the 1st column of tableview
     * @return string that represents formatted date from field @link sample.logic.OrderModel#orderDate
     * */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss  dd/MM/yyyy");
        return orderDate.format(formatter);
    }
}
