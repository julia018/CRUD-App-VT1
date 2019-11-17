package sample.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.beans.Composition;
import sample.beans.Drink;
import sample.beans.Order;
import sample.controls.IControl;
import sample.controls.NewChoiceBox;
import sample.controls.NewLabel;
import sample.controls.NewTextField;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static sample.Main.orderList;

public class DataHandler {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private static final int TOP = 10;
    private static final int RIGHT = 10;
    private static final int BOTTOM = 10;
    private static final int LEFT = 10;
    private static final int SPACING = 5;

    private static final String BEANSPACKAGE = "sample.beans.";
    private static final String STORAGEFILE = "res/OrderStorage.txt";
    //private static final String STORAGEFILEN = "res/OrderStorageN.txt";

    public static <T> ArrayList<Class<? extends T>> getDrinksNames(String pkgname, Class<T> T) {
        ArrayList<Class<? extends T>> classes = new ArrayList<>();
        // Get a File object for the package
        File directory = null;
        String fullPath;
        String relPath = pkgname.replace('.', '/');
        URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
        if (resource == null) {
            throw new RuntimeException("No resource for " + relPath);
        }
        try {
            directory = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(pkgname + " (" + resource + ") does not appear to be a valid URL / URI.  Strange, since we got it from the system...", e);
        } catch (IllegalArgumentException e) {
            directory = null;
        }
        if (directory != null && directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    String className = pkgname + '.' + files[i].substring(0, files[i].length() - 6);
                    try {
                        try {
                            if (T.isAssignableFrom(Class.forName(className)) && !className.equals(T.getName()))
                                classes.add((Class<? extends T>) Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            throw new Exception("Ошибка загрузки напитков.");
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        return classes;
    }

    private static ArrayList<Field> getClassFields(Class currentClass) {
        ArrayList<Field> classFields = new ArrayList<>();
        while (currentClass != null && currentClass != Object.class) {
            Collections.addAll(classFields, currentClass.getDeclaredFields());
            currentClass = currentClass.getSuperclass();
        }
        Collections.reverse(classFields);
        return classFields;
    }

    public static Stage generateOrderForm(String className, Object object) {
        Stage stage = new Stage();
        try {
            Class drinkClass = Class.forName(BEANSPACKAGE + className);
            ArrayList<Field> classFields = getClassFields(drinkClass);

            VBox vb = new VBox();
            vb.setSpacing(SPACING);
            vb.setPadding(new Insets(TOP, RIGHT, BOTTOM, LEFT));


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("ORDER -> " + className);
            stage.sizeToScene();

            List<IControl> controlsList = new ArrayList<>();
            generateControls(vb, controlsList, classFields, object);
            Button OKButton = new Button("Accept!");
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().add(OKButton);
            vb.getChildren().add(hb);
            int height = controlsList.size() * 70;
            stage.setScene(new Scene(vb, WIDTH, height));

            if (object == null) { //create
                OKButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        createOrder(drinkClass, controlsList, null);
                        stage.close();
                    }
                });
            } else { //edit
                OKButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        createOrder(drinkClass, controlsList, object);
                        stage.close();
                    }
                });
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stage;
    }

