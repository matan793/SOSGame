import javax.swing.*;
import java.awt.*;

public class Statistics extends Entity{
    private int computer2Score;
    private int stat1;
    private int stat2;
    private int stat3;
    /**
     * Constructor for the EVE class.
     * Initializes the EVE entity with the specified board size and algorithms.
     * @param boardSize Size of the board
     * @param a1 Algorithm for the first player
     * @param a2 Algorithm for the second player
     */
    public Statistics(int boardSize, Algorithm a1, Algorithm a2) {
        super(boardSize);
        playGame(a1, a2);
        this.stat1 = 0;
        this.stat2 = 0;
        this.stat3 = 0;
        this.computer2Score = 0;
    }
    /**
     * Method to play the game between two algorithms.
     * @param a1 Algorithm for the first player
     * @param a2 Algorithm for the second player
     */
    private void playGame(Algorithm a1, Algorithm a2) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 500; i++) {
                    Thread.sleep(500);
                    System.out.println("game number " + i);
                    boolean b = false;
                    int c = 0;
                    do {
                        System.out.println("turn number + " + c++);
                        SetColor(Color.BLUE);
                        turn = 1;
                        if (a1 == Algorithm.Random) {
                            int sosCount = 0;
                            do {
                                sosCount = RandomMove();
                                computerScore += sosCount;
                            } while (sosCount > 0);
                        } else if (a1 == Algorithm.Expanding) {
                            int sosCount = 0;
                            do {
                                sosCount = ExpandingMove();
                                computerScore += sosCount;
                            } while (sosCount > 0);
                        } else if (a1 == Algorithm.AroundMoves) {
                            int sosCount = 0;
                            do {
                                sosCount = AroundMovesMove();
                                computerScore += sosCount;
                            } while (sosCount > 0);
                        }

                        if (boardFull()) {
                            endGame();
                            b = true;
                            break;
                        } else {
                            System.out.println("turn number + " + c++);

                            SetColor(Color.RED);
                            turn = 2;

                            if (a2 == Algorithm.Random) {
                                int sosCount = 0;
                                do {
                                    sosCount = RandomMove();
                                    computer2Score += sosCount;
                                } while (sosCount > 0);
                            } else if (a2 == Algorithm.Expanding) {
                                int sosCount = 0;
                                do {
                                    sosCount = ExpandingMove();
                                    computer2Score += sosCount;
                                } while (sosCount > 0);
                            } else if (a2 == Algorithm.AroundMoves) {
                                int sosCount = 0;
                                do {
                                    sosCount = AroundMovesMove();
                                    computer2Score += sosCount;
                                } while (sosCount > 0);
                            }
                        }
                        if (boardFull()) {
                            endGame();
                            break;
                        }

                    } while (!boardFull());
                }
                System.out.println("stat1:" + stat1 + " stat2:" + stat2 + "ties:" + stat3);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        System.out.println(winnerMessage);
        if(computerScore > computer2Score)
            stat1++;
        else if (computer2Score > computerScore) {
            stat2++;
        }
        else
            stat3++;
        newGame();
    }
    @Override
    protected void newGame() {
        turn = 1;
        moves.clear();
        redoMoves.clear();
        bitBoard.clear();
        boardCounter = 0;
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                Gboard[i][j].clearLineArray();
                ImageIcon icon = new ImageIcon("src/images/background.png");
                Image img = icon.getImage();
                Gboard[i][j].setImg(img);
                Gboard[i][j].repaint();

            }
        }
        computerScore = 0;
        computer2Score = 0;
    }
}
