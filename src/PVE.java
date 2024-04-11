import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PVE extends AbstractGraphicsBoard {
    private int playerScore;
    private int computerScore;
    private short[][] computerBoard;
    private Stack<Move> moves;
    private boolean played = false;
    private Algorithm difficultyLevel;
    int debugCounter = 0;
    public PVE(int bsize, Algorithm difficultyLevel) {
        super(bsize);
        this.playerScore = 0;
        this.computerScore = 0;
        this.moves = new Stack<>();
        this.computerBoard = new short[board_size][board_size];
        this.difficultyLevel = difficultyLevel;
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }

    }

    @Override
    public void undoMove() {

    }

    @Override
    public void redoMove() {

    }

    class AL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(played)
                return;
            SButton s = (SButton) e.getSource();
            if (logicalBoard[s.row][s.col] == 0) {
                Color turnColor = Color.BLUE;
//                if (state == State.S) {
//                   markButton(s.row, s.col);
//                    sosCount = CheckSos(s.row, s.col, turnColor);
//
//                    moves.push(new Move(s.row, s.col, State.O));
//                } else if (state == State.O) {
//                   markButton(s.row, s.col);
//                    sosCount = CheckSos(s.row, s.col, turnColor);
//
//
//                    moves.push(new Move(s.row, s.col, State.O));
//                }
                markButton(s.row, s.col, state);

                int sosCount = CheckSos(s.row, s.col, Color.BLUE);
                playerScore += sosCount;
                if (sosCount == 0) {
                    played = true;
//                    Thread thread = new Thread(() -> {
//                        try {
//                            do {
//                                Thread.sleep(1500);
//                            }while (ComputerMove());
//
//                        } catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    });
//
//                    thread.start();

                    ComputerMove();
                }
            }
        }



    }
    private int evaluateBoardForS(int row, int colum)
    {
        int sosCount = 0;
        if(colum < computerBoard[row].length - 2 && computerBoard[row][colum+1] == 2 && computerBoard[row][colum+2] == 1)
        {
            //TODO::horizontal (forward)
            sosCount++;
            System.out.println("SOS Found(horizontal (forward))");

        }
        if(colum > 1 && computerBoard[row][colum-1] == 2 && computerBoard[row][colum-2] == 1)
        {
            //TODO::horizontal (backwards)
            sosCount++;
            System.out.println("SOS Found(horizontal (backwards))");

        }
        if(row < computerBoard.length - 2 && computerBoard[row+1][colum] == 2 && computerBoard[row+2][colum] == 1)
        {
            //TODO::vertical (downward)
            sosCount++;
            System.out.println("SOS Found(vertical (downward))");

        }
        if(row > 1 && computerBoard[row-1][colum] == 2 && computerBoard[row-2][colum] == 1)
        {
            //TODO::vertical (upwards)
            sosCount++;
            System.out.println("SOS Found(vertical (upwards))");

        }
        if(row > 1 && colum > 1 && computerBoard[row-1][colum-1] == 2 && computerBoard[row-2][colum-2] == 1)
        {
            //TODO::Diagonal(left up(top to down))
            sosCount++;
        }
        if(row < computerBoard.length - 2 && colum < computerBoard[row].length -2 && computerBoard[row+1][colum+1] == 2 && computerBoard[row+2][colum+2] == 1)
        {
            //TODO::Diagonal(right down(top to down))
            sosCount++;
        }
        if(colum > 1 && row < computerBoard.length-2 && computerBoard[row+1][colum-1] == 2 && computerBoard[row+2][colum-2] == 1)
        {
            //TODO::Diagonal(left down(down to top))
            sosCount++;
        }
        if(row > 1 && colum < computerBoard[row] .length - 2 && computerBoard[row-1][colum+1] == 2 && computerBoard[row-2][colum+2] == 1)
        {
            //TODO::Diagonal(right up(down to top))
            sosCount++;
        }
        return sosCount;
    }
    private int evaluateBoardForO(int row, int colum)
    {
        int sosCount = 0;

        if (colum > 0 && colum < computerBoard[row].length - 1 && computerBoard[row][colum-1] == 1 && computerBoard[row][colum+1] == 1)
        {
            //TODO::Horizontal
            sosCount++;
            System.out.println("SOS Found(Horizontal)");
        }
        if(row > 0 && row < computerBoard.length - 1 && computerBoard[row-1][colum] == 1 && computerBoard[row+1][colum] == 1)
        {
            //TODO::Vertical
            sosCount++;
            System.out.println("SOS Found(Vertical)");
        }
        if(colum > 0 && colum < computerBoard[row].length - 1 && row > 0 && row < computerBoard.length - 1 && computerBoard[row-1][colum-1] == 1 && computerBoard[row+1][colum+1] == 1)
        {
            //TODO::Diagonal(Top To Down)
            sosCount++;
            System.out.println("SOS Found(Diagonal(Top To Down))");

        }
        if(colum > 0 && colum < computerBoard[row].length - 1 && row > 0 && row < computerBoard.length - 1 && computerBoard[row+1][colum-1] == 1 && computerBoard[row-1][colum+1] == 1)
        {
            //TODO::Diagonal(Down To Top)
            sosCount++;
            System.out.println("SOS Found(Diagonal(Down To Top))");

        }
        return sosCount;
    }
    private boolean isGoodForPlacingS(int row, int colm)
    {

        return true;
    }
    private boolean isGoodForPlacingO(int row, int colm)
    {

        return true;
    }
    private int RandomMove() {
        Random rnd = new Random();
        boolean found = false;
        while (!found) {

            int i = rnd.nextInt(board_size);
            int j = rnd.nextInt(board_size);
            if (logicalBoard[i][j] == 0) {
                played = false;
                found = true;
                int choice = rnd.nextInt(2) + 1;
                markButton(i, j, State.values()[choice - 1]);
                return CheckSos((short) i, (short) j, Color.red);


            }
        }
        return 0;
    }
    private int ExpandingMove()
    {
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


                int tmp = evaluateBoardForO(starti, j);

                if(tmp > max && logicalBoard[starti][j] == 0 && !(moves.peek().j == j && moves.peek().i == starti))
                {
                    max = tmp;
                    maxIndecies[0] = starti;
                    maxIndecies[1] = j;
                    maxState = State.O;
                }
                tmp = evaluateBoardForS(starti, j);
                if(tmp > max && logicalBoard[starti][j] == 0 && !(moves.peek().j == j && moves.peek().i == starti))
                {
                    max = tmp;
                    maxIndecies[0] = starti;
                    maxIndecies[1] = j;
                    maxState = State.S;

                }
                tmp = evaluateBoardForO(endi, j);
                if(tmp > max  && logicalBoard[endi][j] == 0 && !(moves.peek().j == j && moves.peek().i == endi))
                {
                    max = tmp;
                    maxIndecies[0] = endi;
                    maxIndecies[1] = j;
                    maxState = State.O;

                }
                tmp = evaluateBoardForS(endi, j);
                if(tmp > max && logicalBoard[endi][j] == 0 && !(moves.peek().j == j && moves.peek().i == endi))
                {
                    max = tmp;
                    maxIndecies[0] = endi;
                    maxIndecies[1] = j;
                    maxState = State.S;

                }
            }

            for (int j = starti + 1; j <= endi && j < board_size; j++) {
                int tmp = evaluateBoardForO(j, startj);
                if(tmp > max && logicalBoard[j][startj] == 0 && !(moves.peek().i == j && moves.peek().j == startj))
                {
                    max = tmp;
                    maxIndecies[0] = starti;
                    maxIndecies[1] = j;
                    maxState = State.O;

                }
                tmp = evaluateBoardForS(j, startj);
                if(tmp > max && logicalBoard[j][startj] == 0 && !(moves.peek().i == j && moves.peek().j == startj))
                {
                    max = tmp;
                    maxIndecies[0] = starti;
                    maxIndecies[1] = j;
                    maxState = State.S;

                }
                tmp = evaluateBoardForO(j, endj);
                if(tmp > max && logicalBoard[j][endj] == 0 && !(moves.peek().i == j && moves.peek().j == endj))
                {
                    max = tmp;
                    maxIndecies[0] = endi;
                    maxIndecies[1] = j;
                    maxState = State.O;

                }
                tmp = evaluateBoardForS(j, endj);
                if(tmp > max && logicalBoard[j][endj] == 0 && !(moves.peek().i == j && moves.peek().j == endj))
                {
                    max = tmp;
                    maxIndecies[0] = endi;
                    maxIndecies[1] = j;
                    maxState = State.S;

                }
            }

        }
        if(max == 0)
            max = RandomMove();
        else {
            markButton(maxIndecies[0], maxIndecies[1], maxState);
            computerScore+= CheckSos((short) maxIndecies[0], (short) maxIndecies[1], Color.RED);
        }
        return max;
    }
    private boolean ComputerMove(){
        debugCounter++;
        if(debugCounter == 2)
            System.out.println("mirav my wife");
        if(difficultyLevel == Algorithm.Random)
        {

            Thread thread = new Thread(() ->{
                try {
                    int sosCount = 0;
                    do {
                        Thread.sleep(1500);


                    }while (RandomMove() > 0);



                }catch (InterruptedException ex)
                {

                }

            });
            thread.start();


        }
        else if(difficultyLevel == Algorithm.Memory)
        {


            Thread thread = new Thread(() ->{
                try {

                    do {
                        Thread.sleep(1500);


                    }while (ExpandingMove() > 0);
                    played = false;


                }catch (InterruptedException ex)
                {

                }

            });
            thread.start();
        }

        //played = false;
        return false;
    }

    @Override
    public void markButton(int row, int col, State s) {
        super.markButton(row, col, s);
        computerBoard[row][col] = (short) (s == State.S ? 1 : 2);
        moves.push(new Move(row, col, s));

    }


}
