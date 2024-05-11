import javax.swing.*;
import java.awt.*;

public class SButton extends JButton
{
    private Image img;
    public State letter;

    public Color[] linesArray;
    public final short row;
    public final short col;
    public SButton(Image img, int row, int col)
    {
        this.row = (short) row;
        this.col = (short) col;
        linesArray = new Color[4];
        this.img=img;
        //this.letter = State.Empty;
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


        if(linesArray[0] != null)
        {
            //horizontal\
            g2d.setColor(linesArray[0]);
            g2d.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        }
        if(linesArray[1] != null)
        {
            //vertical
            g2d.setColor(linesArray[1]);
            g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
        }
        if(linesArray[2] != null)
        {
            //diagonal(bottom to top)
            g2d.setColor(linesArray[2]);
            g2d.drawLine(0, 0, getWidth(), getHeight());
        }
        if(linesArray[3] != null)
        {
            //diagonal(top to bottom)
            g2d.setColor(linesArray[3]);
            g2d.drawLine(0, getHeight(), getWidth(), 0);
        }
    }

    public void setLineArray(int i, Color lineColor) {
        if(linesArray[i] != null)
        {
            linesArray[i] = new Color(Math.min(255, linesArray[i].getRed() + lineColor.getRed()),Math.min(255, linesArray[i].getGreen() + lineColor.getGreen()), Math.min(255, linesArray[i].getBlue() + lineColor.getBlue()));
        }
        else{
            linesArray[i] = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
        }
    }
    public void RemoveLineArray(int i, Color lineColor)
    {
        int x =  linesArray[i].getRGB() - lineColor.getRGB();
        int b= Color.BLACK.getRGB();
        if(linesArray[i] != null && linesArray[i].getRGB() - lineColor.getRGB() == 0)
        {
            linesArray[i] = null;
            //linesArray[i] = new Color(Math.min(255, linesArray[i].getRed() + lineColor.getRed()),Math.min(255, linesArray[i].getGreen() + lineColor.getGreen()), Math.min(255, linesArray[i].getBlue() + lineColor.getBlue()));
        }
        else{
            linesArray[i] = new Color(Math.max(0, linesArray[i].getRed() - lineColor.getRed()),Math.max(0, linesArray[i].getGreen() - lineColor.getGreen()), Math.max(0, linesArray[i].getBlue() - lineColor.getBlue()));
        }
    }


   
}