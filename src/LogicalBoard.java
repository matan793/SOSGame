import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LogicalBoard {
    public short[][] logicalBoard;
    public List<SosSequence> sosSequenceList;
    public LogicalBoard(int boardSize) {
        sosSequenceList = new ArrayList<>();

        this.logicalBoard = new short[boardSize][boardSize];

    }
    public int CheckSos(short row, short colum)
    {
        int sosCount = 0;
        if(logicalBoard[row][colum] == 2)
        {
            if (colum > 0 && colum < logicalBoard[row].length - 1 && logicalBoard[row][colum-1] == 1 && logicalBoard[row][colum+1] == 1)
            {
                //TODO::Horizontal
                SosSequence s = new SosSequence(SosSequence.Direction.Horizontal);
                s.indecies = new short[][]{{row, colum}, {row, (short) (colum - 1)}, {row, (short) (colum + 1)}};
                sosSequenceList.add(s);
                sosCount++;
                System.out.println("SOS Found(Horizontal)");
            }
            if(row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum] == 1 && logicalBoard[row+1][colum] == 1)
            {
                //TODO::Vertical
                SosSequence s = new SosSequence(SosSequence.Direction.Vertical);
                s.indecies = new short[][]{{row, colum}, {(short) (row-1), (short) (colum)}, {(short) (row+1), (short) (colum)}};
                sosSequenceList.add(s);
                sosCount++;
                System.out.println("SOS Found(Vertical)");
            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row-1][colum-1] == 1 && logicalBoard[row+1][colum+1] == 1)
            {
                //TODO::Diagonal(Top To Down)
                SosSequence s = new SosSequence(SosSequence.Direction.MainDiagonal);
                s.indecies = new short[][]{{row, colum}, {(short) (row-1), (short) (colum-1)}, {(short) (row+1), (short) (colum+1)}};
                sosSequenceList.add(s);
                sosCount++;
                System.out.println("SOS Found(Diagonal(Top To Down))");

            }
            if(colum > 0 && colum < logicalBoard[row].length - 1 && row > 0 && row < logicalBoard.length - 1 && logicalBoard[row+1][colum-1] == 1 && logicalBoard[row-1][colum+1] == 1)
            {
                //TODO::Diagonal(Down To Top)
                SosSequence s = new SosSequence(SosSequence.Direction.SubDiagonal);
                s.indecies = new short[][]{{row, colum}, {(short) (row+1), (short) (colum-1)}, {(short) (row-1), (short) (colum+1)}};
                sosSequenceList.add(s);
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
