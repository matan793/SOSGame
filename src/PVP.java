//i am gay as fuck
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;

public class PVP extends AbstractGraphicsBoard{

    private int playerOneScore, playerTwoScore;
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
    class AL implements ActionListener
    {

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
                if(boardFull())
                    endGame();

                if(count == 0) {

                    turn = 3 - turn;
                }
            }
        }
    }


    @Override
    protected void endGame() {
        String winnerMessage = "game ended the player number " +
                (playerOneScore > playerTwoScore ? "1" : (playerTwoScore == playerOneScore ? "1 and 2, its a tie" : "2")) + " won with a score of: "+
                (Math.max(playerOneScore, playerTwoScore));
        Object[] options = {"Replay", "New Game", "Exit"};
        Menu menu = new Menu(options, winnerMessage + "\nChoose an option to proceed:", "Game Over!!");
        int choice = menu.getChoice();
        switch (choice) {
            case 0: // Replay
                break;
            case 1: // New Game

                repaint();
                break;
            case 2: // Exit
                System.exit(0);
                break;
        }
    }


    @Override
    public void undoMove() {
        if(moves.isEmpty())
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
            playerTwoScore -= sosCount;
        else
            playerOneScore -= sosCount;
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
            playerOneScore += count;
        else
            playerTwoScore += count;
        if(boardFull())
            endGame();

        if(count == 0) {

            turn = 3 - turn;
        }
    }

    @Override
    public void replayGame() {

    }


}
