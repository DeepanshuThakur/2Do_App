module ToDoApp {

    requires javafx.controls;
    requires javafx.base ;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.jfoenix;

    requires java.sql ;
    requires mysql.connector.java ;

    opens sample ;
    opens sample.controller ;
    opens sample.view ;
}