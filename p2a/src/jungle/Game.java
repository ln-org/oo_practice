package jungle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.pieces.Tiger;
import jungle.pieces.Lion;
import jungle.squares.Square;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Trap;
import jungle.squares.WaterSquare;

/**
 * 表示丛林游戏的主要游戏逻辑和状态。
 * 管理游戏棋盘、玩家、棋子、回合和游戏规则。
 */
public class Game {
    /** 棋盘高度（行数）。 */
    public static final int HEIGHT = 9;

    /** 棋盘宽度（列数）。 */
    public static final int WIDTH = 7;

    /** 有水方格的行。 */
    public static final int[] WATER_ROWS = {3, 4, 5};

    /** 有水方格的列。 */
    public static final int[] WATER_COLS = {1, 2, 4, 5};

    /** 兽穴的列索引。 */
    public static final int DEN_COL = 3;


    private int currentTurn;
    private Player p0, p1;
    private Player[] players = new Player[2];
    private Square[][] squares = new Square[HEIGHT][WIDTH]; // 游戏棋盘
    private HashMap<Square, Piece> squareToPiece
        = new HashMap<Square, Piece>();

    /**
     * 使用两个玩家构造一个游戏实例。
     * 用棋盘方格初始化游戏棋盘，并将回合设置为第一个玩家。
     * 
     * @param p0 第一个玩家
     * @param p1 第二个玩家
     */
    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
        players[0] = p0;
        players[1] = p1;

