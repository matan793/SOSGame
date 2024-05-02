import java.awt.*;
import java.util.Random;
import java.util.Stack;

public abstract class Entity extends AbstractGraphicsBoard {
    protected boolean played = false;

    protected int computerScore;
    Color color;

    public Entity(int boardSize) {
        super(boardSize);
        this.computerScore = 0;
        //this.moves = new Stack<>();
        this.color = Color.RED;
    }
    public void SetColor(Color color)
    {
        this.color = color;
    }
    protected int evaluateBoardForS(int row, int colum)
    {
        int sosCount = 0;
        if(colum < board_size - 2 && bitBoard.checkCell(row, colum+1) == 2 && bitBoard.checkCell(row, colum+2) == 1)
        {
            //TODO::horizontal (forward)
            sosCount++;
            System.out.println("SOS Found(horizontal (forward))");

        }
        if(colum > 1 &&bitBoard.checkCell(row, colum-1) == 2 && bitBoard.checkCell(row, colum-2) == 1)
        {
            //TODO::horizontal (backwards)
            sosCount++;
            System.out.println("SOS Found(horizontal (backwards))");

        }
        if(row < board_size - 2 && bitBoard.checkCell(row+1, colum) == 2 && bitBoard.checkCell(row+2, colum) == 1)
        {
            //TODO::vertical (downward)
            sosCount++;
            System.out.println("SOS Found(vertical (downward))");

        }
        if(row > 1 && bitBoard.checkCell(row-1, colum) == 2 && bitBoard.checkCell(row-2, colum) == 1)
        {
            //TODO::vertical (upwards)
            sosCount++;
            System.out.println("SOS Found(vertical (upwards))");

        }
        if(row > 1 && colum > 1 && bitBoard.checkCell(row-1, colum-1) == 2 && bitBoard.checkCell(row-2, colum-2) == 1)
        {
            //TODO::Diagonal(left up(top to down))
            sosCount++;
        }
        if(row < board_size - 2 && colum < board_size -2 && bitBoard.checkCell(row+1, colum+1) == 2 && bitBoard.checkCell(row+2, colum+2) == 1)
        {
            //TODO::Diagonal(right down(top to down))
            sosCount++;
        }
        if(colum > 1 && row < board_size - 2 && bitBoard.checkCell(row+1, colum-1) == 2 && bitBoard.checkCell(row+2, colum-2) == 1)
        {
            //TODO::Diagonal(left down(down to top))
            sosCount++;
        }
        if(row > 1 && colum < board_size - 2 && bitBoard.checkCell(row-1, colum+1) == 2 && bitBoard.checkCell(row-2, colum+2) == 1)
        {
            //TODO::Diagonal(right up(down to top))
            sosCount++;
        }
        return sosCount;
    }
    protected int evaluateBoardForO(int row, int colum)
    {
        int sosCount = 0;

        if (colum > 0 && colum < board_size - 1 && bitBoard.checkCell(row, colum-1) == 1 && bitBoard.checkCell(row, colum+1) == 1)
        {
            //TODO::Horizontal
            sosCount++;
            System.out.println("SOS Found(Horizontal)");
        }
        if(row > 0 && row < board_size - 1 && bitBoard.checkCell(row-1, colum) == 1 && bitBoard.checkCell(row+1, colum) == 1)
        {
            //TODO::Vertical
            sosCount++;
            System.out.println("SOS Found(Vertical)");
        }
        if(colum > 0 && colum < board_size - 1 && row > 0 && row < board_size - 1 && bitBoard.checkCell(row-1, colum-1) == 1 && bitBoard.checkCell(row+1, colum+1) == 1)
        {
            //TODO::Diagonal(Top To Down)
            sosCount++;
            System.out.println("SOS Found(Diagonal(Top To Down))");

        }
        if(colum > 0 && colum < board_size - 1 && row > 0 && row < board_size - 1 && bitBoard.checkCell(row+1, colum-1) == 1 && bitBoard.checkCell(row-1, colum+1) == 1)
        {
            //TODO::Diagonal(Down To Top)
            sosCount++;
            System.out.println("SOS Found(Diagonal(Down To Top))");

        }
        return sosCount;
    }
    protected boolean isGoodForPlacingS(int row, int colm)
    {

        return true;
    }
    protected boolean isGoodForPlacingO(int row, int colum)
    {
        for (int i = (row==0 ? 0 : row - 1); i <= (row == board_size - 1 ? row : row + 1); i++)
        {
            for (int j = (colum==0 ? 0 : colum - 1); j <= (colum == board_size - 1 ? colum : colum + 1); j++)
            {
                if(i != row || j != colum)
                {
                    if(bitBoard.checkCell(i, j) == 2)
                        return false;
                }
            }
        }

        return true;
    }
    protected int RandomMove() {
        Random rnd = new Random();
        boolean found = false;
        while (!found) {

            int i = rnd.nextInt(board_size);
            int j = rnd.nextInt(board_size);
            if (bitBoard.checkCell(i, j) == 0) {
                played = false;
                found = true;
                int choice = rnd.nextInt(2) + 1;
                markButton(i, j, State.values()[choice - 1]);
                return CheckSos((short) i, (short) j, color);


            }
        }
        return 0;
    }
    @Override
    public void markButton(int row, int col, State s) {
        super.markButton(row, col, s);
        moves.push(new Move(row, col, s));

    }
    protected int ExpandingMove()
    {
        if(moves.empty())
        {
            return RandomMove();
        }
        Move lastMove = moves.peek();
        int max = 0;
        int[] maxIndecies = new int[2];
        State maxState = State.S; //just initializing
        for (int i= 1; i <= board_size && max < 2; i++) {
            int starti = Math.max((lastMove.i - i), 0);
            int startj = Math.max((lastMove.j - i), 0);
            int endi = Math.min((lastMove.i + i), board_size -1);
            int endj = Math.min((lastMove.j + i), board_size -1);
            for (int j = startj; j <= endj && j < board_size; j++) {
                int tmp = 0;
                if(bitBoard.isEmpty(starti, j))
                {

                    tmp = evaluateBoardForO(starti, j);

                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == starti))
                    {
                        max = tmp;
                        maxIndecies[0] = starti;
                        maxIndecies[1] = j;
                        maxState = State.O;
                    }
                    tmp = evaluateBoardForS(starti, j);
                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == starti))
                    {
                        max = tmp;
                        maxIndecies[0] = starti;
                        maxIndecies[1] = j;
                        maxState = State.S;
                    }
                }
                if(bitBoard.isEmpty(endi, j))
                {
                    tmp = evaluateBoardForO(endi, j);
                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == endi))
                    {
                        max = tmp;
                        maxIndecies[0] = endi;
                        maxIndecies[1] = j;
                        maxState = State.O;
                    }
                    tmp = evaluateBoardForS(endi, j);
                    if(tmp > max  && !(moves.peek().j == j && moves.peek().i == endi))
                    {
                        max = tmp;
                        maxIndecies[0] = endi;
                        maxIndecies[1] = j;
                        maxState = State.S;
                    }
                }

            }

            for (int j = starti + 1; j <= endi && j < board_size; j++) {
                int tmp = 0;

                if(bitBoard.isEmpty(j, startj)){//

                    tmp = evaluateBoardForO(j, startj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == startj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = startj;
                        maxState = State.O;
                    }
                    tmp = evaluateBoardForS(j, startj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == startj))//////
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = startj;
                        maxState = State.S;
                    }
                }
                if(bitBoard.isEmpty(j, endj)){
                    tmp = evaluateBoardForO(j, endj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == endj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = endj;
                        maxState = State.O;
                    }
                    tmp = evaluateBoardForS(j, endj);//
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == endj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = endj;
                        maxState = State.S;
                    }
                }

            }

        }
        if(max == 0)
            max = RandomMove();
        else {

            markButton(maxIndecies[0], maxIndecies[1], maxState);
            max = CheckSos((short) maxIndecies[0], (short) maxIndecies[1], color);
        }
        return max;
    }
    protected int AreaMove()
    {
        int sosCount = 0;
        int size = 0;
        if(board_size < 6)
            size = 3;
        else if (board_size < 9) {
            size = board_size / 2;
        }
        else
            size = board_size / 3;

        for (int i = 0; i < 0; i++) {
            
        }
        return sosCount;
    }
}
