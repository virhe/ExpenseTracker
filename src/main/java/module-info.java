module com.ot.vamk.oop.project.expensetracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.ot.vamk.oop.project.expensetracker to javafx.fxml;
    exports com.ot.vamk.oop.project.expensetracker;
}