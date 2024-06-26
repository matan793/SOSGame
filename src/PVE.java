import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PVE extends Entity {
    private int playerScore;
    private Algorithm difficultyLevel;
    /**
     * Constructs a PVE object with the specified board size and difficulty level.
     *
     * @param bsize The size of the game board.
     * @param difficultyLevel The difficulty level of the computer.
     */
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
    public PVE(int bsize, Algorithm difficultyLevel, Stack<Move> moveStack)
    {
        super(bsize);
        this.playerScore = 0;
        this.difficultyLevel = difficultyLevel;
//        for (int i = 0; i < moveStack.size(); i++) {
//            moves.push(moveStack.get(i));
//        }
        played = true;
        replayGame(moveStack);

        for (int i = 0; i < bsize; i++) {
            for (int j = 0; j < bsize; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }
    }
    /**
     * Handles the undoing of the player's move.
     */
    @Override
    public void undoMove() {
        if(moves.isEmpty() || played)
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
            playerScore -= sosCount;
        else
            computerScore -= sosCount;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + playerScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
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
            playerScore += count;
        else
            computerScore += count;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + playerScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
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
            playerScore = 0;
            computerScore = 0;
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
                if(count > 0)
                {
                    if(turn == 1)
                        playerScore += count;
                    else
                        computerScore += count;
                    if(inputOutputPanel != null)
                    {
                        inputOutputPanel.playerOneText.setText("player One:" + playerScore);
                        inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
                    }
                }


            }
            if(!moveStack.isEmpty() && count == 0)
                turn = 3 - turn;
            played = false;
        });
        thread.start();
    }
    @Override
    public void saveGame(GameType gameType) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SOS Files", "sos");
        fileChooser.setFileFilter(filter);
        String selectedFilePath = null;
        // Show the open dialog
        int result = fileChooser.showSaveDialog(this);

        // Check if a file was selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!selectedFilePath.endsWith(".sos")) {
                selectedFilePath += ".sos";
            } else {
                return;
            }
            try (FileOutputStream fos = new FileOutputStream(selectedFilePath);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(gameType);
                oos.writeInt(board_size);
                oos.writeObject(moves);
                oos.writeObject(difficultyLevel);

            } catch (IOException e) {
                e.printStackTrace();

            }
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
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
                if(inputOutputPanel != null)
                {
                    inputOutputPanel.playerOneText.setText("player One:" + playerScore);
                }
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
    /**
     * Initiates the computer's move based on the selected difficulty level.
     *
     * @return true if the computer successfully makes a move, false otherwise.
     */
    private boolean ComputerMove(){
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
                if(inputOutputPanel != null)
                {
                    inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
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
                if(inputOutputPanel != null)
                {
                    inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
                }
            });
            thread.start();
        }
        else if(difficultyLevel == Algorithm.AroundMoves)
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
                if(inputOutputPanel != null)
                {
                    inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
                }
            });
            thread.start();
        }

        //played = false;
        return false;
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
        played = false;
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                Gboard[i][j].clearLineArray();
                ImageIcon icon = new ImageIcon("src/images/background.png");
                Image img = icon.getImage();
                Gboard[i][j].setImg(img);
                Gboard[i][j].repaint();

            }
        }
        playerScore = 0;
        computerScore = 0;
        if(inputOutputPanel != null)
        {
            inputOutputPanel.playerOneText.setText("player One:" + computerScore);
            inputOutputPanel.playerTwoText.setText("player Two:" + computerScore);
        }
    }
    /**
     * Handles the end of the game, displaying the winner and options for the player.
     */
    @Override
    protected void endGame() {

        String winnerMessage ="game ended " +
                        (playerScore > computerScore ? "you won!" : (playerScore == computerScore ? "its a tie" : "computer won")) + " with a score of: "+
                        (Math.max(playerScore, computerScore));
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




}
