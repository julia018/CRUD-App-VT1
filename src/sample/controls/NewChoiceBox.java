package sample.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NewChoiceBox extends ChoiceBox implements IControl {

    private String name;

    public NewChoiceBox(ObservableList items, String name, Object object, ArrayList<Field> enumFields) {
        super(items);
        this.name = name;
        if (object == null) {
            super.getSelectionModel().select(0);
        } else {
            super.getSelectionModel().select(object);
        }
    }

    @Override
    public Object getControlValue() {
        String value = super.getSelectionModel().getSelectedItem().toString();
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

}
