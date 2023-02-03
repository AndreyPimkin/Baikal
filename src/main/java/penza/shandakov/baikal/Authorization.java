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
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization {

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonOpenReg;
    @FXML
    private TextField inputNumber;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label text;
    DatabaseHandler dbHandler = new DatabaseHandler();
    public static int id;
    public static int idPerson;

    @FXML
    void initialize() {
        // слушатель на кнопку регистрации. Откроет окно регистрации
        buttonOpenReg.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/reg.fxml", buttonOpenReg, "Регистрация");
        });
        // слушатель на кнопку авторизации
        buttonLogin.setOnAction(actionEvent -> {
            ResultSet resultAuto;
            ResultSet resultPerson;
            if (inputNumber.getText().equals("") || inputPassword.getText().equals("")) { // если данные пустые
                text.setText("Неверные данные");
            } else {
                ForClient forClient = new ForClient();
                forClient.setPhone(inputNumber.getText());
                forClient.setPassword(inputPassword.getText());
                resultAuto = dbHandler.autoUser(forClient);
                try {
                    if (resultAuto.next()) { // если совпадения с клиентом есть из Бд, откроет окно клиента
                        id = resultAuto.getInt(1); // запоминает ай ди клиента
                        openWindow("/penza/shandakov/baikal/client.fxml", buttonOpenReg, "Калькулятор");
                    } else { // иначе будет проверять в таблице персон
                        forClient.setPhone(inputNumber.getText());
                        forClient.setPassword(inputPassword.getText());
                        resultPerson = dbHandler.autoPerson(forClient);
                        if (resultPerson.next()) { // если и в персоне не найдет сработает 76 строка
                            idPerson = resultPerson.getInt(1);
                            if (resultPerson.getString("role").equals("Логист")) {
                                openWindow("/penza/shandakov/baikal/logist.fxml", buttonOpenReg, "Кабинет логиста");
                            } else if (resultPerson.getString("role").equals("Водитель")) {
                                openWindow("/penza/shandakov/baikal/driver.fxml", buttonOpenReg, "Кабинет водителя");
                            } else if (resultPerson.getString("role").equals("Админ")) {
                                openWindow("/penza/shandakov/baikal/admin.fxml", buttonOpenReg, "Кабинет администратора");
                            }
                        } else {
                            text.setText("Пользователи не найдены");
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // метод для открытия нового окна
    public void openWindow(String path, Button button, String title) {
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
        stage.setResizable(false);
        stage.setScene((new Scene(root)));
        stage.getIcons().add(new Image("file:src/main/resources/picture/icon.png"));
        stage.setTitle(title);
        stage.show();
    }
}
