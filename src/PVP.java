//i am gay as fuck
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;

public class PVP extends AbstractGraphicsBoard{
    public PVP(int boardSize)
    {
        super(boardSize);
        for (int i = 0; i < super.Gboard.length; i++) {
            for (int j = 0; j < super.Gboard[i].length; j++) {
                Gboard[i][j].addActionListener(new AL());
            }
        }
    }
    class AL implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            SButton s = (SButton)e.getSource();
            if(Lboard.logicalBoard[s.row][s.col] == 0)
            {
                if (state == State.S)
                {
                    Lboard.logicalBoard[s.row][s.col] = (short) 1;
                    ImageIcon icon = new ImageIcon("src/images/s.png");
                    Image img = icon.getImage();
                    Gboard[s.row][s.col].setImg(img);

                    System.out.println(Lboard.CheckSos(s.row, s.col));
                }
                else if(state == State.O)
                {
                    Lboard.logicalBoard[s.row][s.col] = (short) 2;
                    ImageIcon icon = new ImageIcon("src/images/o.png");
                    Image img = icon.getImage();
                    Gboard[s.row][s.col].setImg(img);
                    System.out.println(Lboard.CheckSos(s.row, s.col));
                }
            }

        }
    }
    @Override
    public void undoMove() {

    }

    @Override
    public void redoMove() {

    }


}
