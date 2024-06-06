import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InputOutputPanel extends JPanel {
    private int playerOneScore, playerTwoScore;
    public JLabel playerOneText, playerTwoText;
    private JRadioButton sOption, oOption;
    private AbstractGraphicsBoard game;
    /**
     * Constructor for InputOutputPanel.
     * @param game The game board instance
     */
    public InputOutputPanel(AbstractGraphicsBoard game)
    {
        setLayout(new GridLayout(3,1));
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.sOption = new JRadioButton();
        this.oOption = new JRadioButton();
        ButtonGroup bp = new ButtonGroup();
        this.playerOneText = new JLabel("player One:0");
        this.playerTwoText = new JLabel("player Two:0");
        this.game = game;

        sOption.setSelected(true);

        bp.add(sOption);
        bp.add(oOption);
        JPanel topPanel = new JPanel();
        topPanel.add(playerOneText);
        topPanel.add(playerTwoText);
        add(topPanel);
        JPanel bottomPanel = new JPanel();

        bottomPanel.add(new JLabel("Choose letter:"));
        bottomPanel.add(new JLabel("S:"));
        bottomPanel.add(sOption);
        bottomPanel.add(new JLabel("O:"));
        bottomPanel.add(oOption);
        JPanel buttonPlanel = new JPanel();
        buttonPlanel.setLayout(new GridLayout(1, 3));
        add(bottomPanel);
        JButton undo = new JButton("undo");
        buttonPlanel.add(undo);
        JButton redo = new JButton("redo");
        buttonPlanel.add(redo);
        game.state = State.S;

        JButton saveGame = new JButton("Save Game");
        buttonPlanel.add(saveGame);
        add(buttonPlanel);
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(game instanceof EVE)) {
                    if(game instanceof PVP)
                        game.saveGame(GameType.PVP);
                    else if (game instanceof PVE && !((PVE) game).played) {
                        game.saveGame(GameType.PVE);
                    }
                }

            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game instanceof PVE) {
                    if (!((PVE) game).played)
                        game.undoMove();
                }
                else {
                        game.undoMove();
                    }
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(game instanceof PVE) {
                    if (!((PVE) game).played)
                        game.redoMove();
                }
                    else {
                        game.redoMove();
                    }
            }
        });
        sOption.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                   game.state = State.S;
                }
            }
        });

        oOption.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    game.state = State.O;
                }
            }
        });
    }
    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }
    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

}
