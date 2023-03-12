package penza.shandakov.baikal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    @FXML
    private ComboBox<String> boxFrom;
    @FXML
    private ComboBox<String> boxTo;
    @FXML
    private Button buttonResult;
    @FXML
    private Button buttonOpenPersonalAccount;
    @FXML
    private Label price;
    @FXML
    private Slider sliceSize;
    @FXML
    private Slider sliceWeight;
    @FXML
    private TextField textSize;
    @FXML
    private TextField textWeight;

    public static String selectCityFrom = "Пенза";
    public static String selectCityTo = "Москва";
    public static float weight = 1;
    public static float size = 0.1f;
    public static float total = 0;
    public static float density = 0;
    public static float rateInCity = 1;
    public static float rateOutCity = 0.15f;

    public static float distance = 654;

    public static boolean checkRequest = false;

    public static final int rateBigWeight = 450;
    public static final int rateBigSize = 550;
    public static final int rateStandard = 250;



    DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    void initialize() {
        textWeight.setText(String.format("%.2f", weight) + " кг");
        textSize.setText(String.format("%.2f",size) + " м^3");

        try {
            initCity();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boxFrom.setOnAction(this::getCityFrom);
        boxTo.setOnAction(this::getCityTo);
        boxFrom.setValue("Пенза");
        boxTo.setValue("Москва");

        // метод для шкалы
        sliceWeight.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue) {
                weight = (float) sliceWeight.getValue(); //вес
                textWeight.setText(String.format("%.2f", weight) + " кг");
                costCalculation();
            }
        });
        // метод для шкалы
        sliceSize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue) {
                size = (float) sliceSize.getValue(); //Объем
                textSize.setText(String.format("%.2f",size) + " м^3");
                costCalculation();
            }
        });
        // кнопки открытия новых окон
        buttonResult.setOnAction(actionEvent -> {
            ResultSet resultAuto;
            ForClient forClient = new ForClient();
            forClient.setId(String.valueOf(AuthorizationController.idClient));
            resultAuto = dbHandler.checkInfoClient(forClient);
            try {
                if(AuthorizationController.checkAuthorization && resultAuto.next()){
                    AuthorizationController.openWindow("/penza/shandakov/baikal/request.fxml", buttonResult, "Оформление заявки");
                }
                else{
                    AuthorizationController.openWindow("/penza/shandakov/baikal/authorization.fxml", buttonResult, "Авторизация");
                    checkRequest = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        buttonOpenPersonalAccount.setOnAction(actionEvent -> {
            if(AuthorizationController.checkAuthorization){
                AuthorizationController.openWindow("/penza/shandakov/baikal/personalAccount.fxml", buttonResult, "Личный кабинет");
            }
            else{
                AuthorizationController.openWindow("/penza/shandakov/baikal/authorization.fxml", buttonResult, "Авторизация");
            }
        });
    }

    private void getCityFrom(ActionEvent actionEvent) {
        selectCityFrom = boxFrom.getValue();
        try {
            getDistance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        costCalculation();

    }

    private void getCityTo(ActionEvent actionEvent) {
        selectCityTo = boxTo.getValue();
        try {
            getDistance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        costCalculation();
    }

    private void initCity() throws SQLException {
        ResultSet rs;
        rs = dbHandler.getCity();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxFrom.getItems().addAll(rs.getString(1));
            boxTo.getItems().addAll(rs.getString(1));
        }
    }

    // Метод получения дистанции между городами
    private void getDistance() throws SQLException {
        ResultSet rs;
        ForClient forClient = new ForClient();
        forClient.setCityFrom(selectCityFrom);
        forClient.setCityTo(selectCityTo);
        rs = dbHandler.getDistanceCity(forClient);
        if (rs.next()){
            distance = rs.getInt(1);
        }
        else{
            distance = 600;
        }
    }

    private void costCalculation() {
        density = (weight / size); // плотность
        if(weight <= 1500){ //Зависит от массы груза
            rateInCity = 1;
            rateOutCity = 0.19f;
        }
        else if(weight > 1500 && weight < 5000){
            rateInCity = 1.3f;
            rateOutCity = 0.25f;
        }
        else if(weight >= 5000 && weight < 10000){
            rateInCity = 1.57f;
            rateOutCity = 0.3f;
        }
        else if(weight >= 10000 && weight <= 20000){
            rateInCity = 2.63f;
            rateOutCity = 0.5f;
        }
        if (selectCityFrom.equals(selectCityTo)) { // Если по Пензе
            if (density > rateStandard) { // Если груз тяжелый
                total = rateBigWeight * rateInCity;
            }
            else { // Если груз крупногабаритный
                total = rateBigSize * rateInCity;
            }
        }
        else { // Вне города
            if (density > rateStandard) { // Если груз тяжелый
                total = weight * 0.003f * rateOutCity * distance;
            }
            else { // Если груз крупногабаритный
                total = (density * 250) * rateOutCity * distance * 0.0015f;
            }
            if(total < 500){
                total = 500;
            }
        }
        price.setText("от " + String.format("%.2f", total) + " ₽");
    }

}