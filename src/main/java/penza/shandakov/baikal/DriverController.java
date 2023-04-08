package penza.shandakov.baikal;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogistician;
import penza.shandakov.baikal.server.DatabaseHandler;

public class DriverController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button acceptButton;

    @FXML
    private ComboBox<String> boxStatus;

    @FXML
    private Button cancelButton;

    @FXML
    private Button cancelButtonTwo;

    @FXML
    private TableColumn<ForLogistician, String> description;

    @FXML
    private Label errorInput;

    @FXML
    private Label errorInputTwo;

    @FXML
    private TableColumn<ForLogistician, String> fromCity;

    @FXML
    private TableColumn<ForLogistician, String> fromCityTwo;

    @FXML
    private TableColumn<ForLogistician, String> numberCargo;

    @FXML
    private TableColumn<ForLogistician, String> numberCargoTwo;

    @FXML
    private TableColumn<ForLogistician, String> proportions;

    @FXML
    private TableColumn<ForLogistician, String> sent;

    @FXML
    private TableColumn<ForLogistician, String> sentTwo;

    @FXML
    private TableColumn<ForLogistician, String> size;

    @FXML
    private TableColumn<ForLogistician, String> status;

    @FXML
    private TableView<ForLogistician> tableCargo;

    @FXML
    private TableView<ForLogistician> tableCargoTwo;

    @FXML
    private TableColumn<ForLogistician, String> toCity;

    @FXML
    private TableColumn<ForLogistician, String> toCityTwo;

    @FXML
    private TableColumn<ForLogistician, String> transport;

    @FXML
    private TableColumn<ForLogistician, String> transportTwo;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<ForLogistician, String> weight;

    DatabaseHandler dbHandler = new DatabaseHandler();

    private final ObservableList<ForLogistician> cargoList = FXCollections.observableArrayList();
    private final ObservableList<ForLogistician> cargoAcceptedList = FXCollections.observableArrayList();

    ForLogistician selectCargo;

    @FXML
    void initialize() {
        errorInput.setVisible(false);
        errorInputTwo.setVisible(false);
        try {
            initCargo();
            initAcceptedCargo();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        numberCargo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
        transport.setCellValueFactory(new PropertyValueFactory<>("car"));
        proportions.setCellValueFactory(new PropertyValueFactory<>("proportions"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        fromCity.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCity.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableCargo.setItems(cargoList);

        numberCargoTwo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        transportTwo.setCellValueFactory(new PropertyValueFactory<>("car"));
        fromCityTwo.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityTwo.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        sentTwo.setCellValueFactory(new PropertyValueFactory<>("sent"));
        tableCargoTwo.setItems(cargoAcceptedList);

        acceptButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(tableCargo.getSelectionModel().getSelectedItem() == null){
                errorInput.setVisible(true);
                errorInput.setText("Выберите доставку");
            }
            else {
                selectCargo = tableCargo.getSelectionModel().getSelectedItem();
                ForLogistician forLogistician = new ForLogistician();
                forLogistician.setNumberCargo(selectCargo.getNumberCargo());
                forLogistician.setStatus("Принято в доставку");
                try {
                    dbHandler.changeCargoForDriver(forLogistician);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCargo();
                refreshTableCargoTwo();
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(tableCargo.getSelectionModel().getSelectedItem() == null){
                errorInput.setVisible(true);
                errorInput.setText("Выберите доставку");
            }
            else {
                selectCargo = tableCargo.getSelectionModel().getSelectedItem();
                ForLogistician forLogistician = new ForLogistician();
                forLogistician.setNumberCargo(selectCargo.getNumberCargo());
                forLogistician.setStatus("Отклонено водителем");
                try {
                    dbHandler.changeCargoForDriver(forLogistician);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCargo();
            }
        });


    }

    private void initCargo() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCargoForDriver();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForLogistician(rs.getString(1), rs.getString(2),rs.getString(3),
                    (rs.getString(4) + ", " + rs.getString(5) + ", " +  rs.getString(6)),
                    rs.getFloat(7), rs.getFloat(8), rs.getString(9), rs.getString(10), rs.getString(11)));
        }
    }

    private void initAcceptedCargo() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCargoAcceptedForDriver();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoAcceptedList.add(new ForLogistician(rs.getString(1), rs.getString(2),rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6)));
        }
    }


    private void refreshTableCargo() {
        tableCargo.getItems().clear();
        try {
            initCargo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numberCargo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
        transport.setCellValueFactory(new PropertyValueFactory<>("car"));
        proportions.setCellValueFactory(new PropertyValueFactory<>("proportions"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        fromCity.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCity.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableCargo.setItems(cargoList);
    }

    private void refreshTableCargoTwo() {
        tableCargoTwo.getItems().clear();
        try {
            initCargo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numberCargoTwo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        transportTwo.setCellValueFactory(new PropertyValueFactory<>("car"));
        fromCityTwo.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityTwo.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        status.setCellValueFactory(new PropertyValueFactory<>(" status"));
        sentTwo.setCellValueFactory(new PropertyValueFactory<>("sent"));
        tableCargoTwo.setItems(cargoAcceptedList);
    }


}
