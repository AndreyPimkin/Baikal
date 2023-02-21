package penza.shandakov.baikal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import penza.shandakov.baikal.POJO.ForCar;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Admin {
    @FXML private Button add;
    @FXML private Button addCar;
    @FXML private TableColumn<ForClient, String> birthday;
    @FXML
    private TableView<ForCar> car;
    @FXML private DatePicker birthdayPersonAdd;
    @FXML private ComboBox<String> boxCity;
    @FXML private ComboBox<String> boxRole;
    @FXML private TableView<ForCargo> cargo;
    @FXML private TableColumn<ForCar, String>  city;
    @FXML private TableView<ForClient> client;
    @FXML private TableColumn<ForClient, String> dateDoc;
    @FXML private DatePicker datePas;
    @FXML private Button delete;
    @FXML private Button deleteCar;
    @FXML private TableColumn<ForClient, String> exp;
    @FXML private TextField expAdd;
    @FXML private TableColumn<ForCargo, String> from;
    @FXML private TableColumn<ForClient, String> fullnameClient;
    @FXML private TableColumn<ForClient, String> fullnamePerson;
    @FXML private TableColumn<ForCargo, String> idClient;
    @FXML private TableColumn<ForClient, String> idClientClient;
    @FXML private TableColumn<ForClient, String> idPerson;
    @FXML private TextField idPersonAdd;
    @FXML private TableColumn<ForCar, String> load;
    @FXML private TableColumn<ForCar, String> cityTwo;
    @FXML private TextField loadAdd;
    @FXML private TableColumn<ForCar, String> model;
    @FXML private TextField modelAdd;
    @FXML private TableColumn<ForCar, String> number;
    @FXML private TextField numberCarAdd;
    @FXML private TableColumn<ForCargo, String> numberCargo;
    @FXML private TableColumn<ForClient, String> numberClient;
    @FXML private TableColumn<ForClient, String> numberDoc;
    @FXML private TextField numberPas;
    @FXML private TableColumn<ForClient, String> numberPerson;
    @FXML private TextField numberPersonAdd;
    @FXML private TextField fullnamePersonAdd;
    @FXML private TableColumn<ForClient, String> password;
    @FXML private TextField passwordPersonAdd;
    @FXML private TableView<ForClient> person;
    @FXML private TableColumn<ForClient, String> role;
    @FXML private TableColumn<ForCargo, String> sent;
    @FXML private TableColumn<ForCar, String> status;
    @FXML private TableColumn<ForCargo, String> to;
    @FXML private TableColumn<ForCar, String> vin;
    @FXML private TextField vinAdd;
    DatabaseHandler dbHandler = new DatabaseHandler();
    private final ObservableList<ForClient> clientList = FXCollections.observableArrayList();
    private final ObservableList<ForCargo> cargoList = FXCollections.observableArrayList();
    private final ObservableList<ForCar> carList = FXCollections.observableArrayList();
    private final ObservableList<ForClient> personList = FXCollections.observableArrayList();
    private String selectCity = "";
    private String selectRole = "";
    private final String[] listCity = new String[]{"Пенза", "Москва", "Рязань", "Тамбов", "Саратов", "Лопатино"};
    private final String[] listRole = new String[]{"Водитель", "Админ", "Логист"};

    @FXML
    void initialize() {
        boxCity.getItems().addAll(this.listCity);
        boxRole.getItems().addAll(this.listRole);

        boxCity.setOnAction(this::getCity);
        boxRole.setOnAction(this::getRole);

        try {
            initClient();
            initCargo();
            initCar();
            initPerson();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        idClientClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnameClient.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        cityTwo.setCellValueFactory(new PropertyValueFactory<>("city"));
        numberClient.setCellValueFactory(new PropertyValueFactory<>("phone"));
        client.setItems(clientList);

        idClient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        numberCargo.setCellValueFactory(new PropertyValueFactory<>("number"));
        from.setCellValueFactory(new PropertyValueFactory<>("city_from"));
        to.setCellValueFactory(new PropertyValueFactory<>("city_to"));
        sent.setCellValueFactory(new PropertyValueFactory<>("status"));
        cargo.setItems(cargoList);

        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        load.setCellValueFactory(new PropertyValueFactory<>("load"));
        car.setItems(carList);

        idPerson.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnamePerson.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("day"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        numberPerson.setCellValueFactory(new PropertyValueFactory<>("phone"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        dateDoc.setCellValueFactory(new PropertyValueFactory<>("dateDoc"));
        numberDoc.setCellValueFactory(new PropertyValueFactory<>("numberDoc"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        exp.setCellValueFactory(new PropertyValueFactory<>("pat"));
        person.setItems(personList);


        addCar.setOnAction(actionEvent -> {
            ForCar forCar = new ForCar();
            forCar.setNumber(numberCarAdd.getText());
            forCar.setModel(modelAdd.getText());
            forCar.setStatus("Свободно");
            forCar.setVin(vinAdd.getText());
            forCar.setLoad(loadAdd.getText());
            try {
                dbHandler.addCar(forCar);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTableCar();
        });

        deleteCar.setOnAction(actionEvent -> {
            ForCar forCar = new ForCar();
            forCar.setNumber(numberCarAdd.getText());

            try {
                dbHandler.deleteCar(forCar);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTableCar();
        });

        add.setOnAction(actionEvent -> {
            ForClient forClient = new ForClient();
            forClient.setId(idPersonAdd.getText());
            forClient.setFullName(fullnamePersonAdd.getText());
            forClient.setBirthday(String.valueOf(birthdayPersonAdd.getValue()));
            forClient.setRole(selectRole);
            forClient.setPhone(numberPersonAdd.getText());
            forClient.setPassword(passwordPersonAdd.getText());
            forClient.setDateDoc(String.valueOf(datePas.getValue()));
            forClient.setNumberDoc(numberPas.getText());
            forClient.setCity(selectCity);
            forClient.setPatronymic(expAdd.getText());
            try {
                dbHandler.addPerson(forClient);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTablePerson();

        });

        delete.setOnAction(actionEvent -> {
            ForClient forClient = new ForClient();
            forClient.setId(idPersonAdd.getText());
            try {
                dbHandler.deletePerson(forClient);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTablePerson();
        });




    }
    private void initClient() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getClient();
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
        rs = dbHandler.getCargoTwo();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForCargo(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5)));
        }
    }

    private void initCar() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCar();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            carList.add(new ForCar(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5)));
        }
    }

    private void initPerson() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getPerson();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            personList.add(new ForClient(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7), rs.getString(8),
                    rs.getString(9), rs.getString(10)));
        }
    }

    private void refreshTableCar() {
        car.getItems().clear();
        try {
            initCar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        load.setCellValueFactory(new PropertyValueFactory<>("load"));
        car.setItems(carList);
    }

    private void refreshTablePerson() {
        person.getItems().clear();
        try {
            initPerson();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idPerson.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnamePerson.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("day"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        numberPerson.setCellValueFactory(new PropertyValueFactory<>("phone"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        dateDoc.setCellValueFactory(new PropertyValueFactory<>("dateDoc"));
        numberDoc.setCellValueFactory(new PropertyValueFactory<>("numberDoc"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        exp.setCellValueFactory(new PropertyValueFactory<>("pat"));
        person.setItems(personList);
    }

    private void getCity(ActionEvent actionEvent) {
        selectCity = boxCity.getValue();
    }

    private void getRole(ActionEvent actionEvent) {
        selectRole = boxRole.getValue();
    }

}
