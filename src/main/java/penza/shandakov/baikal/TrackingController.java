package penza.shandakov.baikal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

public class TrackingController {

    @FXML
    private Button clearButton;

    @FXML
    private Label dateAndCityFromSent;

    @FXML
    private Label dateAndCityToSent;

    @FXML
    private ImageView errorImage;

    @FXML
    private Label errorInput;

    @FXML
    private Label fromSent;

    @FXML
    private TextField inputNumber;

    @FXML
    private Label options;

    @FXML
    private Label packing;

    @FXML
    private Label price;

    @FXML
    private Button backButton;
    @FXML
    private Button searchButton;

    @FXML
    private Label size;

    @FXML
    private Label status;

    @FXML
    private Label timeDelivery;

    @FXML
    private Label toSent;

    @FXML
    private Label weight;

    @FXML
    void mouseClick(MouseEvent event) {
        errorImage.setVisible(false);
    }

    DatabaseHandler dbHandler = new DatabaseHandler();
    ForCargo forCargo;
    ForClient forClient;

    @FXML
    void initialize() {
        fromSent.setVisible(false);
        dateAndCityFromSent.setVisible(false);
        toSent.setVisible(false);
        dateAndCityToSent.setVisible(false);

        LocalDate dateNow = LocalDate.now();
        errorImage.setVisible(false);
        errorInput.setVisible(false);
        clearButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            errorImage.setVisible(false);
            fromSent.setVisible(false);
            dateAndCityFromSent.setVisible(false);
            toSent.setVisible(false);
            dateAndCityToSent.setVisible(false);

            options.setText("");
            size.setText("");
            weight.setText("");
            packing.setText("");
            price.setText("");
            status.setText("");
            timeDelivery.setText("");
        });

        backButton.setOnAction(actionEvent -> {
            AuthorizationController.openWindow("/penza/shandakov/baikal/personalAccount.fxml", backButton, "Личный кабинет");
        });

        searchButton.setOnAction(actionEvent -> {
            errorInput.setVisible(false);
            errorImage.setVisible(false);
            if (inputNumber.getText().equals("")) {
                errorImage.setVisible(true);
            } else {
                forCargo = new ForCargo();
                forClient = new ForClient();
                forCargo.setNumber(inputNumber.getText());
                forClient.setId(String.valueOf(AuthorizationController.idClient));
                ResultSet resultCargo = dbHandler.getCargo(forCargo, forClient);
                try {
                    if (resultCargo.next()) {
                        options.setText(resultCargo.getString(1) + "l, " + resultCargo.getString(2) + "w, " + resultCargo.getString(3) + "h");
                        size.setText(resultCargo.getString(4) + "м^3");
                        weight.setText(resultCargo.getString(5) + "кг");
                        packing.setText(resultCargo.getString(6));
                        price.setText(resultCargo.getString(7) + "₽");
                        status.setText(resultCargo.getString(8));
                        timeDelivery.setText(resultCargo.getString(9));

                        if (!resultCargo.getString(11).equals("")) {
                            fromSent.setVisible(true);
                            dateAndCityFromSent.setText(resultCargo.getString(10) + ", " + resultCargo.getString(11));
                            dateAndCityFromSent.setVisible(true);
                        }
                        if (!resultCargo.getString(13).equals("")) {
                            toSent.setVisible(true);
                            dateAndCityToSent.setText(resultCargo.getString(12) + ", " + resultCargo.getString(13));
                            dateAndCityToSent.setVisible(true);
                        }
                    } else {
                        errorInput.setVisible(true);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }
}
