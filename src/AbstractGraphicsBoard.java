import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class AbstractGraphicsBoard extends JPanel {
    protected SButton[][]  Gboard;
    protected int boardCounter;
    protected BitBoard bitBoard;
    protected  int board_size;
    public State state;
    public  AbstractGraphicsBoard(int boardSize){
        this.board_size = boardSize;
        this.Gboard = new SButton[boardSize][boardSize];
        this.boardCounter = 0;
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
        this.bitBoard = new BitBoard(boardSize);

    }
    public void markButton(int row, int col, State s)
    {

        if(s == State.S)
            bitBoard.placeS(row, col);
        else
            bitBoard.placeO(row, col);
        ImageIcon icon = new ImageIcon(s == State.S ? "src/images/s.png" : "src/images/o.png");
        Image img = icon.getImage();
        Gboard[row][col].setImg(img);
        boardCounter++;
    }
    protected boolean boardFull()
    {
        return boardCounter == board_size*board_size;
    }


    protected int CheckSos(short row, short colum, Color lineColor)
    {
        int sosCount = 0;
        if(bitBoard.checkCell(row, colum) == 2)
        {
            if (colum > 0 && colum < board_size - 1 && bitBoard.checkCell(row, colum-1) == 1 && bitBoard.checkCell(row, colum+1) == 1)
            {
                //TODO::Horizontal
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(Horizontal)");
            }
            if(row > 0 && row < board_size - 1 && bitBoard.checkCell(row-1, colum) == 1 &&bitBoard.checkCell(row+1, colum) == 1)
            {
                //TODO::Vertical
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(Vertical)");
            }
            if(colum > 0 && colum < board_size - 1 && row > 0 && row <board_size - 1 && bitBoard.checkCell(row-1, colum-1) == 1 && bitBoard.checkCell(row+1, colum+1) == 1)
            {
                //TODO::Diagonal(Top To Down)
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);

                sosCount++;
                System.out.println("SOS Found(Diagonal(Top To Down))");

            }
            if(colum > 0 && colum < board_size - 1 && row > 0 && row < board_size - 1 && bitBoard.checkCell(row+1, colum-1) == 1 && bitBoard.checkCell(row-1, colum+1) == 1)
            {
                //TODO::Diagonal(Down To Top)

                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row-1][colum+1].setLineArray(3, lineColor);
                sosCount++;
                System.out.println("SOS Found(Diagonal(Down To Top))");

            }
        }
        if(bitBoard.checkCell(row, colum) == 1)
        {
            if(colum < board_size - 2 && bitBoard.checkCell(row, colum+1) == 2 && bitBoard.checkCell(row, colum+2) == 1)
            {
                //TODO::horizontal (forward)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                Gboard[row][colum+2].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(horizontal (forward))");

            }
            if(colum > 1 && bitBoard.checkCell(row, colum-1) == 2 && bitBoard.checkCell(row, colum-2) == 1)
            {
                //TODO::horizontal (backwards)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum-2].setLineArray(0, lineColor);
                sosCount++;
                System.out.println("SOS Found(horizontal (backwards))");

            }
            if(row < board_size - 2 && bitBoard.checkCell(row+1, colum) == 2 && bitBoard.checkCell(row+2, colum) == 1)
            {
                //TODO::vertical (downward)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row+2][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(vertical (downward))");

            }
            if(row > 1 && bitBoard.checkCell(row-1, colum) == 2 && bitBoard.checkCell(row-2, colum) == 1)
            {
                //TODO::vertical (upwards)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                Gboard[row-2][colum].setLineArray(1, lineColor);
                sosCount++;
                System.out.println("SOS Found(vertical (upwards))");

            }
            if(row > 1 && colum > 1 && bitBoard.checkCell(row-1, colum-1) == 2 && bitBoard.checkCell(row-2, colum-2) == 1)
            {
                //TODO::Diagonal(left up(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);
                Gboard[row-2][colum-2].setLineArray(2, lineColor);
                sosCount++;
            }
            if(row < board_size - 2 && colum <board_size -2 && bitBoard.checkCell(row+1, colum+1) == 2 && bitBoard.checkCell(row+2, colum+2) == 1)
            {
                //TODO::Diagonal(right down(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row+2][colum+2].setLineArray(2, lineColor);
                sosCount++;
            }
            if(colum > 1 && row < board_size - 2 && bitBoard.checkCell(row+1, colum-1) == 2 && bitBoard.checkCell(row+2, colum-2) == 1)
            {
                //TODO::Diagonal(left down(down to top))
                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row+2][colum-2].setLineArray(3, lineColor);
                sosCount++;
            }
            if(row > 1 && colum < board_size - 2 && bitBoard.checkCell(row-1, colum+1) == 2 &&bitBoard.checkCell(row-2, colum+2) == 1)
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



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        int w = getWidth() / this.board_size;
        int h = getHeight() / this .board_size;

       // paintSquares(g, w, h);
        //paintSO(g);
    }


    public abstract void undoMove();
    public abstract  void redoMove();
    protected abstract void endGame();

}
