import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainWindow extends JFrame {
    public MainWindow() {
        String Message = "What Size Do You Want To Play";
        Object[] options = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10", "Exit"};
        Menu menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
        int choice = menu.getChoice();

        int size = 3;
        switch (choice) {
            case 0: // Replay
                size = 3;
            case 1: // New Game
                size = 4;

                break;
            case 2:
                size = 5;
                break;
            case 3:
                size = 6;
                break;
            case 4:
                size = 7;
                break;
            case 5:
                size = 8;
                break;
            case 6:
                size = 9;
                break;
            case 7:
                size = 10;
                break;
            default:
                System.exit(0);
                break;
        }

        Message = "What Mode You Want To Play";
        options = new Object[]{"PlayerVsPlayer", "PlayerVsComputer", "ComputerVsComputer", "Exit"};
        menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
        choice = menu.getChoice();
        AbstractGraphicsBoard sosGame = null;
        switch (choice) {
            case 0: // Replay
                sosGame = new PVP(size);
                break;
            case 1:
                Message = "What Mode You Want To Play";
                options = new Object[]{"Random Algorithm", "Expanding Algorithm", "Around Moves Algorithm", "Exit"};
                menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
                choice = menu.getChoice();
                switch (choice) {
                    case 0:
                        sosGame = new PVE(size, Algorithm.Random);
                        break;
                    case 1:
                        sosGame = new PVE(size, Algorithm.Expanding);
                        break;
                    case 2:
                        sosGame = new PVE(size, Algorithm.AroundMoves);
                        break;
                    case 3:
                    default:
                        System.exit(0);
                        break;
                }
            case 2: // Exit
                Message = "What Mode You Want To Play";
                options = new Object[]{"Random vs Random", "Random vs Expanding", "Random vs Around Moves", "Expanding vs Expanding", "Expanding vs Around Moves", "Around Moves vs Around Moves", "Exit"};
                menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
                choice = menu.getChoice();
                switch (choice) {
                    case 0:
                        sosGame = new EVE(size, Algorithm.Random, Algorithm.Random);
                        break;
                    case 1:
                        sosGame = new EVE(size, Algorithm.Random, Algorithm.Expanding);
                        break;
                    case 2:
                        sosGame = new EVE(size, Algorithm.Random, Algorithm.AroundMoves);
                        break;
                    case 3:
                        sosGame = new EVE(size, Algorithm.Expanding, Algorithm.Expanding);
                        break;
                    case 4:
                        sosGame = new EVE(size, Algorithm.Expanding, Algorithm.AroundMoves);
                        break;
                    case 5:
                        sosGame = new EVE(size, Algorithm.AroundMoves, Algorithm.AroundMoves);
                        break;
                    case 6:
                    default:
                        System.exit(0);
                        break;

                }


        }
        setTitle("Sos Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(size * 100, size * 100);
        setLayout(new BorderLayout());
        add(sosGame, BorderLayout.CENTER);
        add(new InputOutputPanel(sosGame), BorderLayout.NORTH);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        new MainWindow();
    }
}