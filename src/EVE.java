import java.awt.*;

public class EVE extends Entity{
    private int computer2Score;
    public EVE(int boardSize, Algorithm a1, Algorithm a2) {
        super(boardSize);
        playGame(a1, a2);
        this.computer2Score = 0;
    }

    private void playGame(Algorithm a1, Algorithm a2) {
        Thread thread = new Thread(() ->{
            try {

                do {
                    SetColor(Color.RED);
                    if(a1 == Algorithm.Random)
                    {
                        int sosCount = 0;
                        do {
                            Thread.sleep(1500);

                        }while (RandomMove() > 0);
                    }
                    else if (a1 == Algorithm.Expanding) {
                        while (ExpandingMove() > 0);
                    } else if (a1 == Algorithm.Area) {
                        for (int i = 0; i < board_size; i++) {
                            for (int j = 0; j < board_size; j++) {
                                System.out.print(bitBoard.checkCell(i, j) + ",");
                            }
                            System.out.println();
                        }
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    Thread.sleep(1500);
                    if(boardFull())
                        endGame();
                    SetColor(Color.BLUE);
                    if(a2 == Algorithm.Random)
                        while (RandomMove() > 0);
                    else if (a2 == Algorithm.Expanding) {
                       while (ExpandingMove() >0);
                    } else if (a2 == Algorithm.Area) {

                    }
                    for (int i = 0; i < board_size; i++) {
                        for (int j = 0; j < board_size; j++) {
                            System.out.print(bitBoard.checkCell(i, j) + ",");
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                }while (!boardFull());



            }catch (InterruptedException ex)
            {

            }

        });
        thread.start();
    }

    @Override
    public void undoMove() {

    }

    @Override
    public void redoMove() {

    }

    @Override
    protected void endGame() {

    }
}
