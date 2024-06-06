import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;


public class MainWindow extends JFrame {
    public MainWindow() {
        AbstractGraphicsBoard sosGame = null;
        String Message = "What Size Do You Want To Play";
        Object[] options = {"PlayGame", "Load File", "Exit"};
        Menu menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
        int size = 3;
        int choice = menu.getChoice();
        if(choice == 1)
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SOS Files", "sos");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this); // Using null to center on screen
            String selectedFilePath = null;
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                 selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                System.exit(0);
            }

            try (FileInputStream fis = new FileInputStream(selectedFilePath);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
               
                GameType gameType = (GameType)ois.readObject();
                size = ois.readInt();
                Stack<Move> moves = (Stack<Move>)ois.readObject();
                if(gameType == GameType.PVP)
                    sosGame = new PVP(size, moves);

                else if (gameType == GameType.PVE) {
                    Algorithm algorithm = (Algorithm) ois.readObject();
                    sosGame = new PVE(size, algorithm, moves);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        else if(choice == 2)
        {
            System.exit(0);
        }
        else if(choice == 0) {
            Message = "What Size Do You Want To Play";
            options = new Object[]{"3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10", "Exit"};
            menu = new Menu(options, Message + "\nChoose an option to proceed:", "SOS");
            choice = menu.getChoice();


            switch (choice) {
                case 0: // Replay
                    size = 3;
                    break;
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
                    break;
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
        }
        else
            System.exit(0);
        InputOutputPanel inputOutputPanel = new InputOutputPanel(sosGame);
        sosGame.setInputOutputPanel(inputOutputPanel);
        setTitle("Sos Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(size * 120, size * 120);
        setLayout(new BorderLayout());
        add(sosGame, BorderLayout.CENTER);
        add(inputOutputPanel, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        new MainWindow();
    }
}