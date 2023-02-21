package penza.shandakov.baikal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import penza.shandakov.baikal.POJO.ForTableClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinTask;


public class Info {
    @FXML private TableView<ForTableClient> cargoTable;
    @FXML private TableColumn<ForkJoinTask, String> deliv;
    @FXML private ImageView imageCon;
    @FXML private TableColumn<ForkJoinTask, String> number;
    @FXML private Button person;
    @FXML private TableColumn<ForkJoinTask, String> sent;
    @FXML private TableColumn<ForkJoinTask, String> status;

    DatabaseHandler dbHandler = new DatabaseHandler();
    private final ObservableList<ForTableClient> cargoList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            initData(); // вызывается метод заполнения таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // заполняются столбики таблицы
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
        deliv.setCellValueFactory(new PropertyValueFactory<>("deliv"));
        cargoTable.setItems(cargoList);

        // откроет окно оформления заявки
        imageCon.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/cargo.fxml", person, "Оформление заявки");
        });


        // откроет окно  личного кабинета
        person.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/personalAccount.fxml", person, "Личный кабинет");
        });


    }

    // метод заполнения таблицы
    private void initData() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getCargo();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForTableClient(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4)));
        }
    }

    // метод для открытия окна
    private void openWindow(String path, Button button, String title) {
        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene((new Scene(root)));
        stage.getIcons().add(new Image("file:src/main/resources/picture/icon.png"));
        stage.setTitle(title);
        stage.show();
    }

}