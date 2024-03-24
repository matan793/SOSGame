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
                moves.push(new Move(s.row, s.col, State.O));  

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
    private boolean ComputerMove(){
        if(difficultyLevel == Algorithm.Random)
        {

            Thread thread = new Thread(() ->{
                try {
                    int sosCount = 0;
                    do {
                        Thread.sleep(1500);
                        Random rnd  = new Random();
                        boolean found = false;
                        while (!found)
                        {

                            int i = rnd.nextInt(board_size);
                            int j = rnd.nextInt(board_size);
                            if(logicalBoard[i][j] == 0)
                            {
                                played = false;
                                found = true;
                                sosCount = 0;
                                int choice = rnd.nextInt(2) + 1;
                                moves.push(new Move(i, j, State.values()[choice-1]));
                                markButton(i, j, State.values()[choice - 1]);
                                sosCount = CheckSos((short) i, (short) j, Color.red);


                            }
                        }
                    }while (sosCount > 0);



                }catch (InterruptedException ex)
                {

                }

            });
            thread.start();


        }
        else if(difficultyLevel == Algorithm.Memory)
        {
            played = false;
            int soscount = 0;
            do {
                Move lastMove = moves.peek();
                int max = 0;
                int[] maxIndecies = new int[2];
                for (int i = 1; i <= board_size ; i++) {
                     int starti = Math.max((lastMove.i - i), 0);
                     int startj = Math.max((lastMove.j - i), 0);
                     int endi = Math.min((lastMove.i + i), board_size);
                     int endj = Math.min((lastMove.j + i), board_size);
                    for (int j = startj; j < endj; j++) {
                      //  if((moves.peek().j == j && moves.peek().i == starti) || (moves.peek().j == j && moves.peek().i == endi))
                        int tmp = evaluateBoardForO(starti, j);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = starti;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForS(starti, j);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = starti;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForO(endi, j);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = endi;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForS(endi, j);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = endi;
                            maxIndecies[1] = j;
                        }
                    }

                    for (int j = starti + 1; j < endi - 1; j++) {
                        int tmp = evaluateBoardForO(j, startj);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = starti;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForS(j, startj);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = starti;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForO(j, endj);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = endi;
                            maxIndecies[1] = j;
                        }
                        tmp = evaluateBoardForS(j, endj);
                        if(tmp > max)
                        {
                            max = tmp;
                            maxIndecies[0] = endi;
                            maxIndecies[1] = j;
                        }
                    }
                    soscount = max;
                }


            }while (soscount > 0);
        }

        //played = false;
        return false;
    }

    @Override
    public void markButton(int row, int col, State s) {
        super.markButton(row, col, s);
        computerBoard[row][col] = (short) (s == State.S ? 1 : 2);

    }


}
