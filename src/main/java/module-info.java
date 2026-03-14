module school.coda.ilay_luisa.bataillejavale {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    exports school.coda.ilay_luisa.bataillejavale;
    exports school.coda.ilay_luisa.bataillejavale.view;
    exports school.coda.ilay_luisa.bataillejavale.controllers;

    opens school.coda.ilay_luisa.bataillejavale to javafx.fxml;
    opens school.coda.ilay_luisa.bataillejavale.view to javafx.fxml;
    opens school.coda.ilay_luisa.bataillejavale.controllers to javafx.fxml;
}