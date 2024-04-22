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
            if (logicalBoard[s.row][s.col] == 0) {
                Color turnColor  = turn == 1 ? Color.BLUE : Color.RED;

//                if (state == State.S) {
//                    logicalBoard[s.row][s.col] = (short) 1;
//                    ImageIcon icon = new ImageIcon("src/images/s.png");
//                    Image img = icon.getImage();
//                    Gboard[s.row][s.col].setImg(img);
//                    count = CheckSos(s.row, s.col, turnColor);
//                } else if (state == State.O) {
//                    logicalBoard[s.row][s.col] = (short) 2;
//                    ImageIcon icon = new ImageIcon("src/images/o.png");
//                    Image img = icon.getImage();
//                    Gboard[s.row][s.col].setImg(img);
//                    count = CheckSos(s.row, s.col, turnColor);
//                }
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

    }

    @Override
    public void redoMove() {

    }


}
