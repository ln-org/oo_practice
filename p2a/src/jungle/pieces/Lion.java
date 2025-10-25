package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * 表示丛林游戏中的狮子棋子。
 * 狮子可以水平和垂直跳跃越过水方格。
 */
public class Lion extends Piece {

    /**
     * 使用拥有者和方格构造一个狮子。
     * 狮子的等级为7。
     * 
     * @param owner     拥有这个狮子的玩家
     * @param square    这个狮子占据的方格
     */
    public Lion(Player owner, Square square) {
        super(owner, square, 7);
    }

    /**
     * 指示狮子可以水平跳跃。
     * 
     * @return true
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }

    /**
     * 指示狮子可以垂直跳跃。
     * 
     * @return true
     */
    @Override
    public boolean canLeapVertically() {
        return true;
    }
}
