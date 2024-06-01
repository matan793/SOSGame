import javax.swing.*;
import java.awt.*;

public class SButton extends JButton
{
    private transient Image img;
    public State letter;

    public Color[] linesArray;
    public final short row;
    public final short col;
    /**
     * Constructor for the SButton class.
     * Initializes a new instance of the SButton class with the specified image, row, and column.
     * @param img The image to be displayed on the button.
     * @param row The row index of the button on the game board.
     * @param col The column index of the button on the game board.
     */
    public SButton(Image img, int row, int col)
    {
        this.row = (short) row;
        this.col = (short) col;
        linesArray = new Color[4];
        this.img=img;
        //this.letter = State.Empty;
    }
    /**
     * Gets the image displayed on the button.
     * @return The image displayed on the button.
     */
    public Image getImg()
    {
        return img;
    }
    /**
     * Sets the image to be displayed on the button.
     * @param img The image to be displayed on the button.
     */
    public void setImg(Image img)
    {
        this.img = img;
    }
    /**
     * Overrides the paintComponent method to customize the button appearance.
     * @param g The Graphics object used for painting.
     */
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
    /**
     * Sets a line in the linesArray at the specified index with the given color.
     * If the line already exists, its color is blended with the new color.
     * @param i The index of the line.
     * @param lineColor The color of the line to be set.
     */
    public void setLineArray(int i, Color lineColor) {
        if(linesArray[i] != null)
        {
            linesArray[i] = new Color(Math.min(255, linesArray[i].getRed() + lineColor.getRed()),Math.min(255, linesArray[i].getGreen() + lineColor.getGreen()), Math.min(255, linesArray[i].getBlue() + lineColor.getBlue()));
        }
        else{
            linesArray[i] = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
        }
    }
    /**
     * Removes a line from the linesArray at the specified index if it matches the given color.
     * If the line exists but its color doesn't match, its color is blended with the complement of the given color.
     * @param i The index of the line.
     * @param lineColor The color of the line to be removed.
     */
    public void RemoveLineArray(int i, Color lineColor)
    {

        if(linesArray[i] != null && linesArray[i].getRGB() - lineColor.getRGB() == 0)
        {
            linesArray[i] = null;
        }
        else{
            linesArray[i] = new Color(Math.max(0, linesArray[i].getRed() - lineColor.getRed()),Math.max(0, linesArray[i].getGreen() - lineColor.getGreen()), Math.max(0, linesArray[i].getBlue() - lineColor.getBlue()));
        }
    }

    /**
     * Clears all lines in the linesArray.
     */
    public void clearLineArray() {
        for (int i = 0; i < 4; i++) {
            linesArray[i] = null;
        }

    }
}