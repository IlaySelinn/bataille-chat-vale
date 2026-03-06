module school.coda.ilay_luisa.bataillejavale {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens school.coda.ilay_luisa.bataillejavale to javafx.fxml;
    exports school.coda.ilay_luisa.bataillejavale;
}