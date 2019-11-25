package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.beans.Order;
import sample.logic.OrderModel;

import java.util.ArrayList;
import java.util.List;

import static sample.logic.DataHandler.*;

public class Main extends Application {

    public static ObservableList<OrderModel> orderList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("COFFEE MACHINE");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (checkStorageFilePresent()) {
                    orderList.addAll(loadPreviousOrderList());
                } else {
                    generateAlert("Storage file", "Warning", "Application can't find file with previous orders. This info will not be loaded.'");
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                saveCurrentOrderList(orderList);
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
