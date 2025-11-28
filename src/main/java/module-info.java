module com.garden.gardensimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.garden.gardensimulation to javafx.fxml;
    exports com.garden.gardensimulation;
}