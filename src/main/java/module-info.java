module org.openjfx.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.logging;
	requires javafx.graphics;

    opens org.openjfx.hellofx to javafx.fxml;
    exports org.openjfx.hellofx;
}