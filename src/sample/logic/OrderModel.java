package sample.logic;

import sample.beans.Drink;
import sample.beans.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OrderModel {

    private Order order;
    private LocalDateTime orderDate;
    private Drink orderDrink;


    public OrderModel(Order order) {
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

    public LocalDateTime getOrderDate() {
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

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss  dd/MM/yyyy");
        return orderDate.format(formatter);
    }
}
