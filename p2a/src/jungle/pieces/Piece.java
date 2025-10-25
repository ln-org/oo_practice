package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * 表示丛林游戏中的一个棋子。
 * 每个棋子都有一个拥有者，占据一个棋盘方格，具有1到8的等级，
 * 等级决定了它的力量（可能受到方格影响），
 * 一些棋子具有特殊能力，如游泳或跳跃。
 */
public class Piece {
    private Player owner = null;
    private Square square = null;
    private int rank = 0;

    /**
     * 使用拥有者、初始方格和等级构造一个棋子。
     * 
     * @param owner     拥有这个棋子的玩家
     * @param square    这个棋子占据的初始方格
     * @param rank      这个棋子的等级
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        owner.gainOnePiece();
    }

    /**
     * 检查此棋子是否属于指定玩家。
     * 
     * @param player 要检查所有权的玩家
     * @return  如果此棋子属于指定玩家则返回true，
     *          否则返回false
     */
    public boolean isOwnedBy(Player player) {
        return player.equals(this.owner);
    }

    /**
     * 获取此棋子的力量。
     * 通常等于等级，但如果在对手的陷阱中则降为0。
     * 
     * @return 棋子的力量，在对手陷阱中时为0
     */
    public int getStrength() {
        // 如果在陷阱中
        if (
            this.square.isTrap()
            && !this.square.isOwnedBy(this.owner)
        ) {
            return 0;
        }

        return this.rank;
    }

    /**
     * 指示此棋子是否可以游泳。
     * 
     * @return 如果棋子可以游泳则返回true，否则返回false
     */
    public boolean canSwim() {
        return false;
    }

    /**
     * 指示此棋子是否可以水平跳跃越过水。
     * 
     * @return 如果棋子可以水平跳跃则返回true，否则返回false
     */
    public boolean canLeapHorizontally() {
        return false;
    }

    /**
     * 指示此棋子是否可以垂直跳跃越过水。
     * 
     * @return 如果棋子可以垂直跳跃则返回true，否则返回false
     */
    public boolean canLeapVertically() {
        return false;
    }

    /**
     * 将棋子移动到新方格。
     * 如果移动到对手的兽穴，则占领该兽穴。
     * 
     * @param toSquare 要移动到的方格
     */
    public void move(Square toSquare) {
        this.square = toSquare;

        if (
            this.square.isDen()
            && !this.square.isOwnedBy(owner)
        ) {
            this.owner.captureDen();
        }
    }

    /**
     * 检查此棋子是否可以击败目标棋子。
     * 如果棋子有更高或相等的力量，或者是老鼠攻击大象，
     * 棋子就可以击败另一个棋子。
     * 
     * @param target 要检查击败的棋子
     * @return 如果棋子可以击败目标则返回true，否则返回false
     */
    public boolean canDefeat(Piece target) {
        if (
            !target.isOwnedBy(this.owner)
            && (
                this.getStrength() >= target.getStrength()
                || this.rank == 1 && target.rank ==  8 // 老鼠1攻击大象8
            )
        ) {
            return true;
        }

        return false;
    }

    /**
     * 通过将其方格设置为null、移除其所有权并减少拥有者的棋子数量
     * 来捕获此棋子。
     */
    public void beCaptured() {
        this.square = null;
        this.owner.loseOnePiece();
        this.owner = null;
    }
}
