import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {
    public Main()
    {

        int size = 7;
        setTitle("Sos Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(size * 100, size * 100);
        setLayout(new BorderLayout());
        //EVE p = new EVE(size, Algorithm.Expanding, Algorithm.Expanding);
        //PVE p = new PVE(7, Algorithm.Expanding);
        PVP p2 = new PVP(size);
        add(p2, BorderLayout.CENTER);
        add(new InputOutputPanel(p2), BorderLayout.NORTH);
        //add(new ScorePanel(), BorderLayout.WEST);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}