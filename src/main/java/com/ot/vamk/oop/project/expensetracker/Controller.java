package com.ot.vamk.oop.project.expensetracker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.*;

// Handles the logic of all GUI events

public class Controller implements Initializable {
    HashMap<String, ArrayList<Product>> dayExpenses = new HashMap<>();
    XYChart.Series<String, Number> series = new XYChart.Series<>();

    @Override
    // Runs on startup
    // Populates ChoiceBoxes
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productCategory.getItems().addAll("Food & Drink", "Bills", "Car", "Technology", "Clothing");
    }

    @FXML
    // Adds a new product to the expenses of a specific date
    protected void onSubmitButtonClick() {
        String name = productName.getText();
        Double price = Double.parseDouble(productPrice.getText());
        String category = productCategory.getValue();
        String date = productDate.getValue().toString();

        productName.clear();
        productPrice.clear();
        productCategory.valueProperty().set(null);

        Product product = new Product(name, price, category);
        dayExpenses.computeIfAbsent(date, k -> new ArrayList<>()).add(product);
    }

    @FXML
    // Populates the list view and total expenses for a specific date
    protected void onExpenseCalendar() {
        productList.getItems().clear();
        String expDate = String.valueOf(expenseDate.getValue());
        double total = 0;

        // Populate expense list
        for(String date : dayExpenses.keySet()) {
            if(date.equals(expDate)) {
                productList.getItems().addAll(dayExpenses.get(date));
                for(Product product : dayExpenses.get(date)) {
                    total += product.getPrice();
                }
            }
        }

        totalExpenses.setText(String.valueOf(total));
    }

    @FXML
    // Saves HashMap contents to a file
    protected void onSaveButtonClick() throws IOException {
        String path = savePath.getText();
        savePath.clear();

        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(dayExpenses);

        fos.close();
        oos.close();
    }

    @FXML
    // Reads HashMap contents from a file
    protected void onReadButtonClick() throws IOException, ClassNotFoundException {
        String path = readPath.getText();
        readPath.clear();

        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        dayExpenses = (HashMap<String, ArrayList<Product>>) ois.readObject();

        fis.close();
        ois.close();
    }

    @FXML
    // Populates barchart with expenses by category
    protected void onGraphSelect() {
        barChart.getData().removeAll();
        series.getData().clear();
        Double foodAndDrink = 0.;
        Double bills = 0.;
        Double car = 0.;
        Double technology = 0.;
        Double clothing = 0.;

        for(String date : dayExpenses.keySet()) {
            for(Product product : dayExpenses.get(date)) {
                switch (product.getCategory()) {
                    case "Food & Drink":
                        foodAndDrink += product.getPrice();
                        break;
                    case "Bills":
                        bills += product.getPrice();
                        break;
                    case "Car":
                        car += product.getPrice();
                        break;
                    case "Technology":
                        technology += product.getPrice();
                        break;
                    case "Clothing":
                        clothing += product.getPrice();
                        break;
                }
            }
        }

        series.getData().add(new XYChart.Data<>("Food & Drink", foodAndDrink));
        series.getData().add(new XYChart.Data<>("Bills", bills));
        series.getData().add(new XYChart.Data<>("Car", car));
        series.getData().add(new XYChart.Data<>("Technology", technology));
        series.getData().add(new XYChart.Data<>("Clothing", clothing));

        if(!barChart.getData().contains(series)) {
            barChart.getData().add(series);
        }
    }

    //<editor-fold desc="FXML variables">

    @FXML
    private DatePicker productDate;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private ChoiceBox<String> productCategory;

    @FXML
    private DatePicker expenseDate;

    @FXML
    private ListView<Product> productList;

    @FXML
    private TextField totalExpenses;

    @FXML
    private TextField savePath;

    @FXML
    private TextField readPath;

    @FXML
    private BarChart<String, Number> barChart;

    //</editor-fold>
}
