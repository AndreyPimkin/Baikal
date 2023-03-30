package penza.shandakov.baikal;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import penza.shandakov.baikal.POJO.ForCar;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogistician;
import penza.shandakov.baikal.server.DatabaseHandler;

public class LogisticianController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCar;

    @FXML
    private ComboBox<String> boxDriver;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<ForClient, String> city;

    @FXML
    private Button confirmButton;

    @FXML
    private TableColumn<ForLogistician, String> description;

    @FXML
    private Label errorInput;

    @FXML
    private TableColumn<ForLogistician, String> fromCity;

    @FXML
    private TableColumn<ForClient, String> fullName;

    @FXML
    private TableColumn<ForClient, String> idClient;

    @FXML
    private TableColumn<ForLogistician, String> idClientCargo;

    @FXML
    private TableColumn<ForLogistician, String> numberCargo;

    @FXML
    private TableColumn<ForClient, String> phone;

    @FXML
    private TableColumn<ForLogistician, String> proportions;

    @FXML
    private TableColumn<ForLogistician, String> size;

    @FXML
    private TableView<ForClient> tableClient;

    @FXML
    private TableView<ForLogistician> tableCargo;

    @FXML
    private TableColumn<ForLogistician, String> toCity;

    @FXML
    private TableColumn<ForLogistician, String> weight;

    DatabaseHandler dbHandler = new DatabaseHandler();

    private final ObservableList<ForLogistician> cargoList = FXCollections.observableArrayList();
    private final ObservableList<ForClient> clientList = FXCollections.observableArrayList();

    private static Float val = 1000f;
    private static Float valTwo = 1f;

    private String selectCar = "";

    private String selectDriver = "";

    ForLogistician selectCargo;


    @FXML
    void initialize() {
        errorInput.setVisible(false);
        try {
            initClient();
            initCargo();
            initCar();
            initDriver();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boxCar.setOnAction(this::getCar);
        boxDriver.setOnAction(this::getDriver);

        idClientCargo.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        numberCargo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        proportions.setCellValueFactory(new PropertyValueFactory<>("proportions"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        fromCity.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCity.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableCargo.setItems(cargoList);

        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("city"));
        city.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableClient.setItems(clientList);

        tableCargo.setRowFactory(tv -> {
            TableRow<ForLogistician> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    errorInput.setVisible(false);
                    ForLogistician clickedRow = row.getItem();
                    val = clickedRow.getWeight();
                    valTwo = clickedRow.getSize();
                    refreshCar();
                }
            });
            return row ;
        });

        LocalDate ld = LocalDate.now();
        String formattedDate  = ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;

        confirmButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(selectCar.equals("")){
                errorInput.setVisible(true);
                errorInput.setText("Выберите транспорт");
            }
            else if(selectDriver.equals("")){
                errorInput.setVisible(true);
                errorInput.setText("Выберите водителя");
            }
            else if(tableCargo.getSelectionModel().getSelectedItem() == null){
                errorInput.setText("Выберите заявку");
            }
            else {
                selectCargo = tableCargo.getSelectionModel().getSelectedItem();
                ForLogistician forLogistician = new ForLogistician();
                forLogistician.setNumberCargo(selectCargo.getNumberCargo());
                forLogistician.setCar(selectCar.substring(0 , selectCar.indexOf(",")));
                forLogistician.setDriver(selectDriver.substring(0, selectDriver.indexOf(",")));
                forLogistician.setIdPersonal(AuthorizationController.idPerson);
                forLogistician.setSent(formattedDate);
                forLogistician.setStatus("Подтвержден");
                if(selectCargo.getFromCity().equals(selectCargo.getToCity())){
                    forLogistician.setTimeDelivery("1-2 дня");
                }
                else {
                    forLogistician.setTimeDelivery("4-7 дней");
                }

                try {
                    dbHandler.changeCargo(forLogistician);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCargo();
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(tableCargo.getSelectionModel().getSelectedItem() == null){
                errorInput.setText("Выберите заявку");
            }
            else {
                selectCargo = tableCargo.getSelectionModel().getSelectedItem();
                ForLogistician forLogistician = new ForLogistician();
                forLogistician.setNumberCargo(selectCargo.getNumberCargo());
                forLogistician.setIdPersonal(AuthorizationController.idPerson);
                forLogistician.setStatus("Отменен");

                try {
                    dbHandler.changeCargoTwo(forLogistician);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCargo();
            }
        });


    }

    private void initCar() throws SQLException {
        ResultSet rs;
        ForCar forCar = new ForCar();
        forCar.setLoad_capacity(val);
        forCar.setSize(valTwo);
        rs = dbHandler.getCar(forCar);
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxCar.getItems().addAll(rs.getString(1) + ", " + rs.getString(2) + ", " +
                            String.format("%.2f", rs.getFloat(4)) + " кг, " +  String.format("%.2f", rs.getFloat(5))+ " м^3");
        }
    }

    private void initDriver() throws SQLException {
        ResultSet rs;
        rs = dbHandler.getDriver();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxDriver.getItems().addAll(rs.getString(1) + ", " + rs.getString(2) + ", " +
                    rs.getString(5) + ", " + rs.getString(9));
        }
    }


    private void initClient() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getClientForLogistician();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            clientList.add(new ForClient(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4)));
        }
    }

    private void initCargo() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCargoForLogistician();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForLogistician(rs.getString(1), rs.getString(2),(
                    rs.getString(3) + ", " + rs.getString(4) + ", " +  rs.getString(5)),
                    rs.getFloat(6), rs.getFloat(7), rs.getString(8),
                    rs.getString(9), rs.getString(10)));
        }
    }

    private void refreshTableCargo() {
        tableCargo.getItems().clear();
        try {
            initCargo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idClientCargo.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        numberCargo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        proportions.setCellValueFactory(new PropertyValueFactory<>("proportions"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        fromCity.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCity.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableCargo.setItems(cargoList);
    }

    private void refreshCar() {
        boxCar.getItems().clear();
        try {
            initCar();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void getCar(ActionEvent actionEvent) {
        selectCar = boxCar.getValue();
    }

    private void getDriver(ActionEvent actionEvent) {
        selectDriver = boxDriver.getValue();
    }

}
