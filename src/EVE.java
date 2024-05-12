import javax.swing.*;
import java.awt.*;

public class EVE extends Entity{
    private int computer2Score;
    /**
     * Constructor for the EVE class.
     * Initializes the EVE entity with the specified board size and algorithms.
     * @param boardSize Size of the board
     * @param a1 Algorithm for the first player
     * @param a2 Algorithm for the second player
     */
    public EVE(int boardSize, Algorithm a1, Algorithm a2) {
        super(boardSize);
        playGame(a1, a2);
        this.computer2Score = 0;
    }
    /**
     * Method to play the game between two algorithms.
     * @param a1 Algorithm for the first player
     * @param a2 Algorithm for the second player
     */
    private void playGame(Algorithm a1, Algorithm a2) {

        Thread thread = new Thread(() ->{

            try {
                boolean b = false;
                do {
                    SetColor(Color.BLUE);
                    turn = 1;
                    if(a1 == Algorithm.Random)
                    {
                        int sosCount = 0;
                        do {
                            Thread.sleep(150);
                            sosCount = RandomMove();
                            computerScore+=sosCount;
                        }while (sosCount > 0);
                    }
                    else if (a1 == Algorithm.Expanding) {
                        int sosCount = 0;
                        do {
                            Thread.sleep(150);
                            sosCount = ExpandingMove();
                            computerScore+=sosCount;
                        }while (sosCount > 0);
                    } else if (a1 == Algorithm.AroundMoves) {
                        int sosCount = 0;
                        do {
                            Thread.sleep(150);
                            sosCount = AroundMovesMove();
                            computerScore+=sosCount;
                        }while (sosCount > 0);
                    }

                    if(boardFull()) {
                        endGame();
                        b = true;
                    }
                    else {
                        SetColor(Color.RED);
                        turn = 2;

                        if (a2 == Algorithm.Random) {
                            int sosCount = 0;
                            do {
                                Thread.sleep(150);
                                sosCount = RandomMove();
                                computer2Score += sosCount;
                            } while (sosCount > 0);
                        } else if (a2 == Algorithm.Expanding) {
                            int sosCount = 0;
                            do {
                                Thread.sleep(150);
                                sosCount = ExpandingMove();
                                computer2Score += sosCount;
                            } while (sosCount > 0);
                        } else if (a2 == Algorithm.AroundMoves) {
                            int sosCount = 0;
                            do {
                                Thread.sleep(150);
                                sosCount = AroundMovesMove();
                                computer2Score += sosCount;
                            } while (sosCount > 0);
                        }
                    }

                }while (!boardFull());
                if(!b)
                    endGame();

            }catch (InterruptedException ex)
            {

            }

        });
        thread.start();
    }
    /**
     * Overrides the undoMove method.
     * This feature is not implemented.
     */
    @Override
    public void undoMove() {
        //not a feature
    }


    /**
     * Overrides the redoMove method.
     * This feature is not implemented.
     */
    @Override
    public void redoMove() {
        //not a feature
    }
    /**
     * Overrides the replayGame method.
     * Replays the game based on the recorded moves.
     */
    @Override
    public void replayGame() {
        Thread thread = new Thread(() ->{
            turn = 1;
            bitBoard.clear();
            boardCounter = 0;
            for (int i = 0; i < Gboard.length; i++) {
                for (int j = 0; j < Gboard[i].length; j++) {
                    Gboard[i][j].clearLineArray();
                    ImageIcon icon = new ImageIcon("src/images/background.png");
                    Image img = icon.getImage();
                    Gboard[i][j].setImg(img);
                    Gboard[i][j].repaint();
//                    Gboard[i][j].removeActionListener(Gboard[i][j].getActionListeners()[0]);
                }
            }
            int size =  moves.size();
            for (int i = 0; i < size; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Move move = moves.get(i);
                markButton(move.i, move.j, move.state, move.player);
                turn = move.player;
                Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;
                int count = CheckSos((short) move.i, (short) move.j, turnColor);



            }
        });
        thread.start();
    }

    /**
     * Overrides the endGame method.
     * Displays the end game message and options for the user.
     */
    @Override
    protected void endGame() {
        String winnerMessage ="game ended " +
                (computer2Score > computerScore ? "second Algorithm won" : (computer2Score == computerScore ? " its a tie" : "First Algorithm won")) + " with a score of: "+
                (Math.max(computer2Score, computerScore));
        Object[] options = { "New Game", "Replay Game", "Exit"};
        Menu menu = new Menu(options, winnerMessage + "\nChoose an option to proceed:", "Game Over!!");
        int choice = menu.getChoice();
        switch (choice) {
            case 0: // New Game

                repaint();
                break;
            case 1:
                replayGame();
                break;
            case 2: // Exit
                System.exit(0);
                break;
        }
    }
}
