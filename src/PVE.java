import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PVE extends Entity {
    private int playerScore;
    private Algorithm difficultyLevel;
    int debugCounter = 0;
    public PVE(int bsize, Algorithm difficultyLevel) {
        super(bsize);
        this.playerScore = 0;
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
            turn = 1;
            SButton s = (SButton) e.getSource();
            if (bitBoard.checkCell(s.row, s.col) == 0) {
                Color turnColor = Color.BLUE;

                markButton(s.row, s.col, state, turn);
                int sosCount = CheckSos(s.row, s.col, Color.BLUE);
                playerScore += sosCount;
                if(boardFull())
                    endGame();
                if (sosCount == 0) {
                    played = true;
                    turn = 2;
                    ComputerMove();
                }
            }
        }



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
                        sosCount = RandomMove();
                        computerScore += sosCount;
                        if(boardFull())
                            endGame();

                    }while (sosCount > 0);



                }catch (InterruptedException ex)
                {

                }

            });
            thread.start();


        }
        else if(difficultyLevel == Algorithm.Expanding)
        {


            Thread thread = new Thread(() ->{
                try {
                    int sosCount = 0;
                    do {
                        Thread.sleep(1500);
                        sosCount = ExpandingMove();
                         computerScore += sosCount;
                        if(boardFull())
                            endGame();

                    }while (sosCount > 0);
                    played = false;


                }catch (InterruptedException ex)
                {

                }

            });
            thread.start();
        }
        else if(difficultyLevel == Algorithm.Area)
        {
            Thread thread = new Thread(() ->{
                try {
                    int sosCount = 0;
                    do {
                        Thread.sleep(1500);
                        if(boardFull())
                            endGame();

                    }while (MiravMove() > 0);
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
    protected void endGame() {
        JOptionPane.showMessageDialog(this, "game ended " +
                (playerScore > computerScore ? "you won!" : (playerScore == computerScore ? "its a tie" : "computer won")) + " with a score of: "+
                (playerScore >= computerScore ? playerScore : computerScore));
        
    }




}
