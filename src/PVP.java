//i am gay as fuck
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;

public class PVP extends AbstractGraphicsBoard{
    private int turn;
    private int playerOneScore, playerTwoScore;
    public PVP(int boardSize)
    {
        super(boardSize);
        this.turn = 1;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        for (int i = 0; i < super.Gboard.length; i++) {
            for (int j = 0; j < super.Gboard[i].length; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }
       // Gboard[0][0].linesArray[0] = Color.decode().get;
        System.out.println("player " + turn + " turn");
    }
    class AL implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            SButton s = (SButton) e.getSource();
            int count = 0;
            if (bitBoard.checkCell(s.row, s.col) == 0) {
                Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;
                markButton(s.row, s.col, state);
                count = CheckSos(s.row, s.col, turnColor);
                if(turn == 1)
                    playerOneScore += count;
                else
                    playerTwoScore += count;
                if(boardFull())
                    endGame();
                if(count == 0)
                    turn = 3 - turn;
                System.out.println("player " + turn + " turn");
                for (int i = 0; i < board_size; i++) {
                    for (int j = 0; j < board_size; j++) {
                        System.out.print(bitBoard.checkCell(i, j) + ",");
                    }
                    System.out.println();
                }





            }

        }
    }


    @Override
    protected void endGame() {
        JOptionPane.showMessageDialog(this, "game ended the player number " +
                (playerOneScore > playerTwoScore ? "1" : (playerTwoScore == playerOneScore ? "1 and 2, its a tie" : "2")) + " won with a score of: "+
                (playerOneScore >= playerTwoScore ? playerOneScore : playerTwoScore));
    }

    @Override
    public void undoMove() {
        int sosCount = CheckSos((short) moves.peek().i, (short) moves.peek().j, Color.red);
        Move move = moves.pop();
        ImageIcon icon = new ImageIcon("src/images/background.png");
        Image img = icon.getImage();
        Gboard[move.i][move.j].setImg(img);
        for (int i = 1; i <= 2; i++) {
            //Gboard[i]

        }
    }

    @Override
    public void redoMove() {

    }


}
