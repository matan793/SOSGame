import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PVE extends AbstractGraphicsBoard {
    private int playerScore;
    private int computerScore;
    private short[][] computerBoard;
    private boolean played = false;
    private DifficultyLevel difficultyLevel;
    public PVE(int bsize, DifficultyLevel difficultyLevel) {
        super(bsize);
        this.playerScore = 0;
        this.computerScore = 0;
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
                } else if (state == State.O) {
                    logicalBoard[s.row][s.col] = (short) 2;
                    computerBoard[s.row][s.col] = (short) 2;
                    ImageIcon icon = new ImageIcon("src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[s.row][s.col].setImg(img);
                    sosCount = CheckSos(s.row, s.col, turnColor);
                }

                played = true;
                playerScore += sosCount;
                if (sosCount == 0) {
                    Thread thread = new Thread(() -> {
                        try {
                            Thread.sleep(1500); // Wait for one second
                            // Call your function here
                            ComputerMove();

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    });

                    thread.start();
                }
            }
        }



    }
    private void ComputerMove(){
        if(difficultyLevel == DifficultyLevel.Easy)
        {
            Random rnd  = new Random();
            while (true)
            {
                int i = rnd.nextInt(board_size);
                int j = rnd.nextInt(board_size);
                if(logicalBoard[i][j] == 0)
                {
                    int choice = rnd.nextInt(2) + 1;
                    logicalBoard[i][j] = (short) choice;
                    ImageIcon icon = new ImageIcon(choice == 1 ? "src/images/s.png" : "src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[i][j].setImg(img);
                    computerScore += CheckSos((short) i, (short) j, Color.red);
                    break;
                }
            }


        }
        else if(difficultyLevel == DifficultyLevel.Medium)
        {
            System.out.println("PVE.ComputerMove Medium");
            int max = -1, choice = 0;
            short[] maxIndecies = new short[2];
            while(max == -1 || max > 0)
            {
                for (int i = 0; i < computerBoard.length; i++) {
                    for (int j = 0; j < computerBoard[i].length; j++) {
                        if(computerBoard[i][j] == 0){
                            computerBoard[i][j] = 1;
                            int sosCount =  CheckSosComputer((short) i, (short) j);
                            if(sosCount > max) {
                                maxIndecies[0] = (short) i;
                                maxIndecies[1]= (short) j;
                                max = sosCount;
                                choice = 1;
                            }
                            computerBoard[i][j] = 0;
                        }
                    }
                }

                for (int i = 0; i < computerBoard.length; i++) {
                    for (int j = 0; j < computerBoard[i].length; j++) {
                        if(computerBoard[i][j] == 0){
                            computerBoard[i][j] = 2;
                            int sosCount =  CheckSosComputer((short) i, (short) j);
                            if(sosCount > max) {
                                maxIndecies[0] = (short) i;
                                maxIndecies[1]= (short) j;
                                max = sosCount;
                                choice = 2;
                            }
                            computerBoard[i][j] = 0;
                        }
                    }
                }
                logicalBoard[maxIndecies[0]][maxIndecies[1]] = (short) choice;
                computerBoard[maxIndecies[0]][maxIndecies[1]] = (short) choice;
                ImageIcon icon = new ImageIcon(choice == 1 ? "src/images/s.png" : "src/images/o.png");
                Image img = icon.getImage();
                Gboard[maxIndecies[0]][maxIndecies[1]].setImg(img);
                CheckSos(maxIndecies[0], maxIndecies[1], Color.red);
                computerScore += max;
                System.out.printf("choice: %d, max Indecies[%d, %d], count: %d", choice, maxIndecies[0], maxIndecies[1], max);

            }


        }
        played = false;
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
