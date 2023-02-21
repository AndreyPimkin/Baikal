package penza.shandakov.baikal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.io.IOException;
import java.sql.SQLException;

public class Reg {

    @FXML private Button buttonReg;
    @FXML
    private TextField inputNumber;
    @FXML private PasswordField inputPasswordAndCode;
    @FXML private PasswordField inputPasswordTwo;
    @FXML private Label text;
    DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML
    void initialize() {
        buttonReg.setOnAction(actionEvent -> {
                ForClient forClient = new ForClient();
                // также проверка данных на пустоту
                if (inputNumber.getText().equals("") || inputPasswordAndCode.getText().equals("") || inputPasswordTwo.getText().equals("")) {
                    text.setText("Неверные данные");
                } else {
                    if (inputPasswordAndCode.getText().equals(inputPasswordTwo.getText())) { // проверяет совпадение  паролей
                        forClient.setPhone(inputNumber.getText());
                        forClient.setPassword(inputPasswordTwo.getText());
                        try {
                            dbHandler.signUpUser(forClient);
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        openWindow("/penza/shandakov/baikal/authorization.fxml", buttonReg, "Авторизация");


                    } else {
                        text.setText("Пароли не совпадают");
                    }
                }});
    }

    // открывает новое окно
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
        stage.show();}
}
