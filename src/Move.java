import java.awt.*;

public class Move {
    public State state;
    public int i, j;

    public Move(int i, int j, State s) {
        this.state = s;
        this.i = i;
        this.j = j;
    }

}
