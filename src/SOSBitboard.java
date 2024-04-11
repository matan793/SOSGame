public class SOSBitboard {
    private long[] sBoard; // Bitboards for "S"
    private long[] oBoard; // Bitboards for "O"
    private final int size;

    public SOSBitboard(int size) {
        this.size = size;
        int arraySize = (int) Math.ceil(size * size / 64.0);
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

    // Additional methods as needed...
}