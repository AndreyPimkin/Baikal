package penza.shandakov.baikal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonReg;

    @FXML
    private ImageView errorImage;

    @FXML
    private Label errorInput;

    @FXML
    private TextField inputNumber;

    @FXML
    private PasswordField inputPasswordOne;

    @FXML
    private PasswordField inputPasswordTwo;

    @FXML
    private Label text;
    DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    void initialize() {
        errorImage.setVisible(false);
        errorInput.setVisible(false);
        buttonBack.setOnAction(actionEvent -> {
            AuthorizationController.openWindow("/penza/shandakov/baikal/authorization.fxml",buttonBack, "Авторизация");
        });
        buttonReg.setOnAction(actionEvent -> {
            if (inputNumber.getText().matches("^8\\d{10}$")) {
                errorImage.setVisible(false);
                errorInput.setVisible(false);
                ForClient forClient = new ForClient();
                forClient.setPhone(inputNumber.getText());
                ResultSet resultAuto = dbHandler.checkPhoneClient(forClient);
                try {
                    if(resultAuto.next()){
                        errorInput.setText("Номер уже используется");
                        errorInput.setVisible(true);
                    }
                    else if (inputNumber.getText().equals("")) {
                        errorImage.setVisible(true);
                        errorImage.setLayoutX(170);
                        errorImage.setLayoutY(155);
                    } else if (inputPasswordOne.getText().equals("")) {
                        errorImage.setVisible(true);
                        errorImage.setLayoutX(170);
                        errorImage.setLayoutY(215);
                    } else if (inputPasswordTwo.getText().equals("")) {
                        errorImage.setVisible(true);
                        errorImage.setLayoutX(170);
                        errorImage.setLayoutY(275);
                    } else {
                        if (inputPasswordOne.getText().equals(inputPasswordTwo.getText())) {
                            forClient = new ForClient();
                            forClient.setPhone(inputNumber.getText());
                            forClient.setPassword(AuthorizationController.getMd5(inputPasswordOne.getText()));
                            try {
                                dbHandler.signUpUser(forClient);
                            } catch (SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            AuthorizationController.openWindow("/penza/shandakov/baikal/authorization.fxml",  buttonReg, "Авторизация");
                        } else {
                            errorInput.setMinWidth(180);
                            errorInput.setText("Пароли не совпадают");
                            errorInput.setVisible(true);
                        }

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                errorInput.setMinWidth(180);
                errorInput.setText("Неверный номер телефона");
                errorInput.setVisible(true);
            }
        });
    }

    public void mouseClick(MouseEvent mouseEvent) {
        errorImage.setVisible(false);
    }

}
