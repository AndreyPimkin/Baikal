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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationController {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonOpenReg;

    @FXML
    private Button buttonChangePassword;

    @FXML
    private ImageView errorImage;

    @FXML
    private Label errorInput;

    @FXML
    private TextField inputNumber;

    @FXML
    private PasswordField inputPassword;


    DatabaseHandler dbHandler = new DatabaseHandler();
    public static int id;
    public static int idPerson;

    public static boolean checkAuthorization = false;


    @FXML
    void initialize() {
        errorImage.setVisible(false);
        errorInput.setVisible(false);

        // слушатель на кнопку регистрации. Откроет окно регистрации
        buttonOpenReg.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/registration.fxml", buttonOpenReg, "Регистрация");
        });

        // Откроет окно смены пароля
        buttonChangePassword.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/changePassword.fxml", buttonOpenReg, "Смена пароля");
        });

        buttonBack.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/main.fxml", buttonBack, "Главная страница");
        });

        // слушатель на кнопку авторизации
        buttonLogin.setOnAction(actionEvent -> {
            errorImage.setVisible(false);
            errorInput.setVisible(false);
            ResultSet resultAuto;
            ResultSet resultPerson;
            if (inputNumber.getText().equals("") ) {
                errorImage.setVisible(true);
                errorImage.setLayoutX(171);
                errorImage.setLayoutY(156);
            }
            else if(inputPassword.getText().equals("")){
                errorImage.setVisible(true);
                errorImage.setLayoutX(171);
                errorImage.setLayoutY(210);
            }

            else {

                ForClient forClient = new ForClient();
                forClient.setPhone(inputNumber.getText());
                forClient.setPassword(getMd5(inputPassword.getText()));
                resultAuto = dbHandler.autoUser(forClient);

                try {
                    if (resultAuto.next()) { // если совпадения с клиентом есть из БД, откроет окно клиента
                        id = resultAuto.getInt(1); // запоминает ай ди клиента
                        checkAuthorization = true;
                        openWindow("/penza/shandakov/baikal/personalAccount.fxml", buttonOpenReg, "Калькулятор");
                    }

                    else { // иначе будет проверять в таблице персон
                        forClient.setPhone(inputNumber.getText());
                        forClient.setPassword(getMd5(inputPassword.getText()));
                        resultPerson = dbHandler.autoPerson(forClient);

                        if (resultPerson.next()) { // если и в персоне не найдет сработает 76 строка
                            idPerson = resultPerson.getInt(1);
                            if (resultPerson.getString("role").equals("Логист")) {
                                openWindow("/penza/shandakov/baikal/logist.fxml", buttonOpenReg, "Кабинет логиста");}

                            else if (resultPerson.getString("role").equals("Водитель")) {
                                openWindow("/penza/shandakov/baikal/driver.fxml", buttonOpenReg, "Кабинет водителя");}

                            else if (resultPerson.getString("role").equals("Админ")) {
                                openWindow("/penza/shandakov/baikal/admin.fxml", buttonOpenReg, "Кабинет администратора");}
                        }
                        else {
                            errorInput.setVisible(true);}
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // метод для открытия нового окна
    public static void openWindow(String path, Button button, String title) {
        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AuthorizationController.class.getResource(path));
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

    // Метод хеширования пароля
    public static String getMd5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public void mouseClick(MouseEvent mouseEvent) {
        errorImage.setVisible(false);
    }
}
