public class BitBoard {
    private long[] sBoard; // Bitboards for "S"
    private long[] oBoard; // Bitboards for "O"
    private final int size;

    public BitBoard(int size) {
        this.size = size;
        int arraySize = (int) Math.ceil(size * size / 64.0);
        System.out.println(arraySize);
        sBoard = new long[arraySize]; // Initialize with all bits as 0
        oBoard = new long[arraySize];
    }

    // Method to place an "S" on the board
    public void placeS(int row, int col) {
        int pos = row * size + col; // Calculate position
        int index = pos / 64; // Determine which long in the array
        int bitPosition = pos % 64; // Determine bit position within the long
        sBoard[index] |= 1L << bitPosition; // Set the bit
    }


    // Method to place an "O" on the board
    public void placeO(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;
        oBoard[index] |= 1L << bitPosition;
    }

    // Method to check if a cell is empty
    public boolean isEmpty(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;
        return (sBoard[index] & (1L << bitPosition)) == 0 && (oBoard[index] & (1L << bitPosition)) == 0;
    }
    public State checkCell(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;

        long sBit = sBoard[index] & (1L << bitPosition);
        long oBit = oBoard[index] & (1L << bitPosition);

        if (sBit != 0) {
            return State.S; // "S" found
        } else if (oBit != 0) {
            return State.O; // "O" found
        } else {
            return State.Empty; // Cell is empty
        }
    }
}
