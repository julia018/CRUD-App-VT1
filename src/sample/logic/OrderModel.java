package sample.logic;

import sample.beans.Drink;
import sample.beans.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderModel {

    private Order order;
    private Date orderDate;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Drink getOrderDrink() {
        return orderDrink;
    }

    public void setOrderDrink(Drink orderDrink) {
        this.orderDrink = orderDrink;
    }

    public String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(orderDate);
    }
}
