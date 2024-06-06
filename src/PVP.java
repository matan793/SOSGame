import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;
import java.util.List;

public class PVP extends AbstractGraphicsBoard{

    private int playerOneScore, playerTwoScore;
    /**
     * Constructor for the PVP class.
     * Initializes a new instance of the PVP class with the specified board size.
     * Adds action listeners to all buttons on the game board.
     * Prints the current player's turn.
     * @param boardSize The size of the game board.
     */
    public PVP(int boardSize)
    {
        super(boardSize);

        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        for (int i = 0; i < super.Gboard.length; i++) {
            for (int j = 0; j < super.Gboard[i].length; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }
       // Gboard[0][0].linesArray[0] = Color.decode().get;
        System.out.println("player " + turn + " turn");
        System.out.println(new Color(Color.BLUE.getRGB() + Color.RED.getRGB()).equals(Color.MAGENTA));

    }
    public PVP(int boardSize, Stack<Move> moveStack)
    {
        super(boardSize);

        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        for (int i = 0; i < moveStack.size(); i++) {
            moves.push(moveStack.get(i));
        }
        replayGame(moveStack);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }
    }
    class AL implements ActionListener
    {
        /**
         * Responds to button click events.
         * Marks the clicked button with the current player's color if the cell is empty.
         * Checks for SOS formations after each move and updates the scores accordingly.
         * Switches the turn to the next player if no SOS formation is found.
         * @param e The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            SButton s = (SButton) e.getSource();
            int count = 0;

            if (bitBoard.checkCell(s.row, s.col) == 0) {
                redoMoves.clear();
                Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;
                markButton(s.row, s.col, state, turn);
                count = CheckSos(s.row, s.col, turnColor);
                if(turn == 1)
                    playerOneScore += count;
                else
                    playerTwoScore += count;
                if(inputOutputPanel != null)
                {
                    inputOutputPanel.playerOneText.setText("player One:" + playerOneScore);
                    inputOutputPanel.playerTwoText.setText("player Two:" + playerTwoScore);
                    //inputOutputPanel.repaint();
                }
                if(boardFull())
                    endGame();

                if(count == 0) {

                    turn = 3 - turn;
                }
            }
        }
    }

    /**
     * Handles the end of the game.
     * Constructs a message indicating the winner and their score.
     * Displays a dialog box with options for starting a new game, replaying the game, or exiting the application.
     */
    @Override
    protected void endGame() {
        String winnerMessage = "game ended the player number " +
                (playerOneScore > playerTwoScore ? "1" : (playerTwoScore == playerOneScore ? "1 and 2, its a tie" : "2")) + " won with a score of: "+
                (Math.max(playerOneScore, playerTwoScore));
        Object[] options = { "New Game", "Replay Game", "Exit"};
        Menu menu = new Menu(options, winnerMessage + "\nChoose an option to proceed:", "Game Over!!");
        int choice = menu.getChoice();
        switch (choice) {
            case 0: // New Game
                newGame();
                break;
            case 1:
                replayGame(moves);
                break;
            case 2: // Exit
                System.exit(0);
                break;
        }
    }



    /*
    handles the making of a new game
     */
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
        playerOneScore = 0;
        playerTwoScore = 0;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + playerOneScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + playerTwoScore);
        }
    }

    /**
     * Handles the undoing of the player's move.
     */
    @Override
    public void undoMove() {
        if(moves.isEmpty())
            return;
        int sosCount = CheckSos((short) moves.peek().i, (short) moves.peek().j, turn == 1 ? Color.BLUE : Color.RED);
        Move move = moves.pop();
        ImageIcon icon = new ImageIcon("src/images/background.png");
        Image img = icon.getImage();
        Gboard[move.i][move.j].setImg(img);
        Gboard[move.i][move.j].repaint();
        int type = bitBoard.checkCell(move.i, move.j);
        for (int i = 0; i < 4; i++) {
            Gboard[move.i][move.j].linesArray[i] = null;
        }
        bitBoard.removeSO(move.i, move.j);
        if(turn == 1)
            playerTwoScore -= sosCount;
        else
            playerOneScore -= sosCount;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + playerOneScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + playerTwoScore);
        }
        boardCounter--;
        redoMoves.push(move);
        if(sosCount == 0)
        {
            turn = move.player;
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
               if (move.i < board_size - 2) {
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
       turn = move.player;

    }
    /**
     * Updates the color of the specified cell in the given direction.
     *
     * @param i The row index of the cell.
     * @param j The column index of the cell.
     * @param direction The direction to update (0 for horizontal, 1 for vertical, 2 for diagonal up, 3 for diagonal down).
     * @param turn The player's turn (1 for player, 2 for computer).
     */
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
    /**
     * Handles the replaying of the game.
     */
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
            playerOneScore += count;
        else
            playerTwoScore += count;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + playerOneScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + playerTwoScore);
        }
        if(boardFull())
            endGame();

        if(count == 0) {

            turn = 3 - turn;
        }
    }

    @Override
    public void replayGame(Stack<Move> moveStack) {
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
                   if(Gboard[i][j].getActionListeners().length > 0)
                       Gboard[i][j].removeActionListener(Gboard[i][j].getActionListeners()[0]);
               }
           }
           int size =  moveStack.size();
           int count = 0;
           for (int i = 0; i < size; i++) {
               try {
                   Thread.sleep(300);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               Move move = moveStack.get(i);
               markButton(move.i, move.j, move.state, move.player);
               turn = move.player;
               Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;
               count = CheckSos((short) move.i, (short) move.j, turnColor);
               if(turn == 1)
                   playerOneScore += count;
               else
                   playerTwoScore += count;
               if(inputOutputPanel != null)
               {
                   inputOutputPanel.playerOneText.setText("player One:" + playerOneScore);
                   inputOutputPanel.playerTwoText.setText("player Two:" + playerTwoScore);
               }
               if(boardFull())
                   endGame();


           }
           if(!moveStack.isEmpty() && count == 0)
               turn = 3 - turn;

       });
       thread.start();

    }


}
