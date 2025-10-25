package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * 表示丛林游戏中的老鼠棋子。
 * 老鼠是最弱的棋子（等级1）但具有游泳能力。
 */
public class Rat extends Piece {

    /**
     * 使用拥有者和方格构造一个老鼠。
     * 老鼠的等级为1。
     * 
     * @param owner     拥有这个老鼠的玩家
     * @param square    这个老鼠占据的方格
     */
    public Rat(Player owner, Square square) {
        super(owner, square, 1);
    }

    /**
     * 指示老鼠可以游泳。
     * 
     * @return true
     */
    @Override
    public boolean canSwim() {
        return true;
    }
}
