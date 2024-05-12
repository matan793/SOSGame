import java.awt.*;
import java.util.Collections;

public class Move {
    public State state;
    public int i, j;
    public int player;
    /**
     * Constructs a Move object with the specified parameters.
     *
     * @param i The row index of the move.
     * @param j The column index of the move.
     * @param s The state of the move ('S' or 'O').
     * @param player The player who made the move (1 or 2).
     */
    public Move(int i, int j, State s, int player) {
        this.state = s;
        this.i = i;
        this.j = j;
        this.player = player;
    }
    /**
     * Checks if this Move object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move))
            return false;
        return ((Move) obj).i == this.i && ((Move) obj).j == this.j;
    }
}
