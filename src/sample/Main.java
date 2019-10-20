package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.beans.Order;
import sample.logic.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static ObservableList<OrderModel> orderList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
