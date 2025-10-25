package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * 表示丛林游戏中的老虎棋子。
 * 老虎可以水平跳跃越过水方格。
 */
public class Tiger extends Piece {

    /**
     * 使用拥有者和方格构造一个老虎。
     * 老虎的等级为6。
     * 
     * @param owner     拥有这个老虎的玩家
     * @param square    这个老虎占据的方格
     */
    public Tiger(Player owner, Square square) {
        super(owner, square, 6);
    }

    /**
     * 指示老虎可以水平跳跃。
     * 
     * @return true
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }
}
