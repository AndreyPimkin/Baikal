package penza.shandakov.baikal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.server.DatabaseHandler;
import java.io.IOException;
import java.sql.SQLException;
public class Person {

    @FXML private DatePicker birthday;
    @FXML private ComboBox<String> boxCity;
    @FXML private Button buttonLogin;
    @FXML private DatePicker datePas;
    @FXML private ImageView imageCon;
    @FXML private ImageView imageInfo;
    @FXML private TextField inputName;
    @FXML private TextField inputPat;
    @FXML private TextField inputSurname;
    @FXML private TextField numberPas;
    private String selectCity = "Пенза";
    private final String[] listCity = new String[]{"Пенза", "Москва", "Рязань", "Тамбов", "Саратов", "Лопатино"};
    DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML
    void initialize() {
        boxCity.getItems().addAll(this.listCity);
        boxCity.setOnAction(this::getCity);
        // кнопка дял изменения личных данных
        buttonLogin.setOnAction(actionEvent -> {
            ForClient forClient = new ForClient();
            forClient.setSurname(inputSurname.getText());
            forClient.setName(inputName.getText());
            forClient.setPat(inputPat.getText());
            forClient.setCity(selectCity);
            forClient.setNumberDoc(numberPas.getText());
            forClient.setDateDoc(String.valueOf(datePas.getValue()));
            forClient.setDay(String.valueOf(birthday.getValue()));
            try {
                dbHandler.updateClient(forClient);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        // кнопка для открытия оформления заказов
        imageCon.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/cargo.fxml", buttonLogin, "Оформление заявки");});
        // кнопка для открытия отслеживания
        imageInfo.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/info.fxml", buttonLogin, "Отслеживание");});
    }

    // метод который в переменную записывае выбранное значение из списка
    private void getCity(ActionEvent actionEvent) {
        selectCity = boxCity.getValue();}
    // метод открытия нового окна
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
