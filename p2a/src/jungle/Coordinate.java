package jungle;

/**
 * Represents a coordinate on the Jungle game board.
 * Each coordinate consists of a row and column index, which are
 * used to locate squares on the game board.
 */
public class Coordinate {
    private static final int HASH_MULTIPLIER = 10;

    private int rowIndex;
    private int colIndex;

    /**
     * Constructs a Coordinate with the row and column indexes.
     * 
     * @param row row index of the coordinate
     * @param col column index of the coordinate
     */
    public Coordinate(int row, int col) {
        this.rowIndex = row;
        this.colIndex = col;
    }

    /**
     * Gets the row index of coordinate.
     * 
     * @return row index
     */
    public int row() {
        return rowIndex;
    }

    /**
     * Gets the column index of coordinate.
     * 
     * @return column index
     */
    public int col() {
        return colIndex;
    }

    /**
     * Checks if this coordinate is equal to another object.
     * Two coordinates are considered equal if they have same
     * row and column index.
     * 
     * @param obj the object to compare with
     * @return true if obj is a Coordinate, and with same row
     *         and column indexes, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Coordinate compareCoordinate = (Coordinate) obj;

        return (
            this.row() == compareCoordinate.row()
            && this.col() == compareCoordinate.col()
        );
    }

    /**
     * Generates hash code for this coordinate based on its row
     * and column indexes.
     * 
     * @return hash code for this coordinate
     */
    @Override
    public int hashCode() {
        return row() * HASH_MULTIPLIER + col();
    }

    /**
     * Returns a string representation of this coordinate.
     * 
     * @return a string in the format "row: <row>, col: <col>".
     */
    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row(), col());
    }
}
