package jungle.squares;

/**
 * 表示丛林游戏棋盘上的水方格。
 * 水方格是一种特殊类型，只有老鼠可以进入，
 * 某些特定棋子可以跳跃。
 */
public class WaterSquare extends Square {

    /**
     * 构造一个没有拥有者的水方格。
     */
    public WaterSquare() {
        super(null);
    }

    /**
     * 指示此方格是水方格。
     * 
     * @return true，因为此方格是水方格
     */
    @Override
    public boolean isWater() {
        return true;
    }
}
