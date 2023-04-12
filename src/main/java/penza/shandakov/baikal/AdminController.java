package penza.shandakov.baikal;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableColumn<ForClient, String> birthday;

    @FXML
    private TableColumn<ForClient, String> birthdayClient;

    @FXML
    private DatePicker birthdayPersonAdd;

    @FXML
    private ComboBox<String> boxCity;

    @FXML
    private ComboBox<?> boxCityFrom;

    @FXML
    private ComboBox<?> boxCityTo;

    @FXML
    private ComboBox<String> boxRole;

    @FXML
    private TableColumn<ForClient, String> cityClient;

    @FXML
    private TableColumn<ForClient, String> cityPerson;

    @FXML
    private TableColumn<ForClient, String> dateDocPerson;

    @FXML
    private DatePicker dateDocPersonAdd;

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
    private TableColumn<ForClient, String> experience;

    @FXML
    private TextField experienceAdd;

    @FXML
    private TableColumn<ForClient, String> fromCity;

    @FXML
    private TableColumn<?, ?> fromCityRate;

    @FXML
    private TableColumn<ForClient, String> fullNameClient;

    @FXML
    private TableColumn<ForClient, String> fullNamePerson;

    @FXML
    private TextField fullNamePersonAdd;

    @FXML
    private TableColumn<ForClient, String> idClient;

    @FXML
    private TableColumn<ForLogistician, String> idClientCargo;

    @FXML
    private TableColumn<ForClient, String> idPerson;

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
    private TableColumn<ForClient, String> numberDocPerson;

    @FXML
    private TextField numberDocPersonAdd;

    @FXML
    private TableColumn<ForClient, String> numberPerson;

    @FXML
    private Label errorInput;

    @FXML
    private TextField numberPersonAdd;

    @FXML
    private TextField numberTransportAdd;

    @FXML
    private TableColumn<?, ?> numberTransport;

    @FXML
    private TableColumn<ForClient, String> packing;

    @FXML
    private TableColumn<ForClient, String> password;

    @FXML
    private TextField passwordPersonAdd;

    @FXML
    private TableColumn<ForClient, String> price;

    @FXML
    private TableColumn<?, ?> pricePacking;

    @FXML
    private TableColumn<ForClient, String> proportions;

    @FXML
    private TableColumn<ForClient, String> role;

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
    private TableView<ForClient> tablePerson;

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

    private final ObservableList<ForClient> personList = FXCollections.observableArrayList();

    private final String[] listRole = new String[]{"Логист", "Водитель", "Админ"};
    private String selectedRole = "";
    private String selectCity = "";

    ForClient selectClient;


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


        //Работники
        tablePerson.setVisible(true);
        addPerson.setDisable(true);
        idPersonAdd.setVisible(false);
        fullNamePersonAdd.setVisible(false);
        birthdayPersonAdd.setVisible(false);
        boxRole.setVisible(false);
        numberPersonAdd.setVisible(false);
        passwordPersonAdd.setVisible(false);
        numberDocPersonAdd.setVisible(false);
        dateDocPersonAdd.setVisible(false);
        experienceAdd.setVisible(false);
        boxCity.setVisible(false);
        errorInput.setVisible(false);

        isAcc.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if (!(isAcc.isSelected())) {
                isAcc.setText("Добавить");
                tablePerson.setVisible(true);
                addPerson.setDisable(true);
                idPersonAdd.setVisible(false);
                fullNamePersonAdd.setVisible(false);
                birthdayPersonAdd.setVisible(false);
                boxRole.setVisible(false);
                numberPersonAdd.setVisible(false);
                passwordPersonAdd.setVisible(false);
                numberDocPersonAdd.setVisible(false);
                dateDocPersonAdd.setVisible(false);
                experienceAdd.setVisible(false);
                boxCity.setVisible(false);

            }
            else {
                isAcc.setText("Таблица");
                tablePerson.setVisible(false);
                addPerson.setDisable(false);
                idPersonAdd.setVisible(true);
                fullNamePersonAdd.setVisible(true);
                birthdayPersonAdd.setVisible(true);
                boxRole.setVisible(true);
                numberPersonAdd.setVisible(true);
                passwordPersonAdd.setVisible(true);
                numberDocPersonAdd.setVisible(true);
                dateDocPersonAdd.setVisible(true);
                experienceAdd.setVisible(true);
                boxCity.setVisible(true);
            }
        });

        boxRole.getItems().addAll(this.listRole);
        boxRole.setOnAction(this::getRole);
        boxCity.setOnAction(this::getCity);

        try {
            initCity();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            initPerson();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addPerson.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if(idPersonAdd.getText().equals("")){
                errorInput.setText("Введите ID");
                errorInput.setVisible(true);
            }
            else if(fullNamePersonAdd.getText().equals("")){
                errorInput.setText("Введите ФИО");
                errorInput.setVisible(true);
            }
            else if(birthdayPersonAdd.getValue() == null){
                errorInput.setText("Выберите дату рождения");
                errorInput.setVisible(true);
            }
            else if(selectedRole.equals("")){
                errorInput.setText("Выберите роль");
                errorInput.setVisible(true);
            }
            else if(numberPersonAdd.getText().equals("")){
                errorInput.setText("Введите телефон");
                errorInput.setVisible(true);
            }

            else  if (numberPersonAdd.getText().matches("^8\\d{10}$")){
                errorInput.setText("Неверный телефон");
                errorInput.setVisible(true);
            }

            else if(passwordPersonAdd.getText().equals("")){
                errorInput.setText("Введите пароль");
                errorInput.setVisible(true);
            }
            else if(numberDocPersonAdd.getText().equals("")){
                errorInput.setText("Введите номер и серию");
                errorInput.setVisible(true);
            }

            else if(dateDocPersonAdd.getValue() == null){
                errorInput.setText("Выберите дату выдачи");
                errorInput.setVisible(true);
            }
            else if(experienceAdd.getText().equals("")){
                errorInput.setText("Введите стаж");
                errorInput.setVisible(true);
            }

            else if(selectCity.equals("")){
                errorInput.setText("Выберите город");
                errorInput.setVisible(true);
            }
            else{
                ForClient forClient = new ForClient();
                forClient.setId(idPersonAdd.getText());
                forClient.setFullName(fullNamePersonAdd.getText());
                forClient.setBirthday(String.valueOf(birthdayPersonAdd.getValue()));
                forClient.setRole(selectedRole);
                forClient.setPhone(numberPersonAdd.getText());
                forClient.setPassword(AuthorizationController.getMd5(passwordPersonAdd.getText()));
                forClient.setNumberDoc(numberDocPersonAdd.getText());
                forClient.setDateDoc(String.valueOf(dateDocPersonAdd.getValue()));
                forClient.setExperience((experienceAdd.getText()));
                forClient.setCity(selectCity);
                try {
                    dbHandler.addPerson(forClient);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                errorInput.setText("Работник " + idPersonAdd.getText() + " добавлен");
                errorInput.setVisible(true);
                refreshTablePersonal();
            }
        });

        deletePerson.setOnAction(actionEvent -> {
            if (!(isAcc.isSelected())) {
                if(tablePerson.getSelectionModel().getSelectedItem() == null){
                    errorInput.setVisible(true);
                    errorInput.setText("Выберите работника");
                }
                else{
                    selectClient = tableClient.getSelectionModel().getSelectedItem();
                    ForClient forClient = new ForClient();
                    forClient.setId(selectClient.getId());
                    try {
                        dbHandler.deletePerson(forClient);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            else {
                if(idPersonAdd.getText().equals("")){
                    errorInput.setVisible(true);
                    errorInput.setText("Введите ID");
                }
                else{
                    ForClient forClient = new ForClient();
                    forClient.setId(idPersonAdd.getText());
                    try {
                        dbHandler.deletePerson(forClient);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        idPerson.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNamePerson.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        numberPerson.setCellValueFactory(new PropertyValueFactory<>("phone"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        dateDocPerson.setCellValueFactory(new PropertyValueFactory<>("dateDoc"));
        numberDocPerson.setCellValueFactory(new PropertyValueFactory<>("numberDoc"));
        cityPerson.setCellValueFactory(new PropertyValueFactory<>("city"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        tablePerson.setItems(personList);



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

    private void initCity() throws SQLException {
        ResultSet rs;
        rs = dbHandler.getCity();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxCity.getItems().addAll(rs.getString(1));
        }
    }

    private void initPerson() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getPerson();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            personList.add(new ForClient(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                    rs.getString(8), rs.getString(9), rs.getString(10)));
        }
    }


    private void getRole(ActionEvent actionEvent) {
        selectedRole = boxRole.getValue();
    }

    private void getCity(ActionEvent actionEvent) {
        selectCity = boxCity.getValue();
    }

    private void refreshTablePersonal() {
        tablePerson.getItems().clear();
        try {
            initPerson();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idPerson.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNamePerson.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        numberPerson.setCellValueFactory(new PropertyValueFactory<>("phone"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        dateDocPerson.setCellValueFactory(new PropertyValueFactory<>("dateDoc"));
        numberDocPerson.setCellValueFactory(new PropertyValueFactory<>("numberDoc"));
        cityPerson.setCellValueFactory(new PropertyValueFactory<>("city"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        tablePerson.setItems(personList);
    }



}
