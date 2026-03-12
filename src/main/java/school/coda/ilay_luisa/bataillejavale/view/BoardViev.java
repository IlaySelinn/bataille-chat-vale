package school.coda.ilay_luisa.bataillejavale.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardViev extends Canvas
{
    private final int cellSize = 50;
    private final int cellNumber = 10; //10*10

    public int getRow(double y) {
        return (int) (y / (cellSize + 1));
    }

    public int getCol(double x) {
        return (int) (x / (cellSize + 1));
    }

    public BoardViev()
    {
        super(510, 510);
        drawGrid();
    }

    private void drawGrid()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        /// Couleur de lu océan
        gc.setFill(Color.DODGERBLUE);
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
    public void markHit(int row, int col, Color color)
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(color);

        double x = (col * cellSize) + col + 1;
        double y = (row * cellSize) + row + 1;

        gc.fillRect(x, y, cellSize, cellSize);


    }
}




