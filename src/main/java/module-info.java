module penza.shandakov.baikal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens penza.shandakov.baikal to javafx.fxml;
    exports penza.shandakov.baikal;

    opens penza.shandakov.baikal.POJO to javafx.fxml;
    exports penza.shandakov.baikal.POJO;
}