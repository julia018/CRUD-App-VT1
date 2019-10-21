package sample.logic;

import sample.beans.Order;

import java.util.Comparator;

public class OrderDrinkComparator implements Comparator<OrderModel> {

    @Override
    public int compare(OrderModel o1, OrderModel o2) {
        return o1.getOrderDrink().getClass().getSimpleName().compareTo(o2.getOrderDrink().getClass().getSimpleName());
    }
}
