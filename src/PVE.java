import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class PVE extends AbstractGraphicsBoard {
    private int playerScore;
    private int computerScore;
    private short[][] computerBoard;
    private ArrayList<Move> moves;
    private boolean played = false;
    private Algorithm difficultyLevel;
    public PVE(int bsize, Algorithm difficultyLevel) {
        super(bsize);
        this.playerScore = 0;
        this.computerScore = 0;
        this.moves = new ArrayList<>(bsize * bsize);
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
            int sosCount = 0;
            if (logicalBoard[s.row][s.col] == 0) {
                Color turnColor = Color.BLUE;
                if (state == State.S) {
                    logicalBoard[s.row][s.col] = (short) 1;
                    computerBoard[s.row][s.col] = (short) 1;
                    ImageIcon icon = new ImageIcon("src/images/s.png");
                    Image img = icon.getImage();
                    Gboard[s.row][s.col].setImg(img);
                    sosCount = CheckSos(s.row, s.col, turnColor);

                    moves.add(new Move(s.row, s.col, State.S));
                } else if (state == State.O) {
                    logicalBoard[s.row][s.col] = (short) 2;
                    computerBoard[s.row][s.col] = (short) 2;
                    ImageIcon icon = new ImageIcon("src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[s.row][s.col].setImg(img);
                    sosCount = CheckSos(s.row, s.col, turnColor);

                    moves.add(new Move(s.row, s.col, State.O));

                }


                playerScore += sosCount;
                if (sosCount == 0) {
                    played = true;
                    Thread thread = new Thread(() -> {
                        try {
                            do {
                                Thread.sleep(1500);
                            }while (ComputerMove());

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    });

                    thread.start();
                }
            }
        }



    }
    private boolean ComputerMove(){
        if(difficultyLevel == Algorithm.Random)
        {
            Random rnd  = new Random();
            while (true)
            {
                int i = rnd.nextInt(board_size);
                int j = rnd.nextInt(board_size);
                if(logicalBoard[i][j] == 0)
                {
                    int sosCount = 0;
                    int choice = rnd.nextInt(2) + 1;
                    logicalBoard[i][j] = (short) choice;
                    ImageIcon icon = new ImageIcon(choice == 1 ? "src/images/s.png" : "src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[i][j].setImg(img);
                    sosCount = CheckSos((short) i, (short) j, Color.red);
                    if(sosCount > 0)
                        return true;

                }
            }


        }
        else if(difficultyLevel == Algorithm.Memory)
        {        played = false;

            boolean flag = false;
            for (int i = 0; i < moves.size() && !flag; i++) {
                if(moves.get(i).state == State.S && logicalBoard[moves.get(i).i][moves.get(i).j+2] == 1)
                {
                    logicalBoard[moves.get(i).i][moves.get(i).j] = (short) 2;
                    computerBoard[moves.get(i).i][moves.get(i).j] = (short) 2;
                    ImageIcon icon = new ImageIcon("src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[moves.get(i).i][moves.get(i).j].setImg(img);
                    computerScore +=  CheckSos((short) moves.get(i).i, (short) moves.get(i).j, Color.red);
                    flag = true;
                    return true;
                }
                if(!flag)
                {
                    Random rnd  = new Random();
                    while (true)
                    {
                        int m = rnd.nextInt(board_size);
                        int j = rnd.nextInt(board_size);
                        if(logicalBoard[m][j] == 0)
                        {
                            int sosCount = 0;
                            int choice = rnd.nextInt(2) + 1;
                            logicalBoard[m][j] = (short) choice;
                            ImageIcon icon = new ImageIcon(choice == 1 ? "src/images/s.png" : "src/images/o.png");
                            Image img = icon.getImage();
                            Gboard[m][j].setImg(img);
                            sosCount = CheckSos((short) m, (short) j, Color.red);
                            if(sosCount > 0)
                                return true;
                            return false;

                        }
                    }
                }
            }

        }

        played = false;
        return false;
    }
    protected int CheckSosComputer(short row, short colum)
    {
        int sosCount = 0;
        if(computerBoard[row][colum] == 2)
        {
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
        }
        if(computerBoard[row][colum] == 1)
        {
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

        }
        return sosCount;
    }
}
