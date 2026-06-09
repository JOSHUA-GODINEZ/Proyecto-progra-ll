
module Proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jakarta.persistence;
    requires java.sql;
    requires java.instrument;
    requires java.naming;       
    requires java.desktop; 

    exports cr.ac.una.proyecto;
    opens cr.ac.una.proyecto to javafx.fxml;
    opens cr.ac.una.proyecto.controller to javafx.fxml;
    opens cr.ac.una.proyecto.model;
}


