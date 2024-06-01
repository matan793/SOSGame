import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public abstract class AbstractGraphicsBoard extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;

    protected SButton[][]  Gboard;
    protected int boardCounter;
    protected BitBoard bitBoard;
    protected int turn;
    protected  int board_size;
    protected Stack<Move> moves;
    protected Stack<Move> redoMoves;
    public State state;
    /**
     * Constructor for AbstractGraphicsBoard.
     * Initializes the game board, bitBoard, and other necessary fields.
     * @param boardSize Size of the game board
     */
    public  AbstractGraphicsBoard(int boardSize){
        this.board_size = boardSize;
        this.Gboard = new SButton[boardSize][boardSize];
        this.boardCounter = 0;
        this.turn = 1;
        setLayout(new GridLayout(boardSize, boardSize));
        for (int i = 0; i < Gboard.length; i++) {
            for (int j = 0; j < Gboard[i].length; j++) {
                ImageIcon icon = new ImageIcon("src/images/background.png");
                Image img = icon.getImage();
                Gboard[i][j] = new SButton(img, i, j);
                add(Gboard[i][j]);
            }
        }
        this.state = State.S;
        this.bitBoard = new BitBoard(boardSize);
        this.moves = new Stack<>();
        this.redoMoves = new Stack<>();
    }
    /**
     * Marks a button on the game board with a specified state and player.
     * Updates the visual representation of the button based on the state.
     * @param row Row index of the button
     * @param col Column index of the button
     * @param s State to mark the button with (S or O)
     * @param player Player who made the move
     */
    public void markButton(int row, int col, State s, int player)
    {
        if(s == State.S)
            bitBoard.placeS(row, col);
        else
            bitBoard.placeO(row, col);
        ImageIcon icon = new ImageIcon(s == State.S ? "src/images/s.png" : "src/images/o.png");
        Image img = icon.getImage();
        Gboard[row][col].setImg(img);
        Gboard[row][col].repaint();
        boardCounter++;
        moves.push(new Move(row, col, s, player));
    }
    /**
     * Checks if the game board is full.
     * @return True if the game board is full, false otherwise
     */
    protected boolean boardFull()
    {
        return boardCounter == board_size*board_size;
    }

    /**
     * Checks for SOS patterns around a specified position on the game board.
     * Updates the visual representation of the buttons involved in the pattern.
     * @param row Row index of the position
     * @param colum Column index of the position
     * @param lineColor Color to mark the buttons involved in the SOS pattern
     * @return Number of SOS patterns found
     */
    protected int CheckSos(short row, short colum, Color lineColor)
    {
        int sosCount = 0;
        ArrayList<SButton> arr = new ArrayList<>(3);
        if(bitBoard.checkCell(row, colum) == 2)
        {
            if (colum > 0 && colum < board_size - 1 && bitBoard.checkCell(row, colum-1) == 1 && bitBoard.checkCell(row, colum+1) == 1)
            {
                //TODO::Horizontal
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row][colum+1]);
                arr.add(Gboard[row][colum-1]);
                sosCount++;
            }
            if(row > 0 && row < board_size - 1 && bitBoard.checkCell(row-1, colum) == 1 &&bitBoard.checkCell(row+1, colum) == 1)
            {
                //TODO::Vertical
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row-1][colum]);
                arr.add(Gboard[row+1][colum]);
                sosCount++;
            }
            if(colum > 0 && colum < board_size - 1 && row > 0 && row <board_size - 1 && bitBoard.checkCell(row-1, colum-1) == 1 && bitBoard.checkCell(row+1, colum+1) == 1)
            {
                //TODO::Diagonal(Top To Down)
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row+1][colum+1]);
                arr.add(Gboard[row-1][colum-1]);

                sosCount++;
            }
            if(colum > 0 && colum < board_size - 1 && row > 0 && row < board_size - 1 && bitBoard.checkCell(row+1, colum-1) == 1 && bitBoard.checkCell(row-1, colum+1) == 1)
            {
                //TODO::Diagonal(Down To Top)

                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row-1][colum+1].setLineArray(3, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row+1][colum-1]);
                arr.add(Gboard[row-1][colum+1]);

                sosCount++;
            }
        }
        if(bitBoard.checkCell(row, colum) == 1)
        {
            if(colum < board_size - 2 && bitBoard.checkCell(row, colum+1) == 2 && bitBoard.checkCell(row, colum+2) == 1)
            {
                //TODO::horizontal (forward)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum+1].setLineArray(0, lineColor);
                Gboard[row][colum+2].setLineArray(0, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row][colum+1]);
                arr.add(Gboard[row][colum+2]);

                sosCount++;
            }
            if(colum > 1 && bitBoard.checkCell(row, colum-1) == 2 && bitBoard.checkCell(row, colum-2) == 1)
            {
                //TODO::horizontal (backwards)
                Gboard[row][colum].setLineArray(0, lineColor);
                Gboard[row][colum-1].setLineArray(0, lineColor);
                Gboard[row][colum-2].setLineArray(0, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row][colum-1]);
                arr.add(Gboard[row][colum-2]);

                sosCount++;
            }
            if(row < board_size - 2 && bitBoard.checkCell(row+1, colum) == 2 && bitBoard.checkCell(row+2, colum) == 1)
            {
                //TODO::vertical (downward)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row+1][colum].setLineArray(1, lineColor);
                Gboard[row+2][colum].setLineArray(1, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row+1][colum]);
                arr.add(Gboard[row+2][colum]);

                sosCount++;
            }
            if(row > 1 && bitBoard.checkCell(row-1, colum) == 2 && bitBoard.checkCell(row-2, colum) == 1)
            {
                //TODO::vertical (upwards)
                Gboard[row][colum].setLineArray(1, lineColor);
                Gboard[row-1][colum].setLineArray(1, lineColor);
                Gboard[row-2][colum].setLineArray(1, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row-1][colum]);
                arr.add(Gboard[row-2][colum]);

                sosCount++;
            }
            if(row > 1 && colum > 1 && bitBoard.checkCell(row-1, colum-1) == 2 && bitBoard.checkCell(row-2, colum-2) == 1)
            {
                //TODO::Diagonal(left up(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row-1][colum-1].setLineArray(2, lineColor);
                Gboard[row-2][colum-2].setLineArray(2, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row-1][colum-1]);
                arr.add(Gboard[row-2][colum-2]);

                sosCount++;
            }
            if(row < board_size - 2 && colum <board_size -2 && bitBoard.checkCell(row+1, colum+1) == 2 && bitBoard.checkCell(row+2, colum+2) == 1)
            {
                //TODO::Diagonal(right down(top to down))
                Gboard[row][colum].setLineArray(2, lineColor);
                Gboard[row+1][colum+1].setLineArray(2, lineColor);
                Gboard[row+2][colum+2].setLineArray(2, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row+1][colum+1]);
                arr.add(Gboard[row+2][colum+2]);

                sosCount++;
            }
            if(colum > 1 && row < board_size - 2 && bitBoard.checkCell(row+1, colum-1) == 2 && bitBoard.checkCell(row+2, colum-2) == 1)
            {
                //TODO::Diagonal(left down(down to top))
                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row+1][colum-1].setLineArray(3, lineColor);
                Gboard[row+2][colum-2].setLineArray(3, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row+1][colum-1]);
                arr.add(Gboard[row+2][colum-2]);

                sosCount++;
            }
            if(row > 1 && colum < board_size - 2 && bitBoard.checkCell(row-1, colum+1) == 2 &&bitBoard.checkCell(row-2, colum+2) == 1)
            {
                //TODO::Diagonal(right up(down to top))
                Gboard[row][colum].setLineArray(3, lineColor);
                Gboard[row-1][colum+1].setLineArray(3, lineColor);
                Gboard[row-2][colum+2].setLineArray(3, lineColor);
                arr.add(Gboard[row][colum]);
                arr.add(Gboard[row-1][colum+1]);
                arr.add(Gboard[row-2][colum+2]);

                sosCount++;
            }

        }
        for (SButton s : arr)
        {
            s.repaint();
        }
        return sosCount;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        int w = getWidth() / this.board_size;
        int h = getHeight() / this .board_size;

       // paintSquares(g, w, h);
        //paintSO(g);
    }
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

            } catch (IOException e) {
                e.printStackTrace();

            }
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
    }

    // Abstract methods for undo, redo, replay, and end game logic
    public abstract void undoMove();
    public abstract  void redoMove();
    public abstract  void replayGame(Stack<Move> moveStack);
    protected abstract void endGame();
    protected abstract void newGame();

}
