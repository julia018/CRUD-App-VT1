package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.beans.Drink;

import java.util.ArrayList;

import static sample.logic.DataHandler.generateOrderForm;
import static sample.logic.DataHandler.getDrinksNames;

public class Controller {

    @FXML
    private ComboBox<String> drinksComboBox;

    @FXML
    private Button chooseButton;

    @FXML
    void initialize() {
        ArrayList<Class<? extends Drink>> availabeDrinks = getDrinksNames("sample.beans", Drink.class);
        availabeDrinks.forEach(aClass -> drinksComboBox.getItems().add(aClass.getSimpleName()));
        drinksComboBox.getSelectionModel().select(0);
    }


    @FXML
    void makeOrder(ActionEvent event) {
        String chosenDrinkName = drinksComboBox.getSelectionModel().getSelectedItem();
        Stage orderStage = generateOrderForm(chosenDrinkName, null);
        orderStage.show();
    }

}
