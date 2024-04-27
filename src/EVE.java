public class EVE extends Entity{
    public EVE(int boardSize, Algorithm a1, Algorithm a2) {
        super(boardSize);
        playGame(a1, a2);
    }

    private void playGame(Algorithm a1, Algorithm a2) {
        Thread thread = new Thread(() ->{
            try {
              
                do {
                    Thread.sleep(1500);
                    if(a1 == Algorithm.Random)
                        RandomMove();
                    else if (a1 == Algorithm.Expanding) {
                        ExpandingMove();
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
                    if(a2 == Algorithm.Random)
                        RandomMove();
                    else if (a2 == Algorithm.Expanding) {
                        ExpandingMove();
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
