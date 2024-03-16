import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class AbstractGraphicsBoard extends JPanel {
    protected short[][] logicalBoard;
    protected SButton[][]  Gboard;
    protected  int board_size;
    public State state;
    public  AbstractGraphicsBoard(int boardSize){
        this.board_size = boardSize;
        this.logicalBoard = new short[boardSize][boardSize];
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
    protected  void MarkButton(int row, int colum, State letter)
    {
        if (letter == State.S) {
            logicalBoard[row][colum] = (short) 1;
            ImageIcon icon = new ImageIcon("src/images/s.png");
            Image img = icon.getImage();
            Gboard[row][colum].setImg(img);

        } else if (letter == State.O) {
            logicalBoard[row][colum] = (short) 2;
            ImageIcon icon = new ImageIcon("src/images/o.png");
            Image img = icon.getImage();
            Gboard[row][colum].setImg(img);

        }
    }

    protected int CheckSos(short row, short colum, Color lineColor)
    {
        int sosCount = 0;
        if(logicalBoard[row][colum] == 2)
        {
            if (colum > 0 && colum < logicalBoard[row].length - 1 && logicalBoard[row][colum-1] == 1 && logicalBoard[row][colum+1] == 1)
            {
                //TODO::Horizontal
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(Horizontal)");
            }
            if(row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum] == 1 && logicalBoard[row+1][colum] == 1)
            {
                //TODO::Vertical
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(Vertical)");
            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum-1] == 1 && logicalBoard[row+1][colum+1] == 1)
            {
                //TODO::Diagonal(Top To Down)
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);

                sosCount++;
                System.out.println("SOS Found(Diagonal(Top To Down))");

            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row+1][colum-1] == 1 && logicalBoard[row-1][colum+1] == 1)
            {
                //TODO::Diagonal(Down To Top)

                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row-1][colum+1].setLineArray(3, lineColor);
                sosCount++;
                System.out.println("SOS Found(Diagonal(Down To Top))");

            }
        }
        if(logicalBoard[row][colum] == 1)
        {
            if(colum < logicalBoard[row].length - 2 && logicalBoard[row][colum+1] == 2 && logicalBoard[row][colum+2] == 1)
            {
                //TODO::horizontal (forward)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                Gboard[row][colum+2].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(horizontal (forward))");

            }
            if(colum > 1 && logicalBoard[row][colum-1] == 2 && logicalBoard[row][colum-2] == 1)
            {
                //TODO::horizontal (backwards)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum-2].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(horizontal (backwards))");

            }
            if(row < logicalBoard.length - 2 && logicalBoard[row+1][colum] == 2 && logicalBoard[row+2][colum] == 1)
            {
                //TODO::vertical (downward)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row+2][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(vertical (downward))");

            }
            if(row > 1 && logicalBoard[row-1][colum] == 2 && logicalBoard[row-2][colum] == 1)
            {
                //TODO::vertical (upwards)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                Gboard[row-2][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(vertical (upwards))");

            }
            if(row > 1 && colum > 1 && logicalBoard[row-1][colum-1] == 2 && logicalBoard[row-2][colum-2] == 1)
            {
                //TODO::Diagonal(left up(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);
                Gboard[row-2][colum-2].setLineArray(2, lineColor);
                sosCount++;
            }
            if(row < logicalBoard.length - 2 && colum < logicalBoard[row].length -2 && logicalBoard[row+1][colum+1] == 2 && logicalBoard[row+2][colum+2] == 1)
            {
                //TODO::Diagonal(right down(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row+2][colum+2].setLineArray(2, lineColor);
                sosCount++;
            }
            if(colum > 1 && row < logicalBoard.length-2 && logicalBoard[row+1][colum-1] == 2 && logicalBoard[row+2][colum-2] == 1)
            {
                //TODO::Diagonal(left down(down to top))
                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row+2][colum-2].setLineArray(3, lineColor);
                sosCount++;
            }
            if(row > 1 && colum < logicalBoard[row] .length - 2 && logicalBoard[row-1][colum+1] == 2 && logicalBoard[row-2][colum+2] == 1)
            {
                //TODO::Diagonal(right up(down to top))
                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row-1][colum+1].setLineArray(3, lineColor);
                Gboard[row-2][colum+2].setLineArray(3, lineColor);
                sosCount++;
            }

        }
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                Gboard[i][j].repaint();
            }
        }
        return sosCount;
    }
    public boolean EndGame()
    {
        for (int i = 0; i < logicalBoard.length; i++) {
            for (int j = 0; j < logicalBoard[i].length; j++) {
                if(logicalBoard[i][j] == 0)
                    return false;
            }
        }
        return true;
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
