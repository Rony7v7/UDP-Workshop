module udp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens udp.workshop.model to javafx.graphics;
    
    exports udp.workshop.model;
}