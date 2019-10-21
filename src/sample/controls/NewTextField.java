package sample.controls;

import javafx.scene.control.TextField;

public class NewTextField extends TextField implements IControl {

    private String name;

    public NewTextField(String name, int prompt) {
        super(Integer.toString(prompt));
        this.name = name;
        super.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                super.setText(oldValue);
            }
        });
    }

    @Override
    public String getControlValue() {
        return super.getText();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
