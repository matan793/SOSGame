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
        System.out.println(Long.toBinaryString(sBoard[index]));
    }


    // Method to place an "O" on the board
    public void placeO(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;
        oBoard[index] |= 1L << bitPosition;
    }
    public void removeSO(int row, int col) {
        int pos = row * size + col; // Calculate position
        int index = pos / 64; // Determine which long in the array
        int bitPosition = pos % 64; // Determine bit position within the long
        long mask = ~(1L << bitPosition); // Create a mask to clear the bit

        sBoard[index] &= mask; // Clear the bit for 'S'
        oBoard[index] &= mask; // Clear the bit for 'O'
    }
    // Method to check if a cell is empty
    public boolean isEmpty(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;
        //return (sBoard[index] & (1L << bitPosition)) == 0 && (oBoard[index] & (1L << bitPosition)) == 0;
        return checkCell(row, col) == 0;
    }
    public int checkCell(int row, int col) {
        int pos = row * size + col;
        int index = pos / 64;
        int bitPosition = pos % 64;

        long sBit = sBoard[index] & (1L << bitPosition);
        long oBit = oBoard[index] & (1L << bitPosition);

        if (sBit != 0) {
            return 1; // "S" found
        } else if (oBit != 0) {
            return 2; // "O" found
        } else {
            return 0; // Cell is empty
        }
    }
}
