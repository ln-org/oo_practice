package jungle;

/**
 * 表示丛林游戏中的一个玩家。
 * 
 * 玩家由玩家编号（0或1）标识。
 * 每个玩家都有剩余棋子的数量，并且可以占领对手的兽穴。
 */
public class Player {
    private String name = null;
    private int playerNumber = -1;
    private boolean isOpponentDenCaptured = false;
    private int numOfPieces = 0;

    /**
     * 使用指定的姓名和玩家编号构造一个玩家。
     * 
     * @param name          玩家姓名
     * @param playerNumber  唯一标识符，应该是0或1
     * @throws IllegalArgumentException 如果{@code playerNumber}不是0或1。
     * @throws IllegalArgumentException 如果{@code name}是{@code null}或
     *                                  空字符串。
     */
    public Player(String name, int playerNumber) {
        // 检查玩家编号有效性
        if (
            playerNumber != 0 && playerNumber != 1
        ) {
            throw new IllegalArgumentException(
                String.format(
                    "playerNumber should be 0 or 1, but: %d",
                    playerNumber
                )
            );
        }

        // 检查玩家姓名有效性
        if (
            name == null || name.isEmpty()
        ) {
            throw new IllegalArgumentException(
                "Player name cannot be null or empty."
            );
        }

        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * 获取玩家姓名。
     * 
     * @return 玩家姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取玩家编号。
     * 
     * @return 玩家编号
     */
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    /**
     * 占领对手的兽穴。将对手兽穴的状态
     * {@code isOpponentDenCaptured}设置为{@code true}。
     */
    public void captureDen() {
        this.isOpponentDenCaptured = true;
    }

    /**
     * 检查对手的兽穴是否已被占领。
     * 
     * @return 如果对手的兽穴被占领则返回true，否则返回false
     */
    public boolean hasCapturedDen() {
        return isOpponentDenCaptured;
    }

    /**
     * 检查玩家是否还有棋子。
     * 
     * @return 如果玩家还有棋子则返回true，否则返回false
     */
    public boolean hasPieces() {
        return numOfPieces > 0;
    }

    /**
     * 将玩家拥有的棋子数量增加一个。
     */
    public void gainOnePiece() {
        numOfPieces++;
    }

    /**
     * 将玩家拥有的棋子数量减少一个。
     * 
     * @throws IllegalStateException 如果玩家没有剩余棋子可以失去。
     */
    public void loseOnePiece() {
        if (numOfPieces > 0) {
            numOfPieces--;
        } else {
            throw new IllegalStateException("No pieces left to lose.");
        }
    }
}
