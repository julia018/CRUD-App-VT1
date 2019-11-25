package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.beans.Drink;
import sample.logic.OrderModel;

import java.time.LocalDate;
import java.util.ArrayList;

import static sample.Main.orderList;
import static sample.logic.DataHandler.*;

public class Controller {

    private final int BUTTONSPACING = 30;

    @FXML
    private ComboBox<String> drinksComboBox;

    @FXML
    private TableView<OrderModel> orderTableView;

    @FXML
    private TableColumn<OrderModel, String> dateColumn;

    @FXML
    private TableColumn<OrderModel, String> drinkColumn;

    @FXML
    private TableColumn<OrderModel, Void> actionColumn;

    @FXML
    private Button chooseButton;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private Button sortButton;

    @FXML
    void initialize() {
        orderList.addListener((ListChangeListener<OrderModel>) c -> {
            orderTableView.refresh();
            orderTableView.setItems(orderList);
        });
        ArrayList<Class<? extends Drink>> availableDrinks = null;
        try {
            availableDrinks = getDrinksNames("sample.beans", Drink.class);
        } catch (Exception e) {
            generateAlert("Warning", "Drink types' loading error", "Application can't get available drink types, sorry...").showAndWait();
            System.exit(-1);
        }
        availableDrinks.forEach(aClass -> drinksComboBox.getItems().add(aClass.getSimpleName()));
        drinksComboBox.getSelectionModel().select(0);

        //configure DatePicker for filter
        orderDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == null) {
                orderTableView.setItems(FXCollections.observableArrayList(orderList));
            } else {
                LocalDate chosenDate = orderDatePicker.getValue();
                orderTableView.setItems(extractOrdersByDate(chosenDate, orderList));
            }
        });

        sortButton.setTooltip(new Tooltip("Sort orders by drink's kind"));

        //configure TableView for appropriate display
        dateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFormattedDate()));
        drinkColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrderDrink().getClass().getSimpleName()));
        actionColumn.setCellFactory(param -> new TableCell<OrderModel, Void>() {
            private final Button editButton = new Button("edit");
            private final Button deleteButton = new Button("delete");
            private final HBox pane = new HBox(editButton, deleteButton);

            {

                pane.setSpacing(BUTTONSPACING);

                deleteButton.setOnAction(event -> {
                    OrderModel deletedOrder = getTableView().getItems().get(getIndex());
                    deletedOrder.getOrder().deleteObject();
                    deletedOrder.setOrder(null);
                    orderList.remove(deletedOrder);
                });

                editButton.setOnAction(event -> {
                    OrderModel chosenOrder = getTableView().getItems().get(getIndex());
                    Stage editOrderStage = generateOrderForm(chosenOrder.getOrderDrink().getClass().getSimpleName(), chosenOrder.getOrderDrink());
                    editOrderStage.show();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }


    @FXML
    void makeOrder(ActionEvent event) {
        String chosenDrinkName = drinksComboBox.getSelectionModel().getSelectedItem();
        Stage createOrderStage = generateOrderForm(chosenDrinkName, null);
        createOrderStage.show();
    }

    @FXML
    void sortOrderList(ActionEvent event) {
        ObservableList<OrderModel> displayedOrderList = FXCollections.observableArrayList(orderTableView.getItems());
        sortOrderListByDrinkName(displayedOrderList);
        orderTableView.setItems(displayedOrderList);
    }

}
