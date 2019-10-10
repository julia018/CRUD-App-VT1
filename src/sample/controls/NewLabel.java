package sample.controls;

import javafx.scene.control.Label;

public class NewLabel extends Label implements IControl {

    private String name;
    private Object object;

    public NewLabel(String text, String name) {
        super(text);
        this.name = name;
        this.object = null;
    }


    @Override
    public Object getControlValue() {
        return object;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
