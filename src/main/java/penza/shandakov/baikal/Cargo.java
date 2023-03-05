package penza.shandakov.baikal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.server.DatabaseHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
public class Cargo {
    @FXML private ComboBox<String> box;
    @FXML private ComboBox<String> boxFrom;
    @FXML private ComboBox<String> boxTo;
    @FXML private Button buttonGo;
    @FXML private TextField dlina;
    @FXML private ImageView imageInfo;
    @FXML private Button itog;
    @FXML private TextField ob;
    @FXML private TextField opis;
    @FXML private Button person;
    @FXML private Label price;
    @FXML private TextField shir;
    @FXML private TextField ves;
    @FXML private TextField vis;

    private String selectCityFrom = "Пенза";
    private String selectCityTo = "Москва";
    private String selectBox = "Жесткая упаковка";
    private String number;
    private final String[] listCity = new String[]{"Пенза", "Москва", "Рязань", "Тамбов", "Саратов", "Лопатино"};
    private final String[] listPask = new String[]{"Жесткая упаковка", "Паллетирование", "Мешки", "Пленка"};
    DatabaseHandler dbHandler = new DatabaseHandler();
    Random random = new Random();
    private float p = 0;
    @FXML
    void initialize() {
        ob.setDisable(true);
        ob.setText(String.format("%.2f", MainController.size));
        ves.setText(String.format("%.2f", MainController.weight));
        price.setText(String.format("%.2f", MainController.total));

        boxFrom.getItems().addAll(this.listCity);
        boxTo.getItems().addAll(this.listCity);
        box.getItems().addAll(this.listPask);
        boxFrom.setOnAction(this::getCityFrom);
        boxTo .setOnAction(this::getCityTo);
        box .setOnAction(this::getBox);

        person.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/personalAccount.fxml", person, "Личный кабинет");
        });

        imageInfo.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/tracking.fxml", buttonGo, "Отслеживание");
        });

        // метод подсчета

        itog.setOnAction(actionEvent -> {
            MainController.weight = Float.parseFloat(ves.getText());
            MainController.size = Float.parseFloat(dlina.getText()) * Float.parseFloat(vis.getText()) *  Float.parseFloat(shir.getText());
            p = (MainController.weight / MainController.size);
            if(selectCityFrom.equals(selectCityTo)){
                if(p > 250){
                    MainController.total  = MainController.weight * 2;
                }
                else {
                    MainController.total = (int) (MainController.weight * 1.5);
                }
            }
            else{
                if(p > 250){
                    MainController.total  = MainController.weight * 3;
                }
                else {
                    MainController.total = (int) (MainController.weight * 2);
                }
            }
            price.setText("от "+ String.format("%.2f", MainController.total) + " ₽");

        });

        // метод добавления груза

  /*      buttonGo.setOnAction(actionEvent -> {
            number = generateNumber();
            ForCargo forCargo = new ForCargo();
            forCargo.setNumber(number);
            forCargo.setId_client(String.valueOf(AuthorizationController.id));
            forCargo.setDesc(opis.getText());
            forCargo.setLen(dlina.getText());
            forCargo.setWid(shir.getText());
            forCargo.setHei(vis.getText());
            forCargo.setSize(String.valueOf(MainController.size));
            forCargo.setWei(String.valueOf(MainController.weight));
            forCargo.setPrice(String.valueOf(MainController.total));
            forCargo.setPack(selectBox);
            forCargo.setCity_from(selectCityFrom);
            forCargo.setCity_to(selectCityTo);
            forCargo.setNumberTwo(number);
            forCargo.setStatus("Создан");
            try {
                dbHandler.addCargo(forCargo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
*/




    }

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
    private void getCityFrom(ActionEvent actionEvent) {
        selectCityFrom = boxFrom.getValue();
    }
    private void getCityTo(ActionEvent actionEvent) {

        selectCityTo = boxTo.getValue();
    }

    private void getBox(ActionEvent actionEvent) {

        selectBox = box.getValue();
    }

    private String generateNumber(){
        String codeRandom = "";
        int rrr;
        for (int i = 0; i < 10; i++) {
            rrr = random.nextInt(1, 3);
            if (rrr == 1) {codeRandom += (char) random.nextInt(65, 90);}
            else if (rrr == 2) {codeRandom += (char) random.nextInt(48, 57);
            } else {codeRandom += (char) random.nextInt(97, 122);}}
        return ("19IT18."+codeRandom+".RU");}


}
