import javax.swing.*;
import java.awt.*;

public class SButton extends JButton
{
    private Image img;
    public ButtonType letter;

    public Color[] linesArray;
    public final int row, col;
    public SButton(Image img, int row, int col)
    {
        this.row = row;
        this.col = col;
        linesArray = new Color[4];
        this.img=img;
        this.letter = ButtonType.Empty;
    }

    public Image getImg()
    {
        return img;
    }

    public void setImg(Image img)
    {
        this.img = img;
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(),null);
       // drawLine(g, lineDirection.straight, Color.red);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(5));
//        if(diagnalsArray[0])
//        {
//            //horizontal
//            g2d.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
//        }
//        if(diagnalsArray[1])
//        {
//            g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
//        }
//        if(diagnalsArray[2])
//        {
//            g2d.drawLine(0, 0, getWidth(), getHeight());
//        }
//        if(diagnalsArray[3])
//        {
//            g2d.drawLine(0, getHeight(), getWidth(), 0);
//        }



        if(linesArray[0] != null)
        {
            //horizontal\
            g2d.setColor(linesArray[0]);
            g2d.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        }
        if(linesArray[1] != null)
        {
            g2d.setColor(linesArray[1]);

            g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
        }
        if(linesArray[2] != null)
        {
            g2d.setColor(linesArray[2]);

            g2d.drawLine(0, 0, getWidth(), getHeight());
        }
        if(linesArray[3] != null)
        {
            g2d.setColor(linesArray[3]);
            g2d.drawLine(0, getHeight(), getWidth(), 0);
        }
    }
    enum ButtonType{
        Empty,
        S,
        O
    }
   
}