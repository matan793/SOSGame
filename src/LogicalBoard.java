import java.awt.*;

public class LogicalBoard {
    public short[][] logicalBoard;

    public LogicalBoard(int boardSize) {

        this.logicalBoard = new short[boardSize][boardSize];
        this.logicalBoard[4][1] = 1;
    }
    public int CheckSos(int row, int colum)
    {
        int sosCount = 0;
        if(logicalBoard[row][colum] == 2)
        {
            if (colum > 0 && colum < logicalBoard[row].length - 1 && logicalBoard[row][colum-1] == 1 && logicalBoard[row][colum+1] == 1)
            {
                //TODO::Horizontal
               
                sosCount++;
                System.out.println("SOS Found(Horizontal)");
            }
            if(row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum] == 1 && logicalBoard[row+1][colum] == 1)
            {
                //TODO::Vertical
           
                sosCount++;
                System.out.println("SOS Found(Vertical)");
            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum-1] == 1 && logicalBoard[row+1][colum+1] == 1)
            {
                //TODO::Diagonal(Top To Down)
               
                sosCount++;
                System.out.println("SOS Found(Diagonal(Top To Down))");

            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row+1][colum-1] == 1 && logicalBoard[row-1][colum+1] == 1)
            {
                //TODO::Diagonal(Down To Top)
               
                sosCount++;
                System.out.println("SOS Found(Diagonal(Down To Top))");

            }
        }
        if(logicalBoard[row][colum] == 1)
        {
            if(colum < logicalBoard[row].length - 2 && logicalBoard[row][colum+1] == 2 && logicalBoard[row][colum+2] == 1)
            {
                //TODO::horizontal (forward)
             
                sosCount++;
                System.out.println("SOS Found(horizontal (forward))");

            }
            if(colum > 1 && logicalBoard[row][colum-1] == 2 && logicalBoard[row][colum-2] == 1)
            {
                //TODO::horizontal (backwards)
              
                sosCount++;
                System.out.println("SOS Found(horizontal (backwards))");

            }
            if(row < logicalBoard.length - 2 && logicalBoard[row+1][colum] == 2 && logicalBoard[row+2][colum] == 1)
            {
                //TODO::vertical (downward)
              
                sosCount++;
                System.out.println("SOS Found(vertical (downward))");

            }
            if(row > 1 && logicalBoard[row-1][colum] == 2 && logicalBoard[row-2][colum] == 1)
            {
                //TODO::vertical (upwards)
              
                sosCount++;
                System.out.println("SOS Found(vertical (upwards))");

            }
            if(row > 1 && colum > 1 && logicalBoard[row-1][colum-1] == 2 && logicalBoard[row-2][colum-2] == 1)
            {
                //TODO::Diagonal(left up(top to down))
               
                sosCount++;
            }
            if(row < logicalBoard.length - 2 && colum < logicalBoard[row].length -2 && logicalBoard[row+1][colum+1] == 2 && logicalBoard[row+2][colum+2] == 1)
            {
                //TODO::Diagonal(right down(top to down))
             
                sosCount++;
            }
            if(colum > 1 && row < logicalBoard.length-2 && logicalBoard[row+1][colum-1] == 2 && logicalBoard[row+2][colum-2] == 1)
            {
                //TODO::Diagonal(left down(down to top))
                
                sosCount++;
            }
            if(row > 1 && colum < logicalBoard[row] .length - 2 && logicalBoard[row-1][colum+1] == 2 && logicalBoard[row-2][colum+2] == 1)
            {
                //TODO::Diagonal(right up(down to top))
                
                sosCount++;
            }
        }

        return sosCount;
    }
    public boolean EndGame()
    {
        return true;
    }
    public Point MakeMove(Point point)
    {
        return null;
    }

}