        this.initializeBoardSquares();
        this.currentTurn = 0;
    }

    /**
     * 通过在相应位置设置每种方格类型（普通、水、兽穴、陷阱）来初始化游戏棋盘。
     * 棋盘是一个9x7的网格，其中某些方格被指定为水方格，
     * 每个玩家的兽穴和陷阱方格被放置在特定位置。
     */
    private void initializeBoardSquares() {
        // 初始时将所有方格设置为普通方格
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                squares[row][col] = new PlainSquare();
            }
        }

        // 基于预定义的行和列设置水方格
        for (int row : WATER_ROWS) {
            for (int col : WATER_COLS) {
                squares[row][col] = new WaterSquare();
            }
        }

        // 为每个玩家设置兽穴和陷阱
        // 设置p0的兽穴和陷阱
        int p0DenRow = 0;
        squares[p0DenRow][DEN_COL] = new Den(p0);
        squares[p0DenRow][DEN_COL - 1] = new Trap(p0);
        squares[p0DenRow][DEN_COL + 1] = new Trap(p0);
        squares[p0DenRow + 1][DEN_COL] = new Trap(p0);

        // 设置p1的兽穴和陷阱
        int p1DenRow = 8;
        squares[p1DenRow][DEN_COL] = new Den(p1);
        squares[p1DenRow][DEN_COL - 1] = new Trap(p1);
        squares[p1DenRow][DEN_COL + 1] = new Trap(p1);
        squares[p1DenRow - 1][DEN_COL] = new Trap(p1);
    }

    /**
     * 根据初始位置和等级为两个玩家添加起始棋子。
     */
    public void addStartingPieces() {
        int[][] initialPieceConfigs = {
                // {行, 列, 等级, 玩家编号}
                // 玩家0
                {2, 0, 1, 0},
                {1, 5, 2, 0},
                {1, 1, 3, 0},
                {2, 4, 4, 0},
                {2, 2, 5, 0},
                {0, 6, 6, 0},
                {0, 0, 7, 0},
                {2, 6, 8, 0},
                // 玩家1
                {6, 6, 1, 1},
                {7, 1, 2, 1},
                {7, 5, 3, 1},
                {6, 2, 4, 1},
                {6, 4, 5, 1},
                {8, 0, 6, 1},
                {8, 6, 7, 1},
                {6, 0, 8, 1},
        };

        for (int[] pieceConig : initialPieceConfigs) {
            int row = pieceConig[0];
            int col = pieceConig[1];
            int rank = pieceConig[2];
            int playerNumber = pieceConig[3];

            addPiece(row, col, rank, playerNumber);
        }
    }

    /**
     * 在棋盘上的给定位置添加一个棋子。
     * 
     * @param row          游戏棋盘上方格的行坐标。
     * @param col          游戏棋盘上方格的列坐标。
     * @param rank         棋子的等级
     * @param playerNumber 拥有该棋子的玩家编号
     */
    public void addPiece(int row, int col, int rank, int playerNumber) {
        Player player = getPlayer(playerNumber);
        Square square = getSquare(row, col);

        Piece piece;
        if (rank == 1) {
            piece = new Rat(player, square);
        } else if (rank == 6) {
            piece = new Tiger(player, square);
        } else if (rank == 7) {
            piece = new Lion(player, square);
        } else {
            piece = new Piece(player, square, rank);
        }

        // 放入squareToPiece映射中
        squareToPiece.put(getSquare(row, col), piece);
    }

    /**
     * 获取棋盘上指定位置的棋子。
     * 
     * @param row 游戏棋盘上的行坐标。
     * @param col 游戏棋盘上的列坐标。
     * @return 给定坐标处的棋子，如果不存在则返回null
     */
    public Piece getPiece(int row, int col) {
        Square square = getSquare(row, col);
        return squareToPiece.get(square);
    }

    /**
     * 将棋子从一个位置移动到另一个位置。
     * 
     * @param fromRow 棋子当前位置的行坐标
     * @param fromCol 棋子当前位置的列坐标
     * @param toRow   目标位置的行坐标
     * @param toCol   目标位置的列坐标
     * @throws IllegalMoveException 如果目标坐标对该棋子不合法
     */
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        // 检查目标位置是否有效
        if (
            !getLegalMoves(fromRow, fromCol)
                .contains(new Coordinate(toRow, toCol))
        ) {
            throw new IllegalMoveException(
                String.format("(%d, %d) is not legal move", toRow, toCol)
            );
        }

        Piece piece = getPiece(fromRow, fromCol);
        Piece targetPiece = getPiece(toRow, toCol);
        Square sourceSquare = getSquare(fromRow, fromCol);
        Square targetSquare = getSquare(toRow, toCol);

        if (targetPiece != null) {
            targetPiece.beCaptured();
        }

        // 移动棋子
        piece.move(getSquare(toRow, toCol));

        // 更新方格到棋子的映射
        squareToPiece.remove(sourceSquare, piece);
        squareToPiece.put(targetSquare, piece);
        // 轮到另一个玩家
        nextTurn();
    }

    /**
     * 根据玩家编号获取玩家。
     * 
     * @param playerNumber 玩家编号（0或1）
     * @return 具有指定玩家编号的玩家
     * @throws IllegalArgumentException 如果玩家编号无效
     */
    public Player getPlayer(int playerNumber) {
        // 检查玩家编号有效性，有效编号能成功创建玩家
        try {
            Player player = new Player("checkPlayerNumber", playerNumber);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return playerNumber == p0.getPlayerNumber() ? p0 : p1;
    }

    /**
     * 确定游戏的获胜者。
     * 如果玩家占领了对手的兽穴或者对手没有剩余棋子，
     * 该玩家就获胜。
     * 
     * @return 获胜的玩家，如果还没有获胜者则返回null
     */
    public Player getWinner() {
        if (
            p0.hasCapturedDen() // p0占领了p1的兽穴
            || !p1.hasPieces()  // 或者p1没有剩余棋子
        ) {
            return p0;          // p0获胜
        }

        if (
            p1.hasCapturedDen() // p1占领了p0的兽穴
            || !p0.hasPieces()  // 或者p0没有剩余棋子
        ) {
            return p1;          // p1获胜
        }

        return null;
    }

    /**
     * 检查游戏是否结束。
     * 
     * @return 如果有获胜者则返回true，否则返回false
     */
    public boolean isGameOver() {
        return getWinner() != null;
    }

    /**
     * 获取游戏棋盘上指定坐标的方格。
     * 
     * @param row 棋盘上的行坐标。
     * @param col 棋盘上的列坐标。
     * @return 指定坐标处的方格
     * @throws IndexOutOfBoundsException 如果坐标超出游戏棋盘范围
     */
    public Square getSquare(int row, int col) {
        if (
            (row < 0 || row > HEIGHT - 1)   // 超出高度
            || (col < 0 || col > WIDTH - 1) // 超出宽度
        ) {
            throw new IndexOutOfBoundsException(
                String.format("Coordinate exceed board bounds: (%d, %d)", row, col)
            );
        }

        return squares[row][col];
    }

    /**
     * 获取指定坐标处棋子的合法移动。
     * 
     * @param row 当前行坐标
     * @param col 当前列坐标
     * @return 棋子可以移动的合法坐标列表
     */
    public List<Coordinate> getLegalMoves(int row, int col) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();

        Player thisTurnPlayer = getPlayer(getTrun() % 2);
        Piece selectedPiece = getPiece(row, col);

        if (
            selectedPiece == null   // 此坐标处没有棋子
            // 或者棋子不属于本回合可移动的玩家
            || !selectedPiece.isOwnedBy(thisTurnPlayer)
            || isGameOver()         // 或者游戏已结束
        ) {
            return coordinates;       // 返回空列表
        }

        // 4个潜在的移动方向
        Coordinate[] directions = {
            new Coordinate(-1, 0),  // 向上
            new Coordinate(1, 0),  // 向下
            new Coordinate(0, -1),  // 向左
            new Coordinate(0, 1),  // 向右
        };

        // 探索在4个方向中每个方向可以移动到的方格
        for (Coordinate direction : directions) {
            Coordinate coordinate = exploreDirection(row,
                                                     col,
                                                     direction.row(),
                                                     direction.col());

            // 如果找到有效方格，添加到列表中
            if (coordinate != null) {
                coordinates.add(coordinate);
            }
        }

        // 返回列表中所有有效方格
        return coordinates;
    }

    /**
     * 从起始位置探索指定方向，查找是否有可以移动到的方格。
     * 处理棋子可以直接移动到相邻方格、游过水或跳跃越过水
     * （如果棋子具有该能力）的情况。
     * 
     * @param row           起始行坐标
     * @param col           起始列坐标
     * @param rowDirection  行方向（例如，-1表示向上，1表示向下）
     * @param colDirection  列方向（例如，-1表示向左，1表示向右）
     * @return 如果移动有效则返回目标坐标，如果不允许则返回null
     * @throws IllegalArgumentException 如果方向无效
     */
    private Coordinate exploreDirection(int row,
                                        int col,
                                        int rowDirection,
                                        int colDirection) {

        // 探索（移动）方向只能是水平或垂直
        if (
            !((rowDirection == -1 || rowDirection == 1)  && colDirection == 0)
            && !(rowDirection == 0 && (colDirection == -1 || colDirection == 1))
        ) {
            throw new IllegalArgumentException(
                "Row and Col Direction, one should be -1 or 1,"
                + " the other should be 0"
            );
        }

        try {
            // 当前探索的方格
            Square exploreSqure = getSquare(row + rowDirection,
                                            col + colDirection);

            Piece selectedPiece = getPiece(row, col);
            Piece distinationPiece = getPiece(row + rowDirection,
                                              col + colDirection);

            if (
                // 探索可以直接移动到的相邻方格
                (
                    // 方格不是水或棋子可以游泳
                    !exploreSqure.isWater() || selectedPiece.canSwim()
                ) && (
                    // 有棋子，但可以击败
                    distinationPiece != null
                    && selectedPiece.canDefeat(distinationPiece)
                    // 或者没有棋子
                    || distinationPiece == null
                )
            ) {
                return new Coordinate(row + rowDirection, col + colDirection);
            } else if (
                // 探索可以跳跃到的方格
                (
                    // 相邻方格是水，且没有棋子
                    exploreSqure.isWater() && distinationPiece == null
                ) && (
                    // 选中的棋子可以垂直跳跃
                    // 且当前探索垂直方向
                    selectedPiece.canLeapVertically() && rowDirection != 0
                    // 或者选中的棋子可以水平跳跃
                    // 且当前探索水平方向
                    || selectedPiece.canLeapHorizontally() && colDirection != 0
                )
            ) {
                int step = 2;
                while (true) {
                    exploreSqure = getSquare(row + rowDirection * step,
                                             col + colDirection * step);
                    distinationPiece = getPiece(row + rowDirection * step,
                                                col + colDirection * step);

                    // 停止探索，如果棋子占据了
                    // 中间的水方格则无法跳跃
                    if (
                        exploreSqure.isWater()
                        && distinationPiece != null
                    ) {
                        break;
                    }

                    // 返回方格不是水，没有棋子或可以被击败
                    if (
                        !exploreSqure.isWater()
                        && (
                            distinationPiece == null
                            || selectedPiece.canDefeat(distinationPiece)
                        )
                    ) {
                        return new Coordinate(row + rowDirection * step,
                                              col + colDirection * step);
                    }

                    // 探索下一个方格
                    step++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Exploring: " + e.getMessage());
        }
        return null;
    }

    private int getTrun() {
        return this.currentTurn;
    }

    private void nextTurn() {
        this.currentTurn++;
    }
}
