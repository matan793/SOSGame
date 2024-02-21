import java.time.Duration;

public class SosSequence {
    public  short[][] indecies;
    public Direction direction;
    public SosSequence(Direction direction)
    {
        this.indecies = new short[3][2];
        this.direction = direction;

    }

    public enum Direction{
        Horizontal,
        Vertical,
        MainDiagonal,
        SubDiagonal
    }
}
