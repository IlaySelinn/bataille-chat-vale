package school.coda.ilay_luisa.bataillejavale.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardViev extends Canvas
{
    private final int cellSize = 30;
    private final int cellNumber = 10;

    public BoardViev()
    {
        super(311, 311);
        drawGrid();
    }

    private void drawGrid()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        /// Couleur de lu océan
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        /// Couleur de grilles
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);

        for (int i = 0; i <= cellNumber; i++)
        {
            double pos = (cellSize * i) + i;
            /// lignes verticales
            gc.strokeLine(pos, 0, pos, getHeight());
            /// lignes horizontal
            gc.strokeLine(0, pos, getWidth(), pos);
        }
    }

    /// Pour marquer les chts et les hits
    public void markHit(int row, int col, Color color) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(color);

        double x = (col * cellSize) + col + 1;
        double y = (row * cellSize) + row + 1;

        gc.fillRect(x, y, cellSize, cellSize);
    }
}




