module com.miguel.marina2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires spring.data.commons;
    requires spring.data.mongodb;
    requires spring.context;
    requires spring.beans;

    opens com.miguel.marina2 to javafx.fxml;
    exports com.miguel.marina2;
}