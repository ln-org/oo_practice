package jungle.squares;

import jungle.Player;

/**
 * 表示丛林游戏棋盘上的陷阱方格。
 * 陷阱会削弱对手棋子。
 */
public class Trap extends Square {

    /**
     * 使用拥有者构造一个陷阱。
     *
     * @param owner  拥有这个陷阱的玩家
     */
    public Trap(Player owner) {
        super(owner);
    }

    /**
     * 指示此方格是陷阱。
     * 
     * @return true，因为此方格是陷阱
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
