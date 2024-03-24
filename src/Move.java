import java.awt.*;

public class Move {
    public State state;
    public int i, j;

    public Move(int i, int j, State s) {
        this.state = s;
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move))
            return false;
        return ((Move) obj).i == this.i && ((Move) obj).j == this.j;
    }
}