    private static void generateControls(VBox vbox, List<IControl> controlList, ArrayList<Field> fieldList, Object object) {
        fieldList.forEach(field -> {
            if (field.getType().equals(int.class)) {
                try {
                    generateTextField(field, vbox, object, controlList);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (field.getType().isEnum()) {
                try {
                    generateComboBox(field, vbox, object, controlList);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            if (Composition.class.isAssignableFrom(field.getType())) {
                Separator separator1 = new Separator(Orientation.HORIZONTAL);
                vbox.getChildren().add(separator1);
                ArrayList<Field> innerObjectFields = new ArrayList<>();
                Collections.addAll(innerObjectFields, field.getType().getDeclaredFields());
                String controlName = field.getName();
                NewLabel innerObjectLabel = new NewLabel(controlName, controlName);
                controlList.add(innerObjectLabel);
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().add(innerObjectLabel);
                vbox.getChildren().add(hb);
                Object innerObject = null;
                if (object != null) {
                    try {
                        Method getInnerObject = object.getClass().getMethod("get" + capitalizeFirstLetter(field.getName()));
                        innerObject = getInnerObject.invoke(object);//get comp obj-> res
                        innerObjectLabel.setObject(innerObject);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
                generateControls(vbox, controlList, innerObjectFields, innerObject);
                Separator separator2 = new Separator(Orientation.HORIZONTAL);
                vbox.getChildren().add(separator2);
            }

        });

    }

    private static void generateTextField(Field field, VBox vbox, Object object, List<IControl> controlList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String controlName = field.getName();
        Label controlLabel = new Label(controlName);
        vbox.getChildren().add(controlLabel);
        int prompt = 0;
        if (object != null) {
            Class objectClass = object.getClass();
            Method getValue = objectClass.getMethod("get" + capitalizeFirstLetter(controlName));
            prompt = (int) getValue.invoke(object);
        }
        NewTextField newTextField = new NewTextField(controlName, prompt);
        vbox.getChildren().add(newTextField);
        controlList.add(newTextField);
    }

    private static void generateComboBox(Field field, VBox vbox, Object object, List<IControl> controlList) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String controlName = field.getName();
        Label controlLabel = new Label(controlName);
        vbox.getChildren().add(controlLabel);
        ArrayList<Field> enumFields = new ArrayList<>();
        Collections.addAll(enumFields, field.getType().getFields());
        String value = "";
        if (object != null) {
            Class objectClass = object.getClass();
            Method getValue = objectClass.getMethod("get" + capitalizeFirstLetter(controlName));
            value = (String) getValue.invoke(object);
        }
        // list of fields-constants declared annotations
        ArrayList<String> enumConstants = new ArrayList<>();
        Object objectValue = null;
        for (Field enumField : enumFields) {
            enumConstants.add(enumField.getName());
            if (object != null && enumField.getName().equals(value))
                objectValue = enumField.getName();
        }
        NewChoiceBox chBox = new NewChoiceBox(FXCollections.observableArrayList(enumConstants), controlName, objectValue, enumFields);
        controlList.add(chBox);
        vbox.getChildren().add(chBox);
    }

    private static void createOrder(Class<? extends Drink> drinkClass, List<IControl> controlsList, Object object) {
        Drink drinkInstance = null;
        try {
            if (object == null) {
                Constructor constructor = drinkClass.getConstructor();
                drinkInstance = (Drink) constructor.newInstance();
            } else {
                drinkInstance = (Drink) object;
            }

            ArrayList<Method> methodList = new ArrayList<>();
            Class testClass = drinkClass;
            while (testClass != null && testClass != Object.class) {
                Collections.addAll(methodList, testClass.getDeclaredMethods());
                testClass = testClass.getSuperclass();
            }

            for (IControl control : controlsList) {
                if (control.getClass().equals(NewLabel.class)) { // composition
                    Object innerInstance = null;
                    Class innerObjectClass = Class.forName(BEANSPACKAGE + capitalizeFirstLetter(control.getName()));
                    if (object == null) {

                        Constructor innerConstructor = innerObjectClass.getConstructor();
                        innerInstance = innerConstructor.newInstance();
                    } else {
                        innerInstance = ((NewLabel) control).getObject();
                    }
                    //Class innerObjectClass = ((NewLabel) control).getObject().getClass();

                    ArrayList<Method> innerObjectMethodsList = new ArrayList<>();
                    Collections.addAll(innerObjectMethodsList, innerObjectClass.getDeclaredMethods());
                    for (Method method : innerObjectMethodsList) {
                        for (IControl control1 : controlsList) {
                            if (method.getName().equals("set" + capitalizeFirstLetter(control1.getName()))) {
                                method.invoke(innerInstance, control1.getControlValue());
                            }
                        }
                    }
                    ((NewLabel) control).setObject(innerInstance);
                }
                for (Method method : methodList) {
                    if (method.getName().equals("set" + capitalizeFirstLetter(control.getName()))) {
                        method.invoke(drinkInstance, control.getControlValue());
                    }
                }
            }

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException l) {
            System.out.println("Reflection exception!");
        }
        if (object == null) {
            Order newOrder = new Order(getCurrentDate(), drinkInstance);
            addOrderToList(orderList, new OrderModel(newOrder));
        }
    }

    private static void addOrderToList(ObservableList<OrderModel> orderList, OrderModel order) {
        orderList.add(order);
    }

    private static LocalDateTime getCurrentDate() {
        return LocalDateTime.now();

    }

    private static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static ObservableList<OrderModel> extractOrdersByDate(LocalDate dateTime, ObservableList<OrderModel> orders) {
        ObservableList<OrderModel> suitableOrders = FXCollections.observableArrayList();
        orders.forEach(element -> {
            if (element.getOrderDate().toLocalDate().equals(dateTime)) {
                suitableOrders.add(element);
            }
        });
        return suitableOrders;
    }

    public static ObservableList<OrderModel> sortOrderListByDrinkName(ObservableList<OrderModel> orderModelList) {
        orderModelList.sort(new OrderDrinkComparator());
        return orderModelList;
    }

    public static void getPreviousOrderList() {
        if (isStorageFilePresent()) {

        } else {

        }
    }

    public static boolean isStorageFilePresent() {
        File file = new File(STORAGEFILE);
        return file.exists();
    }

    public static boolean checkStorageFilePresent() {
        if (!isStorageFilePresent()) {
            Alert faultAlert = generateAlert("Orders' loading", "Results", "Could not find storage file(");
            faultAlert.show();
            return false;
        }
        return true;
    }

    public static Alert generateAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static void saveCurrentOrderList(ObservableList<OrderModel> orderList) {
        if (!isStorageFilePresent()) {
            //we must create new file for storing list
            File fileToWrite = new File(STORAGEFILE);
            try {
                fileToWrite.createNewFile();
            } catch (IOException ex) {
                generateAlert("System error", "Order storage file can't be created.", "Changes can't be saved...").show();
                return;
            }
        }
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(STORAGEFILE), "UTF-8")) {
            orderList.forEach(orderModel -> {
                try {
                    writeObject(orderModel.getOrder(), outputStreamWriter);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            //nothing to do: there was checking for file existance and UTF-8 is standart charset
            generateAlert("Warning", "Informatiom storage", "Could not find storage file. Changed data will not be saved.").show();
        }
    }

    private static void writeObject(Order order, OutputStreamWriter writer) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        String serializedOrder = serializeObject(order);
        writer.write(serializedOrder);
        writer.append("\r\n");
        writer.flush();
    }

    private static String serializeObject(Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder resultString = new StringBuilder("{");
        resultString.append(o.getClass().getName()).append(";");
        ArrayList<Field> fieldList = new ArrayList<>();
        ArrayList<Method> mainMethodList = new ArrayList<>();
        Class i = o.getClass();
        while (i != null && i != Object.class) {
            Collections.addAll(fieldList, i.getDeclaredFields());
            Collections.addAll(mainMethodList, i.getDeclaredMethods());
            i = i.getSuperclass();
        }
        Collections.reverse(fieldList);
        for (Field objectField : fieldList) {
            resultString.append(objectField.getName()).append("|");
            if (Composition.class.isAssignableFrom(objectField.getType())) {
                resultString.append(serializeInnerObject(objectField, o)).append(';');
            } else {
                if (Drink.class.isAssignableFrom(objectField.getType())) {
                    /*Method getDrinkObject = o.getClass().getMethod("get"+capitalizeFirstLetter(objectField.getName()));
                    Drink drink = (Drink) getDrinkObject.invoke(o);
                    resultString.append(serializeObject(drink)).append(';');*/
                    resultString.append(serializeInnerObject(objectField, o)).append(';');
                } else {
                    for (Method objectMethod : mainMethodList) {
                        if (objectMethod.getName().equals("get" + capitalizeFirstLetter(objectField.getName()))) {
                            String value = objectMethod.invoke(o).toString();
                            resultString.append(value).append(";");
                        }
                    }
                }
            }
        }
        resultString.append("}");
        return resultString.toString();
    }

    private static String serializeInnerObject(Field compObjectField, Object mainObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> compClass = compObjectField.getType();
        Method getCompObject = mainObject.getClass().getMethod("get" + capitalizeFirstLetter(compObjectField.getName()));
        Object compObject = getCompObject.invoke(mainObject);
        return serializeObject(compObject);
    }

    public static ObservableList<OrderModel> loadPreviousOrderList() {
        ObservableList<OrderModel> obj_list = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(STORAGEFILE)))) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                try {
                    Order obj = (Order) deserializeObject(strLine);
                    System.out.println(obj.getDrink().getContainer());
                    obj_list.add(new OrderModel(obj));
                    //br.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            //System.out.println("Fault caused while opening file!");
            e.printStackTrace();
        }
        return obj_list;
    }

    private static Object deserializeObject(String objectInString) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int i = 1;
        Object resultObject;
        StringBuilder mainClassName = new StringBuilder();
        ArrayList<Method> methodList = new ArrayList<>();
        HashMap<String, String> mainObjFields = new HashMap<>();
        while (!(objectInString.charAt(i) == ';')) {
            mainClassName.append(objectInString.charAt(i));
            i++;
        }
        i++;
        resultObject = createObject(mainClassName.toString());
        Class mainClass = Class.forName(mainClassName.toString());
        mainClass.cast(resultObject);
        Class a = mainClass;
        while (a != null && a != Object.class) {
            Collections.addAll(methodList, a.getDeclaredMethods());
            a = a.getSuperclass();
        }
        while (i < (objectInString.length() - 1)) {
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            while (!(objectInString.charAt(i) == '|')) {
                key.append(objectInString.charAt(i));
                i++;
            }
            i++;
            //object in composition 1
            if (objectInString.charAt(i) == '{') {
                i++;
                Object drinkObject;
                StringBuilder drinkClassName = new StringBuilder();
                ArrayList<Method> drinkMethodList = new ArrayList<>();
                HashMap<String, String> drinkObjFields = new HashMap<>();
                while (!(objectInString.charAt(i) == ';')) {
                    drinkClassName.append(objectInString.charAt(i));
                    i++;
                }
                i++;
                drinkObject = createObject(drinkClassName.toString());
                Class a1 = drinkObject.getClass();
                while (a1 != null && a1 != Object.class) {
                    Collections.addAll(drinkMethodList, a1.getDeclaredMethods());
                    a1 = a1.getSuperclass();
                }
                while (objectInString.charAt(i) != '}') {
                    StringBuilder key1 = new StringBuilder();
                    StringBuilder value1 = new StringBuilder();
                    while (!(objectInString.charAt(i) == '|')) {
                        key1.append(objectInString.charAt(i));
                        i++;
                    }
                    i++;
                    //object in composition 2
                    if (objectInString.charAt(i) == '{') {
                        i++;
                        StringBuilder className = new StringBuilder();
                        while (!(objectInString.charAt(i) == ';')) {
                            className.append(objectInString.charAt(i));
                            i++;
                        }
                        i++;
                        Object compObject = createObject(className.toString());
                        Class<?> compClass = Class.forName(className.toString());
                        ArrayList<Method> compMethodsList = new ArrayList<>();
                        Collections.addAll(compMethodsList, compClass.getDeclaredMethods());
                        HashMap<String, String> objFields = new HashMap<>();
                        while (!(objectInString.charAt(i) == '}')) {
                            StringBuilder objectFieldDescription = new StringBuilder();
                            while (!(objectInString.charAt(i) == ';')) {
                                objectFieldDescription.append(objectInString.charAt(i));
                                i++;
                            }
                            String[] out = objectFieldDescription.toString().split("\\|");
                            String fieldName = out[0];
                            String fieldValue = out[1];
                            objFields.put(fieldName, fieldValue);
                            i++;
                        }
                        i++;
                        setObjectFields(compMethodsList, objFields.entrySet(), compObject);
                        //set inner object to drink
                        for (Method drinkObjMethod : drinkMethodList) {
                            if (drinkObjMethod.getName().equals("set" + capitalizeFirstLetter(key1.toString()))) {
                                drinkObjMethod.invoke(drinkObject, compObject);
                            }
                        }
                        i++;

                    } else {
                        while (!(objectInString.charAt(i) == ';')) {
                            value1.append(objectInString.charAt(i));
                            i++;
                        }
                        i++;
                        drinkObjFields.put(key1.toString(), value1.toString());
                    }
                }
                i++;
                setObjectFields(drinkMethodList, drinkObjFields.entrySet(), drinkObject);
                for (Method mainObjMethod : methodList) {
                    if (mainObjMethod.getName().equals("set" + capitalizeFirstLetter(key.toString()))) {
                        mainObjMethod.invoke(resultObject, drinkObject);
                    }
                }
                i++;

            } else {
                while (!(objectInString.charAt(i) == ';')) {
                    value.append(objectInString.charAt(i));
                    i++;
                }
                i++;
                mainObjFields.put(key.toString(), value.toString());
            }
        }
        i++;
        setObjectFields(methodList, mainObjFields.entrySet(), resultObject);
        return resultObject;
    }


    private static void setObjectFields(ArrayList<Method> methodList, Set<Map.Entry<String, String>> entrySet, Object resultObject) throws InvocationTargetException, IllegalAccessException {
        for (Method mainObjectMethod : methodList) {
            for (Map.Entry entry : entrySet) {
                if (mainObjectMethod.getName().equals("setDate") && !mainObjectMethod.getParameterTypes()[0].equals(String.class))
                    continue;
                if (("set" + capitalizeFirstLetter(entry.getKey().toString())).equals(mainObjectMethod.getName())) {

                    if (mainObjectMethod.getParameterTypes()[0].equals(Boolean.class)) {
                        mainObjectMethod.invoke(resultObject, Boolean.valueOf((String) entry.getValue()));

                    } else

                        mainObjectMethod.invoke(resultObject, (String) entry.getValue());
                }
            }
        }
    }

    private static Object createObject(String className) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        Class clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        Object newObject = constructor.newInstance();
        clazz.cast(newObject);
        return newObject;
    }
}
