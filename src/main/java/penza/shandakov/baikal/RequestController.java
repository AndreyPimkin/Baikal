package penza.shandakov.baikal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class RequestController {
    @FXML
    private ComboBox<String> boxFrom;
    @FXML
    private ComboBox<String> boxTo;

    @FXML
    private Button buttonOpenPersonalAccount;

    @FXML
    private Button countButton;

    @FXML
    private TextField description;

    @FXML
    private TextField heightInput;

    @FXML
    private ImageView imageTracking;

    @FXML
    private TextField lengthInput;

    @FXML
    private Button makeRequestButton;

    @FXML
    private ComboBox<String> boxPacking;

    @FXML
    private Label price;

    @FXML
    private TextField sizeInput;

    @FXML
    private Label errorInput;

    @FXML
    private TextField weightInput;

    @FXML
    private TextField widthInput;


    private String number;
    private String packing = "Упаковка в пузырчатую полиэтиленовую пленку";
    private float packingPrice;

    public static boolean checkBack = true;

    private boolean checkInfo = false;

    DatabaseHandler dbHandler = new DatabaseHandler();
    Random random = new Random();

    ForClient forClient = new ForClient();

    @FXML
    void initialize() {
        ResultSet resultAuto;
        forClient = new ForClient();
        forClient.setId(String.valueOf(AuthorizationController.idClient));
        resultAuto = dbHandler.checkInfoClient(forClient);

        try {
            checkInfo = resultAuto.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        buttonOpenPersonalAccount.setOnAction(actionEvent -> {
            AuthorizationController.openWindow("/penza/shandakov/baikal/personalAccount.fxml", buttonOpenPersonalAccount, "Личный кабинет");
        });
        imageTracking.setOnMouseClicked(mouseEvent -> {
            AuthorizationController.openWindow("/penza/shandakov/baikal/tracking.fxml", buttonOpenPersonalAccount, "Отслеживание");
            checkBack = true;
        });

        errorInput.setVisible(false);

        boxFrom.setPromptText(MainController.selectCityFrom);
        boxTo.setPromptText(MainController.selectCityTo);
        sizeInput.setText(String.valueOf(MainController.size));
        weightInput.setText(String.valueOf(MainController.weight));

        try {
            initCity();
            initPackage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boxFrom.setOnAction(this::getCityFrom);
        boxTo.setOnAction(this::getCityTo);
        boxPacking.setOnAction(this::getPacking);
        boxFrom.setValue("Пенза");
        boxTo.setValue("Москва");
        boxPacking.setValue("Упаковка в пузырчатую полиэтиленовую пленку");
        try {
            getPackingPrice();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        price.setText(String.format("%.2f", MainController.total + packingPrice * MainController.size));


        countButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            MainController.size = Float.parseFloat(sizeInput.getText());
            MainController.weight = Float.parseFloat(weightInput.getText());
            if (MainController.size <= 0) {
                errorInput.setVisible(true);
                errorInput.setText("Выберите объем");
            } else if (MainController.weight <= 0) {
                errorInput.setVisible(true);
                errorInput.setText("Выберите массу");
            }
            else {
                MainController.density = (MainController.weight / MainController.size); // плотность
                if (MainController.weight <= 1500) { //Зависит от массы груза
                    MainController.rateInCity = 1;
                    MainController.rateOutCity = 0.19f;
                } else if (MainController.weight > 1500 && MainController.weight < 5000) {
                    MainController.rateInCity = 1.3f;
                    MainController.rateOutCity = 0.25f;
                } else if (MainController.weight >= 5000 && MainController.weight < 10000) {
                    MainController.rateInCity = 1.57f;
                    MainController.rateOutCity = 0.3f;
                } else if (MainController.weight >= 10000 && MainController.weight <= 20000) {
                    MainController.rateInCity = 2.63f;
                    MainController.rateOutCity = 0.5f;
                }
                if (MainController.selectCityFrom.equals(MainController.selectCityTo)) { // Если по Пензе
                    if (MainController.density > MainController.rateStandard) { // Если груз тяжелый
                        MainController.total = MainController.rateBigWeight * MainController.rateInCity;
                    } else { // Если груз крупногабаритный
                        MainController.total = MainController.rateBigSize * MainController.rateInCity;
                    }
                }
                else { // Вне города
                    if (MainController.density > MainController.rateStandard) { // Если груз тяжелый
                        MainController.total = MainController.weight * 0.003f * MainController.rateOutCity * MainController.distance;
                    } else { // Если груз крупногабаритный
                        MainController.total = (MainController.density * 250) * MainController.rateOutCity * MainController.distance * 0.0015f;
                    }
                    if (MainController.total < 500) {
                        MainController.total = 500;
                    }
                }
                price.setText(String.format("%.2f", MainController.total + packingPrice * MainController.size));
            }

        });



        makeRequestButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(checkInfo){
                if(MainController.total <= 0){
                    errorInput.setVisible(true);
                    errorInput.setText("Ошибка в подсчетах цены");
                }
                else if (MainController.size != Float.parseFloat(lengthInput.getText()) * Float.parseFloat(widthInput.getText()) * Float.parseFloat(heightInput.getText()) ) {
                    errorInput.setVisible(true);
                    errorInput.setText("Ошибка в подсчетах объема");
                }
                else if(MainController.size > 100){
                    errorInput.setVisible(true);
                    errorInput.setText("Слишком большой объем!");
                }
                else {
                    number = generateNumber();
                    ForCargo forCargo = new ForCargo();
                    forCargo.setNumber(number);
                    forCargo.setIDClient(String.valueOf(AuthorizationController.idClient));
                    if(description.getText().equals("")){
                        forCargo.setDescription("Отсутствует");
                    }
                    else{
                        forCargo.setDescription(description.getText());
                    }
                    forCargo.setLength(lengthInput.getText());
                    forCargo.setWidth(widthInput.getText());
                    forCargo.setHeight(heightInput.getText());
                    forCargo.setSize(String.valueOf(MainController.size));
                    forCargo.setWeight(String.valueOf(MainController.weight));
                    forCargo.setPrice(String.valueOf(MainController.total + packingPrice * MainController.size));
                    forCargo.setPacking(packing);
                    forCargo.setCityFrom(MainController.selectCityFrom);
                    forCargo.setCityTo(MainController.selectCityTo);
                    forCargo.setNumberTwo(number);
                    try {
                        dbHandler.addCargo(forCargo);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    errorInput.setVisible(true);
                    errorInput.setText("Заявка отправлена");
                }
            }

            else{
                errorInput.setVisible(true);
                errorInput.setText("Заполните профиль");
            }
        });


    }

    private void initPackage() throws SQLException {
        ResultSet rs;
        rs = dbHandler.getPackage();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxPacking.getItems().addAll(rs.getString(1));
        }
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


    private void getDistance() throws SQLException {
        ResultSet rs;
        ForClient forClient = new ForClient();
        forClient.setCityFrom(MainController.selectCityFrom);
        forClient.setCityTo(MainController.selectCityTo);
        rs = dbHandler.getDistanceCity(forClient);
        if (rs.next()) {
            MainController.distance = rs.getInt(1);
        } else {
            MainController.distance = 600;
        }
    }

    private void getPackingPrice() throws SQLException {
        ResultSet rs;
        ForCargo forCargo = new ForCargo();
        forCargo.setPacking(packing);
        rs = dbHandler.getPricePacking(forCargo);
        if (rs.next()) {
            packingPrice = rs.getFloat(1);
        }

    }

    private void getCityFrom(ActionEvent actionEvent) {
        MainController.selectCityFrom = boxFrom.getValue();
        try {
            getDistance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCityTo(ActionEvent actionEvent) {
        MainController.selectCityTo = boxTo.getValue();
        try {
            getDistance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPacking(ActionEvent actionEvent) {
        packing = boxPacking.getValue();
        try {
            getPackingPrice();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateNumber() {
        String codeRandom = "";
        int rrr;
        for (int i = 0; i < 10; i++) {
            rrr = random.nextInt(1, 3);
            if (rrr == 1) {
                codeRandom += (char) random.nextInt(65, 90);
            } else if (rrr == 2) {
                codeRandom += (char) random.nextInt(48, 57);
            } else {
                codeRandom += (char) random.nextInt(97, 122);
            }
        }
        return ("19IT18." + codeRandom + ".RU");
    }

    public void pressedEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            MainController.size = Float.parseFloat(lengthInput.getText()) * Float.parseFloat(widthInput.getText()) * Float.parseFloat(heightInput.getText());
            sizeInput.setText(String.valueOf(MainController.size));
        }
    }

}
