package jungle.squares;

import jungle.Player;

/**
 * 表示7x9丛林游戏棋盘的一个方格。
 * 每个方格可以有一个拥有者，并且可以表示特定类型，
 * 如兽穴、陷阱、水或普通方格。
 */
public abstract class Square {
    private Player owner;

    /**
     * 使用拥有者构造一个方格。
     * 
     * @param owner 拥有此方格的玩家，如果没有拥有者可以为null
     */
    public Square(Player owner) {
        this.owner = owner;
    }

    /**
     * 检查此方格是否属于指定玩家。
     * 
     * @param player 要检查所有权的玩家
     * @return 如果玩家拥有此方格则返回true，否则返回false
     */
    public boolean isOwnedBy(Player player) {
        return player.equals(this.owner);
    }

    /**
     * 指示此方格是否为水方格。
     * 默认返回false，由{@code WaterSquare}重写。
     * 
     * @return 如果此方格是水方格则返回true，否则返回false
     */
    public boolean isWater() {
        return false;
    }

    /**
     * 指示此方格是否为兽穴。
     * 默认返回false，由{@code Den}重写。
     *
     * @return 如果此方格是兽穴则返回true，否则返回false
     */
    public boolean isDen() {
        return false;
    }

    /**
     * 指示此方格是否为陷阱。
     * 默认返回false，由{@code Trap}重写。
     *
     * @return 如果此方格是陷阱则返回true，否则返回false
     */
    public boolean isTrap() {
        return false;
    }
}
