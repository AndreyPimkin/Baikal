package penza.shandakov.baikal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import penza.shandakov.baikal.POJO.ForLogist;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Driver {

    @FXML private TableColumn<ForLogist, String> from;
    @FXML private TableColumn<ForLogist, String> number;
    @FXML private TableColumn<ForLogist, String> ob;
    @FXML private TableView<ForLogist> tableLogist;
    @FXML private TableColumn<ForLogist, String> to;
    @FXML private TableColumn<ForLogist, String> ves;
    DatabaseHandler dbHandler = new DatabaseHandler();
    private final ObservableList<ForLogist> cargoList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        ves.setCellValueFactory(new PropertyValueFactory<>("ves"));
        ob.setCellValueFactory(new PropertyValueFactory<>("ob"));
        from.setCellValueFactory(new PropertyValueFactory<>("to"));
        to.setCellValueFactory(new PropertyValueFactory<>("from"));
        tableLogist.setItems(cargoList);
    }

    // метод заполнения таблицы с  доставками
    private void initData() throws SQLException {
        dbHandler = new DatabaseHandler();
        ResultSet rs;
        rs = dbHandler.getDelivery();
        while (true) {
            assert rs != null;
            if (!rs.next()) break;
            cargoList.add(new ForLogist(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5)));
        }
    }

}
