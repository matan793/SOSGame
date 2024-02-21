import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class AbstractGraphicsBoard extends JPanel {
    public LogicalBoard Lboard;
    public SButton[][]  Gboard;
    private  int board_size;
    public State state;
    public  AbstractGraphicsBoard(int boardSize){
        this.board_size = boardSize;
        this.Lboard = new LogicalBoard(boardSize);
        this.Gboard = new SButton[boardSize][boardSize];
        setLayout(new GridLayout(boardSize, boardSize));
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                ImageIcon icon = new ImageIcon("src/images/background.png");
                Image img = icon.getImage();
                Gboard[i][j] = new SButton(img, i, j);
                this.state = State.S;
                add(Gboard[i][j]);
            }
        }

    }

    public Point FindSquare(Point point) {
        int i = point.x * board_size;
        int j = point.y  * board_size;
        System.out.println("i: " + i + "j: " + j + "point: " + point);
        return new Point(i, j);
    }
    public void paintSO(Graphics g)
    {

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        int w = getWidth() / this.board_size;
        int h = getHeight() / this .board_size;

       // paintSquares(g, w, h);
        //paintSO(g);
    }

    public void paintSquares(Graphics g, int w, int h) {

        boolean colorChanger = true;
        for (int i = 0; i < this.board_size; i++) {
            colorChanger = !colorChanger;
            for (int j = 0; j < this.board_size; j++) {
                if (colorChanger)
                    g.setColor(Color.decode("#607391"));
                else
                    g.setColor(Color.BLACK);
                g.fillRect(w * j, h * i, w, h);
                colorChanger = !colorChanger;
            }
        }
    }
    public abstract void undoMove();
    public abstract  void redoMove();

}
