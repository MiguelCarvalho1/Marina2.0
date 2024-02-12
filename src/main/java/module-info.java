module com.miguel.marina2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.miguel.marina2 to javafx.fxml;
    exports com.miguel.marina2;
}