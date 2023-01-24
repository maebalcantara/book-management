module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
	requires javafx.base;

    opens com.example.librarymanagement to javafx.fxml;
    exports com.example.librarymanagement;
}