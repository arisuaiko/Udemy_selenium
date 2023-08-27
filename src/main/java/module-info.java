module com.example.udemy_selenium {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.udemy_selenium to javafx.fxml;
    exports com.example.udemy_selenium;
}