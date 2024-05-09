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
        if(moves.isEmpty() || played)
            return;
        int sosCount = CheckSos((short) moves.peek().i, (short) moves.peek().j, turn == 1 ? Color.BLUE : Color.RED);
        Move move = moves.pop();
        ImageIcon icon = new ImageIcon("src/images/background.png");
        Image img = icon.getImage();
        Gboard[move.i][move.j].setImg(img);
        int type = bitBoard.checkCell(move.i, move.j);
        for (int i = 0; i < 4; i++) {
            Gboard[move.i][move.j].linesArray[i] = null;
        }
        bitBoard.removeSO(move.i, move.j);
        if(turn == 1)
            playerScore -= sosCount;
        else
            computerScore -= sosCount;
        boardCounter--;
        redoMoves.push(move);
        if(sosCount == 0)
        {
            if(!moves.isEmpty())
                turn = moves.peek().player;
            return;
        }
        if(type == 1) {
            if (move.j > 1) {
                updateColor(move.i, move.j - 1, 0, turn);
                updateColor(move.i, move.j - 2, 0, turn);
                if (move.i > 1) {
                    updateColor(move.i - 1, move.j - 1, 2, turn);
                    updateColor(move.i - 2, move.j - 2, 2, turn);
                }
                if (move.i < board_size - 2) {
                    updateColor(move.i + 1, move.j - 1, 3, turn);
                    updateColor(move.i + 2, move.j - 2, 3, turn);
                }
            }

            // Update to the right
            if (move.j < board_size - 2) {
                updateColor(move.i, move.j + 1, 0, turn);
                updateColor(move.i, move.j + 2, 0, turn);
                if (move.i > 1) {
                    updateColor(move.i - 1, move.j + 1, 3, turn);
                    updateColor(move.i - 2, move.j + 2, 3, turn);
                }
                if (move.i < board_size - 1) {
                    updateColor(move.i + 1, move.j + 1, 2, turn);
                    updateColor(move.i + 2, move.j + 2, 2, turn);
                }
            }

            // Update above
            if (move.i > 1) {
                updateColor(move.i - 1, move.j, 1, turn);
                updateColor(move.i - 2, move.j, 1, turn);
            }

            // Update below
            if (move.i < board_size - 2) {
                updateColor(move.i + 1, move.j, 1, turn);
                updateColor(move.i + 2, move.j, 1, turn);
            }
        } else if (type == 2) {
            if (move.j > 0) {
                updateColor(move.i, move.j - 1, 0, turn);
                if (move.i > 0) {
                    updateColor(move.i - 1, move.j - 1, 2, turn);
                }
                if (move.i < board_size - 1) {
                    updateColor(move.i + 1, move.j - 1, 3, turn);
                }
            }

            // Update to the right
            if (move.j < board_size - 1) {
                updateColor(move.i, move.j + 1, 0, turn);
                if (move.i > 0) {
                    updateColor(move.i - 1, move.j + 1, 3, turn);
                }
                if (move.i < board_size - 1) {
                    updateColor(move.i + 1, move.j + 1, 2, turn);
                }
            }

            // Update above
            if (move.i > 0) {
                updateColor(move.i - 1, move.j, 1, turn);
            }

            // Update below
            if (move.i < board_size - 1) {
                updateColor(move.i + 1, move.j, 1, turn);
            }
        }
        if(!moves.isEmpty())
            turn = moves.peek().player;

    }
    private void updateColor(int i, int j, int direction, int turn) {
        Color currentColor = Gboard[i][j].linesArray[direction];
        Color turnColor = turn == 1 ? Color.BLUE : Color.red;
        if (Gboard[i][j].linesArray[direction] != null && currentColor.equals(new Color(Color.BLUE.getRGB() + Color.RED.getRGB()))) {
            System.out.println(currentColor.equals(new Color(Color.BLUE.getRGB() + Color.RED.getRGB())));
            if (turn == 1) {
                Gboard[i][j].linesArray[direction] = Color.RED;
            } else if (turn == 2) {
                Gboard[i][j].linesArray[direction] = Color.BLUE;
            }

        } else if (Gboard[i][j].linesArray[direction] != null && currentColor.equals(turnColor)) {
            //Gboard[i][j].linesArray[direction] = null;
            Gboard[i][j].RemoveLineArray(direction, turnColor);
        }
    }
    @Override
    public void redoMove() {
        if(redoMoves.isEmpty())
            return;
        Move move = redoMoves.pop();
        markButton(move.i, move.j, move.state, move.player);
        turn = move.player;
        Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;
        int count = CheckSos((short) move.i, (short) move.j, turnColor);
        if(turn == 1)
            playerScore += count;
        else
            computerScore += count;
        if(boardFull())
            endGame();
        if(count == 0) {

            turn = 3 - turn;
        }
    }

    @Override
    public void replayGame() {

    }

    class AL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(played)
                return;
            turn = 1;
            SButton s = (SButton) e.getSource();
            if (bitBoard.checkCell(s.row, s.col) == 0) {
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

                    }while (AroundMovesMove() > 0);
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
