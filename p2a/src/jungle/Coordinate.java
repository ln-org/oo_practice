package jungle;

/**
 * 表示丛林游戏棋盘上的一个坐标。
 * 每个坐标由行和列索引组成，用于在游戏棋盘上定位方格。
 */
public class Coordinate {
    private static final int HASH_MULTIPLIER = 10;

    private int rowIndex;
    private int colIndex;

    /**
     * 使用行和列索引构造一个坐标。
     * 
     * @param row 坐标的行索引
     * @param col 坐标的列索引
     */
    public Coordinate(int row, int col) {
        this.rowIndex = row;
        this.colIndex = col;
    }

    /**
     * 获取坐标的行索引。
     * 
     * @return 行索引
     */
    public int row() {
        return rowIndex;
    }

    /**
     * 获取坐标的列索引。
     * 
     * @return 列索引
     */
    public int col() {
        return colIndex;
    }

    /**
     * 检查此坐标是否与另一个对象相等。
     * 如果两个坐标具有相同的行和列索引，则认为它们相等。
     * 
     * @param obj 要比较的对象
     * @return 如果obj是一个坐标，且具有相同的行和列索引，则返回true，
     *         否则返回false
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
     * 根据此坐标的行和列索引生成哈希码。
     * 
     * @return 此坐标的哈希码
     */
    @Override
    public int hashCode() {
        return row() * HASH_MULTIPLIER + col();
    }

    /**
     * 返回此坐标的字符串表示。
     * 
     * @return 格式为"row: <row>, col: <col>"的字符串。
     */
    @Override
    public String toString() {
        return String.format("row: %d, col: %d", row(), col());
    }
}
