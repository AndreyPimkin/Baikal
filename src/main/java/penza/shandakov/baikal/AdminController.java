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
import penza.shandakov.baikal.POJO.ForAdmin;
import penza.shandakov.baikal.POJO.ForCar;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogistician;
import penza.shandakov.baikal.server.DatabaseHandler;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ForAdmin, String> IDPacking;

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
    private ComboBox<String> boxCityFrom;

    @FXML
    private ComboBox<String> boxCityTo;

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
    private TableColumn<ForAdmin, String> distanceRate;

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
    private TableColumn<ForAdmin, String> fromCityRate;

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
    private TableColumn<ForAdmin, String> idRate;

    @FXML
    private RadioButton isAcc;

    @FXML
    private RadioButton isPack;

    @FXML
    private TableColumn<ForCar, Float> loadCapacityTransport;

    @FXML
    private TextField loadCapacityTransportAdd;

    @FXML
    private TableColumn<ForClient, Integer> driver;

    @FXML
    private TableColumn<ForCar, String> modelTransport;

    @FXML
    private TextField modelTransportAdd;

    @FXML
    private TableColumn<ForAdmin, String> namePacking;

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
    private Label errorInputTwo;

    @FXML
    private Label errorInputThree;

    @FXML
    private TextField numberPersonAdd;

    @FXML
    private TextField numberTransportAdd;

    @FXML
    private TableColumn<ForCar, String> numberTransport;

    @FXML
    private TableColumn<ForClient, String> packing;

    @FXML
    private TableColumn<ForClient, String> password;

    @FXML
    private TextField passwordPersonAdd;

    @FXML
    private TableColumn<ForClient, String> price;

    @FXML
    private TableColumn<ForAdmin, String> pricePacking;

    @FXML
    private TableColumn<ForClient, String> proportions;

    @FXML
    private TableColumn<ForClient, String> role;

    @FXML
    private TableColumn<ForClient, String> sent;

    @FXML
    private TableColumn<ForClient, Float> size;

    @FXML
    private TableColumn<ForCar, Float> sizeTransport;

    @FXML
    private TextField sizeTransportAdd;

    @FXML
    private TableColumn<ForClient, String> status;

    @FXML
    private TableColumn<ForCar, String> statusTransport;

    @FXML
    private TableView<ForLogistician> tableCargo;

    @FXML
    private TableView<ForClient> tableClient;

    @FXML
    private TableView<ForAdmin> tablePackage;

    @FXML
    private TableView<ForClient> tablePerson;

    @FXML
    private TableView<ForAdmin> tableRate;

    @FXML
    private TableView<ForCar> tableTransport;

    @FXML
    private TableColumn<ForClient, String> timeDelivery;

    @FXML
    private TableColumn<ForClient, String> toCity;

    @FXML
    private TableColumn<ForAdmin, String> toCityRate;

    @FXML
    private TableColumn<ForClient, String> transport;

    @FXML
    private TableColumn<ForClient, Float> weight;

    DatabaseHandler dbHandler = new DatabaseHandler();

    private final ObservableList<ForClient> clientList = FXCollections.observableArrayList();
    private final ObservableList<ForLogistician> cargoList = FXCollections.observableArrayList();
    private final ObservableList<ForClient> personList = FXCollections.observableArrayList();

    private final ObservableList<ForAdmin> packageList = FXCollections.observableArrayList();

    private final ObservableList<ForAdmin> rateList = FXCollections.observableArrayList();

    private final ObservableList<ForCar> carList = FXCollections.observableArrayList();

    private final String[] listRole = new String[]{"Логист", "Водитель", "Админ"};
    private String selectedRole = "";
    private String selectCity = "";
    private String selectCityFrom = "";
    private String selectCityTo = "";

    ForClient selectClient;
    ForCar selectCar;

    ForAdmin selectRate;

    ForAdmin selectPacking;


    @FXML
    void initialize() {
        //Клиенты
        try {
            initClient();

        } catch (SQLException e) {
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
        } catch (SQLException e) {
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

            } else {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            initPerson();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addPerson.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            if (idPersonAdd.getText().equals("")) {
                errorInput.setText("Введите ID");
                errorInput.setVisible(true);
            } else if (fullNamePersonAdd.getText().equals("")) {
                errorInput.setText("Введите ФИО");
                errorInput.setVisible(true);
            } else if (birthdayPersonAdd.getValue() == null) {
                errorInput.setText("Выберите дату рождения");
                errorInput.setVisible(true);
            } else if (selectedRole.equals("")) {
                errorInput.setText("Выберите роль");
                errorInput.setVisible(true);
            } else if (numberPersonAdd.getText().equals("")) {
                errorInput.setText("Введите телефон");
                errorInput.setVisible(true);
            } else if (numberPersonAdd.getText().matches("^8\\d{10}$")) {
                errorInput.setText("Неверный телефон");
                errorInput.setVisible(true);
            } else if (passwordPersonAdd.getText().equals("")) {
                errorInput.setText("Введите пароль");
                errorInput.setVisible(true);
            } else if (numberDocPersonAdd.getText().equals("")) {
                errorInput.setText("Введите номер и серию");
                errorInput.setVisible(true);
            } else if (dateDocPersonAdd.getValue() == null) {
                errorInput.setText("Выберите дату выдачи");
                errorInput.setVisible(true);
            } else if (experienceAdd.getText().equals("")) {
                errorInput.setText("Введите стаж");
                errorInput.setVisible(true);
            } else if (selectCity.equals("")) {
                errorInput.setText("Выберите город");
                errorInput.setVisible(true);
            } else {
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
                if (tablePerson.getSelectionModel().getSelectedItem() == null) {
                    errorInput.setVisible(true);
                    errorInput.setText("Выберите работника");
                } else {
                    selectClient = tableClient.getSelectionModel().getSelectedItem();
                    ForClient forClient = new ForClient();
                    forClient.setId(selectClient.getId());
                    try {
                        dbHandler.deletePerson(forClient);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTablePersonal();
                }

            } else {
                if (idPersonAdd.getText().equals("")) {
                    errorInput.setVisible(true);
                    errorInput.setText("Введите ID");
                } else {
                    ForClient forClient = new ForClient();
                    forClient.setId(idPersonAdd.getText());
                    try {
                        dbHandler.deletePerson(forClient);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTablePersonal();
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

        // Транспорт

        try {
            initTransport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        numberTransport.setCellValueFactory(new PropertyValueFactory<>("state"));
        modelTransport.setCellValueFactory(new PropertyValueFactory<>("model"));
        statusTransport.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadCapacityTransport.setCellValueFactory(new PropertyValueFactory<>("load_capacity"));
        sizeTransport.setCellValueFactory(new PropertyValueFactory<>("size"));
        tableTransport.setItems(carList);

        errorInputTwo.setVisible(false);
        addTransport.setOnAction(actionEvent -> {
            errorInputTwo.setVisible(false);
            if (numberTransportAdd.getText().equals("")) {
                errorInputTwo.setText("Введите номер");
                errorInputTwo.setVisible(true);
            } else if (statusTransport.getText().equals("")) {
                errorInputTwo.setText("Введите объем");
                errorInputTwo.setVisible(true);
            } else if (modelTransportAdd.getText().equals("")) {
                errorInputTwo.setText("Введите модель");
                errorInputTwo.setVisible(true);
            } else if (sizeTransportAdd.getText().equals("")) {
                errorInputTwo.setText("Введите грузоподъемность");
                errorInputTwo.setVisible(true);
            } else {
                ForCar forCar = new ForCar();
                forCar.setState(numberTransportAdd.getText());
                forCar.setModel(modelTransportAdd.getText());
                forCar.setStatus("Свободен");
                forCar.setLoad_capacity(Float.valueOf(modelTransportAdd.getText()));
                forCar.setSize(Float.valueOf(sizeTransportAdd.getText()));
                try {
                    dbHandler.addCar(forCar);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                errorInputTwo.setText("Машина " + numberTransportAdd.getText() + " добавлена");
                errorInputTwo.setVisible(true);
                refreshTableCar();
            }

        });

        deleteTransport.setOnAction(actionEvent -> {
            if (tableTransport.getSelectionModel().getSelectedItem() != null) {
                ForCar forCar = new ForCar();
                selectCar = tableTransport.getSelectionModel().getSelectedItem();
                forCar.setState(selectCar.getState());
                try {
                    dbHandler.deleteCar(forCar);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCar();
            } else if (!numberTransportAdd.getText().equals("")) {
                ForCar forCar = new ForCar();
                forCar.setState(numberTransportAdd.getText());
                try {
                    dbHandler.deleteCar(forCar);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                refreshTableCar();
            } else {
                errorInputTwo.setText("Выберите транспорт");
                errorInputTwo.setVisible(true);
            }
        });

        // Пятое окно
        try {
            initCityBox();
            initPackage();
            initRate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        boxCityFrom.setOnAction(this::getCityFrom);
        boxCityTo.setOnAction(this::getCityTo);
        tablePackage.setVisible(false);
        addPricePacking.setVisible(false);
        addNamePacking.setVisible(false);

        errorInputThree.setVisible(false);

        isPack.setOnAction(actionEvent -> {
            errorInputThree.setVisible(false);
            if (!(isPack.isSelected())) {
                isAcc.setText("Упаковки");
                tablePackage.setVisible(false);
                addPricePacking.setVisible(false);
                addNamePacking.setVisible(false);

                tableRate.setVisible(true);
                boxCityFrom.setVisible(true);
                boxCityTo.setVisible(true);
                addDistance.setVisible(true);


            } else {
                isAcc.setText("Тарифы");
                tablePackage.setVisible(true);
                addPricePacking.setVisible(true);
                addNamePacking.setVisible(true);

                tableRate.setVisible(false);
                boxCityFrom.setVisible(false);
                boxCityTo.setVisible(false);
                addDistance.setVisible(false);

            }
        });

        add.setOnAction(actionEvent -> {
            errorInputThree.setVisible(false);
            if (!(isPack.isSelected())) {
                if (namePacking.getText().equals("")) {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Укажите название");
                } else if (pricePacking.getText().equals("")) {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Выберите цену");
                } else {
                    ForAdmin forAdmin = new ForAdmin();
                    forAdmin.setNamePacking(namePacking.getText());
                    forAdmin.setPricePacking(pricePacking.getText());
                    try {
                        dbHandler.addPacking(forAdmin);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Упаковка добавлена");
                    refreshTablePackage();
                }
            } else {
                if (selectCityFrom.equals("")) {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Выберите город");
                } else if (selectCityTo.equals("")) {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Выберите город");
                } else if (distanceRate.getText().equals("")) {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Укажите дистанцию");
                } else {
                    ForAdmin forAdmin = new ForAdmin();
                    forAdmin.setFromCityRate(selectCityFrom);
                    forAdmin.setToCityRate(selectCityTo);
                    forAdmin.setDistanceRate(distanceRate.getText());
                    try {
                        dbHandler.addRate(forAdmin);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Тариф добавлен");
                    refreshTableRate();
                }
            }

        });

        delete.setOnAction(actionEvent -> {
            if (!(isPack.isSelected())) {
                if (tablePackage.getSelectionModel().getSelectedItem() != null) {
                    selectPacking = tablePackage.getSelectionModel().getSelectedItem();
                    ForAdmin forAdmin = new ForAdmin();
                    forAdmin.setIDPacking(selectPacking.getIDPacking());
                    try {
                        dbHandler.deletePacking(forAdmin);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTablePackage();
                } else {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Выберите упаковку");
                }
            } else {
                if (tableRate.getSelectionModel().getSelectedItem() != null) {
                    selectRate = tableRate.getSelectionModel().getSelectedItem();
                    ForAdmin forAdmin = new ForAdmin();
                    forAdmin.setIdRate(selectRate.getIdRate());
                    try {
                        dbHandler.deleteRate(forAdmin);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    refreshTableRate();
                } else {
                    errorInputThree.setVisible(true);
                    errorInputThree.setText("Выберите тариф");
                }
            }

        });


        IDPacking.setCellValueFactory(new PropertyValueFactory<>("IDPacking"));
        namePacking.setCellValueFactory(new PropertyValueFactory<>("namePacking"));
        pricePacking.setCellValueFactory(new PropertyValueFactory<>("pricePacking"));
        tablePackage.setItems(packageList);

        idRate.setCellValueFactory(new PropertyValueFactory<>("idRate"));
        fromCityRate.setCellValueFactory(new PropertyValueFactory<>("fromCityRate"));
        toCityRate.setCellValueFactory(new PropertyValueFactory<>("toCityRate"));
        distanceRate.setCellValueFactory(new PropertyValueFactory<>("distanceRate"));
        tableRate.setItems(rateList);


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

    private void initPackage() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getPacking();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            packageList.add(new ForAdmin(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
    }

    private void initRate() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getRate();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            rateList.add(new ForAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
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

    private void initCityBox() throws SQLException {
        ResultSet rs;
        rs = dbHandler.getCity();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxCityFrom.getItems().addAll(rs.getString(1));
            boxCityTo.getItems().addAll(rs.getString(1));
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

    private void initTransport() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getTransport();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            carList.add(new ForCar(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getFloat(4), rs.getFloat(5)));
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

    private void refreshTableCar() {
        tableTransport.getItems().clear();
        try {
            initTransport();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numberTransport.setCellValueFactory(new PropertyValueFactory<>("state"));
        modelTransport.setCellValueFactory(new PropertyValueFactory<>("model"));
        statusTransport.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadCapacityTransport.setCellValueFactory(new PropertyValueFactory<>("load_capacity"));
        sizeTransport.setCellValueFactory(new PropertyValueFactory<>("size"));
        tableTransport.setItems(carList);
    }

    private void refreshTablePackage() {
        tablePackage.getItems().clear();
        try {
            initPackage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IDPacking.setCellValueFactory(new PropertyValueFactory<>("IDPacking"));
        namePacking.setCellValueFactory(new PropertyValueFactory<>("namePacking"));
        pricePacking.setCellValueFactory(new PropertyValueFactory<>("pricePacking"));
        tablePackage.setItems(packageList);
    }


    private void refreshTableRate() {
        tableRate.getItems().clear();
        try {
            initRate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idRate.setCellValueFactory(new PropertyValueFactory<>("idRate"));
        fromCityRate.setCellValueFactory(new PropertyValueFactory<>("fromCityRate"));
        toCityRate.setCellValueFactory(new PropertyValueFactory<>("toCityRate"));
        distanceRate.setCellValueFactory(new PropertyValueFactory<>("distanceRate"));
        tableRate.setItems(rateList);
    }


    private void getCityFrom(ActionEvent actionEvent) {
        selectCityFrom = boxCityFrom.getValue();

    }

    private void getCityTo(ActionEvent actionEvent) {
        selectCityTo = boxCityTo.getValue();
    }


}
