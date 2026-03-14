package school.coda.ilay_luisa.bataillejavale.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;

public class BoardView extends Canvas
{
    /// Zone pour les lettres et chiffres
    private final int offset = 30;
    private final int cellSize = 45;
    private final int cellNumber = 10; // 10*10

    public int getRow(double y)
    {
        return (int) ((y - offset) / (cellSize + 1));
    }

    public int getCol(double x)
    {
        return (int) ((x - offset) / (cellSize + 1));
    }


    public BoardView()
    {
        super(510, 510);
        drawGrid();
    }

    private void drawGrid()
    {
        GraphicsContext gc = this.getGraphicsContext2D();

        /// Fond blanc pour les étiquettes (A-J, 1-10)
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        /// Dessiner les lettres (A-J)
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 16));
        String[] lettrers = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 0; i < cellNumber; i++)
        {
            double xPos = offset + (i * cellSize) + i + (cellSize / 3.0);
            gc.fillText(lettrers[i], xPos, 20);
        }

        // Dessiner les chiffres (1-10)
        for (int i = 0; i < cellNumber; i++)
        {
            double yPos = offset + (i * cellSize) + i + (cellSize / 1.5);
            gc.fillText(String.valueOf(i + 1), 5, yPos);
        }

        /// Couleur de l'océan (Commence après l'offset)
        gc.setFill(Color.DODGERBLUE);
        gc.fillRect(offset, offset, getWidth() - offset, getHeight() - offset);

        /// Couleur des grilles
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);

        for (int i = 0; i <= cellNumber; i++)
        {
            double pos = (cellSize * i) + i + offset;
            /// lignes verticales
            gc.strokeLine(pos, offset, pos, getHeight());
            /// lignes horizontales
            gc.strokeLine(offset, pos, getWidth(), pos);
        }
    }

    /// Pour marquer les chats et les hits
    public void markHit(int row, int col, Color color)
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(color);

        // On ajoute l'offset pour dessiner dans la grille
        double x = offset + (col * cellSize) + col + 1;
        double y = offset + (row * cellSize) + row + 1;

        gc.fillRect(x, y, cellSize, cellSize);
    }
    /// Pour affichage des chats
    public void drawCatImage(int row, int col, Image catImage)
    {
        GraphicsContext gc = this.getGraphicsContext2D();


        double x = offset + (col * cellSize) + col + 1;
        double y = offset + (row * cellSize) + row + 1;


        if (catImage != null && !catImage.isError())
        {
            gc.setFill(Color.DODGERBLUE);
            gc.fillRect(x, y, cellSize, cellSize);

            gc.drawImage(catImage, x, y, cellSize, cellSize);
            System.out.println("✅ Le chat a été dessiné à: " + row + "," + col);
        }
        else
        {
            System.out.println("❌ Erreur: Photo de chat introuvable ou corrompue!");
            gc.setFill(Color.RED);
            gc.fillRect(x, y, cellSize, cellSize);
        }
    }
}