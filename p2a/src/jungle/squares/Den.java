package jungle.squares;

import jungle.Player;

/**
 * 表示丛林游戏棋盘上的兽穴方格。
 * 占领对手的兽穴将获得胜利。
 */
public class Den extends Square {
    /**
     * 使用拥有者构造一个兽穴。
     * 
     * @param owner 拥有这个兽穴的玩家
     */
    public Den(Player owner) {
        super(owner);
    }

    /**
     * 指示此方格是兽穴。
     * 
     * @return true
     */
    @Override
    public boolean isDen() {
        return true;
    }
}
