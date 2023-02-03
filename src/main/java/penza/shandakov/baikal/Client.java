package penza.shandakov.baikal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
public class Client {
    @FXML private ComboBox<String> boxFrom;
    @FXML private ComboBox<String> boxTo;
    @FXML private Button buttonGo;
    @FXML private ImageView imageCon;
    @FXML private ImageView imageInfo;
    @FXML private Button person;
    @FXML private Label price;
    @FXML private Slider sliceOb;
    @FXML private Slider sliceVes;
    @FXML private TextField textOb;
    @FXML private TextField textVes;
    private final String[] listCity = new String[]{"Пенза", "Москва", "Рязань", "Тамбов", "Саратов", "Лопатино"};
    private String selectCityFrom = "Пенза";
    private String selectCityTo = "Москва";
    public static float ves = 1;
    public static float ob = 0.01f;
    public static float total = 0;
    private float p = 0;
    @FXML
    void initialize() {
        boxFrom.getItems().addAll(this.listCity);
        boxTo.getItems().addAll(this.listCity);
        boxFrom.setOnAction(this::getCityFrom);
        boxTo .setOnAction(this::getCityTo);
        boxFrom.setValue("Пенза");
        boxTo.setValue("Москва");
        // метод для шкалы
        sliceVes.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                ves = (float) sliceVes.getValue();
                textVes.setText(String.format("%.2f",ves) + " кг");
                p = (ves /ob);
                if(selectCityFrom.equals(selectCityTo)){
                    if(p > 250){
                        total  = ves * 2;
                    }
                    else {
                        total = (int) (ves * 1.5);
                    }}
                else{
                    if(p > 250){
                        total  = ves * 3;
                    }
                    else {
                        total = (ves * 2);
                    }}
                price.setText("от "+ String.format("%.2f",total) + " ₽");
            }});
        // метод для шкалы
        sliceOb.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                ob = (float) sliceOb.getValue();
                textOb.setText(String.format("%.2f",ob) + " м^3");
                if(selectCityFrom.equals(selectCityTo)){
                    if(p > 250){
                        total  = ves * 2;
                    }
                    else {
                        total = (int) (ves * 1.5);
                    }}
                else{
                    if(p > 250){
                        total  = ves * 3;
                    }
                    else {
                        total = (ves * 2);
                    }}
                price.setText("от "+ String.format("%.2f",total) + " ₽");}});
        // кнопки открытия новых окон
        buttonGo.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/cargo.fxml", buttonGo, "Оформление заявки");
        });
        imageCon.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/cargo.fxml", buttonGo, "Оформление заявки");
        });

        imageInfo.setOnMouseClicked(mouseEvent -> {
            openWindow("/penza/shandakov/baikal/info.fxml", buttonGo, "Отслеживание");
        });

        person.setOnAction(actionEvent -> {
            openWindow("/penza/shandakov/baikal/person.fxml", person, "Личный кабинет");
        });}

    private void getCityFrom(ActionEvent actionEvent) {
        selectCityFrom = boxFrom.getValue();
    }
    private void getCityTo(ActionEvent actionEvent) {selectCityTo = boxTo.getValue();}
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
        stage.show();}

}