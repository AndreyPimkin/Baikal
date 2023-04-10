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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogistician;
import penza.shandakov.baikal.server.DatabaseHandler;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> IDPacking;

    @FXML
    private Button add;

    @FXML
    private TextField addDistance;

    @FXML
    private TextField addNamePacking;

    @FXML
    private Button addPerson;

    @FXML
    private TextField addPricePacking;

    @FXML
    private Button addTransport;

    @FXML
    private TableColumn<?, ?> birthday;

    @FXML
    private TableColumn<ForClient, String> birthdayClient;

    @FXML
    private DatePicker birthdayPersonAdd;

    @FXML
    private ComboBox<?> boxCity;

    @FXML
    private ComboBox<?> boxCityFrom;

    @FXML
    private ComboBox<?> boxCityTo;

    @FXML
    private ComboBox<?> boxRole;

    @FXML
    private TableColumn<ForClient, String> cityClient;

    @FXML
    private TableColumn<?, ?> cityPerson;

    @FXML
    private TableColumn<?, ?> dateDocPerson;

    @FXML
    private DatePicker datePas;

    @FXML
    private Button delete;

    @FXML
    private Button deletePerson;

    @FXML
    private Button deleteTransport;

    @FXML
    private TableColumn<ForClient, String> delivered;

    @FXML
    private TableColumn<ForClient, String> description;

    @FXML
    private TableColumn<?, ?> distanceRate;

    @FXML
    private TableColumn<ForClient, String> docNumberClient;

    @FXML
    private TableColumn<ForClient, String> docDateClient;

    @FXML
    private TableColumn<ForClient, String> passwordClient;

    @FXML
    private TableColumn<?, ?> experience;

    @FXML
    private TextField experienceAdd;

    @FXML
    private TableColumn<ForClient, String> fromCity;

    @FXML
    private TableColumn<?, ?> fromCityRate;

    @FXML
    private TableColumn<ForClient, String> fullNameClient;

    @FXML
    private TableColumn<?, ?> fullNamePerson;

    @FXML
    private TextField fullNamePersonAdd;

    @FXML
    private TableColumn<ForClient, String> idClient;

    @FXML
    private TableColumn<ForLogistician, String> idClientCargo;

    @FXML
    private TableColumn<?, ?> idPerson;

    @FXML
    private TextField idPersonAdd;

    @FXML
    private TableColumn<?, ?> idRate;

    @FXML
    private RadioButton isAcc;

    @FXML
    private RadioButton isPack;

    @FXML
    private TableColumn<?, ?> loadCapacityTransport;

    @FXML
    private TextField loadCapacityTransportAdd;

    @FXML
    private TableColumn<ForClient, Integer> driver;

    @FXML
    private TableColumn<?, ?> modelTransport;

    @FXML
    private TextField modelTransportAdd;

    @FXML
    private TableColumn<?, ?> namePacking;

    @FXML
    private TableColumn<ForClient, String> numberCargo;

    @FXML
    private TableColumn<ForClient, String> numberClient;

    @FXML
    private TableColumn<?, ?> numberDocPerson;

    @FXML
    private TextField numberPas;

    @FXML
    private TableColumn<?, ?> numberPerson;

    @FXML
    private TextField numberPersonAdd;

    @FXML
    private TextField numberTransportAdd;

    @FXML
    private TableColumn<?, ?> numberTransport;

    @FXML
    private TableColumn<ForClient, String> packing;

    @FXML
    private TableColumn<?, ?> password;

    @FXML
    private TextField passwordPersonAdd;

    @FXML
    private TableColumn<ForClient, String> price;

    @FXML
    private TableColumn<?, ?> pricePacking;

    @FXML
    private TableColumn<ForClient, String> proportions;

    @FXML
    private TableColumn<?, ?> role;

    @FXML
    private TableColumn<ForClient, String> sent;

    @FXML
    private TableColumn<ForClient, Float> size;

    @FXML
    private TableColumn<?, ?> sizeTransport;

    @FXML
    private TextField sizeTransportAdd;

    @FXML
    private TableColumn<ForClient, String> status;

    @FXML
    private TableColumn<?, ?> statusTransport;

    @FXML
    private TableView<ForLogistician> tableCargo;

    @FXML
    private TableView<ForClient> tableClient;

    @FXML
    private TableView<?> tablePackage;

    @FXML
    private TableView<?> tablePerson;

    @FXML
    private TableView<?> tableRate;

    @FXML
    private TableView<?> tableTransport;

    @FXML
    private TableColumn<ForClient, String> timeDelivery;

    @FXML
    private TableColumn<ForClient, String> toCity;

    @FXML
    private TableColumn<?, ?> toCityTo;

    @FXML
    private TableColumn<ForClient, String> transport;

    @FXML
    private TableColumn<ForClient, Float> weight;

    DatabaseHandler dbHandler = new DatabaseHandler();

    private final ObservableList<ForClient> clientList = FXCollections.observableArrayList();
    private final ObservableList<ForLogistician> cargoList = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        //Клиенты
        try {
            initClient();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameClient.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthdayClient.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        numberClient.setCellValueFactory(new PropertyValueFactory<>("phone"));
        passwordClient.setCellValueFactory(new PropertyValueFactory<>("password"));
        docNumberClient.setCellValueFactory(new PropertyValueFactory<>("dateDoc"));
        docDateClient.setCellValueFactory(new PropertyValueFactory<>("numberDoc"));
        cityClient.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableClient.setItems(clientList);

        //Доставки
        try {
            initCargo();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        idClientCargo.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        numberCargo.setCellValueFactory(new PropertyValueFactory<>("numberCargo"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        proportions.setCellValueFactory(new PropertyValueFactory<>("proportions"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        packing.setCellValueFactory(new PropertyValueFactory<>("packing"));
        fromCity.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCity.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        transport.setCellValueFactory(new PropertyValueFactory<>("car"));
        driver.setCellValueFactory(new PropertyValueFactory<>("idPersonal"));
        timeDelivery.setCellValueFactory(new PropertyValueFactory<>("timeDelivery"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
        delivered.setCellValueFactory(new PropertyValueFactory<>("delivered"));
        tableCargo.setItems(cargoList);



    }

    private void initClient() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getClientForAdmin();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            clientList.add(new ForClient(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
    }

    private void initCargo() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCargoForAdmin();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForLogistician(rs.getString(1), rs.getString(2), rs.getString(3),
                    (rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6)),
                    rs.getFloat(7), rs.getFloat(8), rs.getString(9), rs.getString(10),
                    rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14),
                    rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18)));
        }
    }



}
