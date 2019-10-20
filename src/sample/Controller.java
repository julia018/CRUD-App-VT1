package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.beans.Drink;
import sample.beans.Order;
import sample.logic.OrderModel;

import java.util.ArrayList;

import static sample.Main.orderList;
import static sample.logic.DataHandler.generateOrderForm;
import static sample.logic.DataHandler.getDrinksNames;

public class Controller {

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
    void initialize() {
        orderList.addListener((ListChangeListener<OrderModel>) c -> orderTableView.setItems(orderList));
        ArrayList<Class<? extends Drink>> availableDrinks = getDrinksNames("sample.beans", Drink.class);
        availableDrinks.forEach(aClass -> drinksComboBox.getItems().add(aClass.getSimpleName()));
        drinksComboBox.getSelectionModel().select(0);

        //configure TableView for appropriate display
        dateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFormattedDate()));
        drinkColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrderDrink().getClass().getSimpleName()));
    }


    @FXML
    void makeOrder(ActionEvent event) {
        String chosenDrinkName = drinksComboBox.getSelectionModel().getSelectedItem();
        Stage orderStage = generateOrderForm(chosenDrinkName, null);
        orderStage.show();
    }

    private static void configureOrderTableView(TableView tableView) {


    }

}
