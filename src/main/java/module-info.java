module school.coda.ilay_luisa.bataillejavale {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens school.coda.ilay_luisa.bataillejavale to javafx.fxml;
    exports school.coda.ilay_luisa.bataillejavale;
    exports school.coda.ilay_luisa.bataillejavale.view;
    opens school.coda.ilay_luisa.bataillejavale.view to javafx.fxml;
    opens school.coda.ilay_luisa.bataillejavale.controllers to javafx.fxml;
}