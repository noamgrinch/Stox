module org.openjfx.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.logging;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.xml;
	requires org.json;
	requires java.desktop;

    opens org.openjfx.hellofx to javafx.fxml;
    exports org.openjfx.hellofx;
}