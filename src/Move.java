import java.awt.*;
import java.util.Collections;

public class Move {
    public State state;
    public int i, j;
    public int player;

    public Move(int i, int j, State s, int player) {
        this.state = s;
        this.i = i;
        this.j = j;
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move))
            return false;
        return ((Move) obj).i == this.i && ((Move) obj).j == this.j;
    }


}
