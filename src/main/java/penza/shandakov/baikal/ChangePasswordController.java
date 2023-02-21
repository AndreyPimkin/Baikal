package penza.shandakov.baikal;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

public class ChangePasswordController {

    @FXML
    private Button buttonChangePassword;

    @FXML
    private Button buttonOpenAutho;

    @FXML
    private ImageView errorImage;

    @FXML
    private Label errorInput;

    @FXML
    private TextField inputPhone;

    @FXML
    private PasswordField inputPasswordTwo;

    @FXML
    private PasswordField inputPasswordOne;

    DatabaseHandler dbHandler = new DatabaseHandler();
    private String phone;
    private boolean checkPhone = false;

    @FXML
    void initialize() {
        errorImage.setVisible(false);
        errorInput.setVisible(false);
        inputPasswordTwo.setVisible(false);
        inputPasswordOne.setVisible(false);

        // Открывает окно авторизации
        buttonOpenAutho.setOnAction(actionEvent -> {
            AuthorizationController.openWindow("/penza/shandakov/baikal/authorization.fxml", buttonOpenAutho, "Авторизация");
        });

        buttonChangePassword.setOnAction(actionEvent -> {
            if (checkPhone) {
                errorImage.setVisible(false);
                errorInput.setVisible(false);

                if (inputPasswordOne.getText().equals("")) {
                    errorImage.setVisible(true);
                    errorImage.setLayoutX(105);
                    errorImage.setLayoutY(72);
                } else if (inputPasswordTwo.getText().equals("")) {
                    errorImage.setVisible(true);
                    errorImage.setLayoutX(105);
                    errorImage.setLayoutY(137);
                } else {
                    if (inputPasswordOne.getText().equals(inputPasswordTwo.getText())) {
                        ForClient forClient = new ForClient();
                        forClient.setPassword(AuthorizationController.getMd5(inputPasswordOne.getText()));
                        forClient.setPhone(phone);
                        try {
                            dbHandler.changePassword(forClient);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        errorInput.setMinWidth(180);
                        errorInput.setText("Успешно");
                        errorInput.setVisible(true);
                        inputPasswordOne.setDisable(true);
                        inputPasswordTwo.setDisable(true);
                        buttonChangePassword.setDisable(true);
                    } else {
                        errorInput.setMinWidth(180);
                        errorInput.setText("Пароли не совпадают");
                        errorInput.setVisible(true);
                    }

                }
            }
            else{
                errorImage.setVisible(false);
                errorInput.setVisible(false);
                ResultSet resultNumber;

                if (inputPhone.getText().equals("")) {
                    errorImage.setVisible(true);
                    errorImage.setLayoutX(105);
                    errorImage.setLayoutY(72);
                } else {
                    phone = inputPhone.getText();
                    ForClient forClient = new ForClient();
                    forClient.setPhone(inputPhone.getText());
                    resultNumber = dbHandler.checkPhoneClient(forClient);
                    try {
                        if (resultNumber.next()) {
                            checkPhone = true;
                            inputPhone.setVisible(false);
                            inputPasswordTwo.setVisible(true);
                            inputPasswordOne.setVisible(true);
                        } else {
                            errorInput.setVisible(true);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void mouseClick(MouseEvent mouseEvent) {
        errorImage.setVisible(false);
    }
}
