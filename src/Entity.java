import java.awt.*;
import java.util.Random;
import java.util.Stack;

public abstract class Entity extends AbstractGraphicsBoard {
    protected boolean played = false;
    protected Stack<Move> moves;
    protected int computerScore;


    public Entity(int boardSize) {
        super(boardSize);
        this.computerScore = 0;
        this.moves = new Stack<>();
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
                return CheckSos((short) i, (short) j, Color.red);


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
                if(!moves.contains(new Move(starti, j, State.S)) && !moves.contains(new Move(starti, j, State.O)))
                {

                    tmp = evaluateBoardForO(starti, j);

                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == starti))
                    {
                        max = tmp;
                        maxIndecies[0] = starti;
                        maxIndecies[1] = j;
                        maxState = State.O;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }
                    }
                    tmp = evaluateBoardForS(starti, j);
                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == starti))
                    {
                        max = tmp;
                        maxIndecies[0] = starti;
                        maxIndecies[1] = j;
                        maxState = State.S;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }

                    }
                }
                if(!moves.contains(new Move(endi, j, State.S)) && !moves.contains(new Move(endi, j, State.O)))
                {
                    tmp = evaluateBoardForO(endi, j);
                    if(tmp > max && !(moves.peek().j == j && moves.peek().i == endi))
                    {
                        max = tmp;
                        maxIndecies[0] = endi;
                        maxIndecies[1] = j;
                        maxState = State.O;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }

                    }
                    tmp = evaluateBoardForS(endi, j);
                    if(tmp > max  && !(moves.peek().j == j && moves.peek().i == endi))
                    {
                        max = tmp;
                        maxIndecies[0] = endi;
                        maxIndecies[1] = j;
                        maxState = State.S;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }

                    }
                }

            }

            for (int j = starti + 1; j <= endi && j < board_size; j++) {
                int tmp = 0;

                if(!moves.contains(new Move(j, startj, State.S)) && !moves.contains(new Move(j, startj, State.O))){//

                    tmp = evaluateBoardForO(j, startj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == startj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = starti;
                        maxState = State.O;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }

                    }
                    tmp = evaluateBoardForS(j, startj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == startj))//////
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = starti;
                        maxState = State.S;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }
                    }
                }
                if(!moves.contains(new Move(j, endj, State.S)) && !moves.contains(new Move(j, endi, State.O))){
                    tmp = evaluateBoardForO(j, endj);
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == endj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = endi;
                        maxState = State.O;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }
                    }
                    tmp = evaluateBoardForS(j, endj);//
                    if(tmp > max && !(moves.peek().i == j && moves.peek().j == endj))
                    {
                        max = tmp;
                        maxIndecies[0] = j;
                        maxIndecies[1] = endi;
                        maxState = State.S;
                        if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
                        {
                            System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
                        }
                    }
                }

            }

        }
        if(max == 0)
            max = RandomMove();
        else {
            if(!bitBoard.isEmpty(maxIndecies[0], maxIndecies[1]))
            {
                System.out.println("max indecies: " + maxIndecies[0] + "," + maxIndecies[1] + "... cell is: " + bitBoard.checkCell(maxIndecies[0], maxIndecies[1]));
            }
            markButton(maxIndecies[0], maxIndecies[1], maxState);
            computerScore+= CheckSos((short) maxIndecies[0], (short) maxIndecies[1], Color.RED);
        }
        return max;
    }
}
