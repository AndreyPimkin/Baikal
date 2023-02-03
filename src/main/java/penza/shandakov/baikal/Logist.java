package penza.shandakov.baikal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import penza.shandakov.baikal.POJO.ForLogist;
import penza.shandakov.baikal.POJO.ForTableClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Logist {

    @FXML private ComboBox<String> boxCar;

    @FXML private ComboBox<String> boxDriver;
    @FXML private TableColumn<ForLogist, String> from;
    @FXML private TableColumn<ForLogist, String> fullname;
    @FXML private Button no;
    @FXML private TableColumn<ForLogist, String> number;
    @FXML private TableColumn<ForLogist, String> ob;

    @FXML private Button okey;
    @FXML private TableColumn<ForLogist, String> price;
    @FXML private TableView<ForLogist> tableLogist;

    @FXML private TableColumn<ForLogist, String> to;
    @FXML private TableColumn<ForLogist, String> ves;
    DatabaseHandler dbHandler = new DatabaseHandler();
    private final ObservableList<ForLogist> cargoList = FXCollections.observableArrayList();
    private String selectDriver;
    private String selectCar;
    ForLogist select;
    private String formattedDate;

    @FXML
    void initialize() {
        try {
            initDataDriver(); // методы для заполнения
            initDataCar();
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boxDriver.setOnAction(this::getDriver); // реагирует при выборе какого-либо элемента из спика
        boxCar.setOnAction(this::getCar);
        LocalDate ld = LocalDate.now();
        formattedDate = ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ForLogist forLogist = new ForLogist();
        // кнопка подтверждения заказа
        okey.setOnAction(actionEvent -> {
            select = tableLogist.getSelectionModel().getSelectedItem();
            forLogist.setState(selectCar.substring(selectCar.indexOf(" ") + 1));
            forLogist.setId_personal(selectDriver.substring(selectDriver.lastIndexOf(" ") + 1));
            if(select.getFrom().equals(select.getTo())){ // смотрит города
                forLogist.setTime("1-2 дня");
            }
            else {
                forLogist.setTime("4-7 дней");
            }
            forLogist.setStatus("Сформирован");
            forLogist.setFrom(formattedDate);
            forLogist.setNumber(select.getNumber());

            try {
                dbHandler.changeCargo(forLogist);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTable();
        });

        // кнопка отклонения заказа
        no.setOnAction(actionEvent -> {
            select = tableLogist.getSelectionModel().getSelectedItem();
            forLogist.setState(selectCar.substring(selectCar.indexOf(" ") - 1));
            forLogist.setId_personal(selectDriver.substring(selectDriver.lastIndexOf(" ") + 1));
            if(select.getFrom().equals(select.getTo())){
                forLogist.setTime("1-2 дня");
            }
            else {
                forLogist.setTime("4-7 дней");
            }
            forLogist.setStatus("Отклонено");
            forLogist.setFrom(formattedDate);
            forLogist.setNumber(select.getNumber());
            try {
                dbHandler.changeCargo(forLogist);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            refreshTable();
        });

        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        ves.setCellValueFactory(new PropertyValueFactory<>("ves"));
        ob.setCellValueFactory(new PropertyValueFactory<>("ob"));
        from.setCellValueFactory(new PropertyValueFactory<>("to"));
        to.setCellValueFactory(new PropertyValueFactory<>("from"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableLogist.setItems(cargoList);

    }
    // метод заполнения таблицы товаров
    private void initData() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getBook();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForLogist(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7)));
        }
    }
    // метод заполнения водителей
    private void initDataDriver() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getDriver();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxDriver.getItems().addAll(rs.getString("fullname") + " " +rs.getString("id_personal"));
        }
    }
    // метод заполнения машин
    private void initDataCar() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getTransport();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            boxCar.getItems().addAll(rs.getString("model") + " " +rs.getString("state"));
        }
    }

    private void getDriver(ActionEvent actionEvent) {
        selectDriver = String.valueOf(boxDriver.getValue());
    }
    private void getCar(ActionEvent actionEvent) {

        selectCar = String.valueOf(boxCar.getValue());
    }
    // обновление таблицы
    private void refreshTable() {
        tableLogist.getItems().clear();
        try {
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        ves.setCellValueFactory(new PropertyValueFactory<>("ves"));
        ob.setCellValueFactory(new PropertyValueFactory<>("ob"));
        from.setCellValueFactory(new PropertyValueFactory<>("to"));
        to.setCellValueFactory(new PropertyValueFactory<>("from"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableLogist.setItems(cargoList);
    }





}