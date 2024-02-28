module com.github.chizzaru.zebrakit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jt400;
    requires org.apache.commons.configuration2;


    opens com.github.chizzaru.zebrakit to javafx.fxml;
    exports com.github.chizzaru.zebrakit;
    exports com.github.chizzaru.zebrakit.controller;
}