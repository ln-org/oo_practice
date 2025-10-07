import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jungle.Game;
import jungle.IllegalMoveException;
import jungle.Player;

/** Game 类的测试用例，包含所有棋子。 */
public class FullGameTest {

    private Player michael, oz;
    private Game game;

    @BeforeEach
    // 设置包含所有棋子的测试游戏
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
        game = new Game(michael, oz);
        game.addStartingPieces();
    }

    @Test
    // 测试放置的棋子有正确的强度
    public void testPiecePlacedStrength() {
        assertEquals(4, game.getPiece(2, 4).getStrength());
    }

    @Test
    // 测试放置的棋子有正确的所有者
    public void testPiecePlacedOwner() {
        assertTrue(game.getPiece(2, 4).isOwnedBy(michael));
    }

    @Test
    // 测试空方格没有棋子
    public void testEmptySquare() {
        assertNull(game.getPiece(2, 3));
    }

    @Test
    // 测试放置的老鼠能游泳
    public void testPlacedRat() {
        assertTrue(game.getPiece(2, 0).canSwim());
    }

    @Test
    // 测试放置的狮子能纵向跳跃
    public void testPlacedLion() {
        assertTrue(game.getPiece(8, 6).canLeapVertically());
    }

    @Test
    // 测试放置的狗有正确的强度
    public void testPlacedDog() {
        assertEquals(3, game.getPiece(1, 1).getStrength());
    }

    @Test
    // 测试游戏开始时没有赢家
    public void testNoWinnerAtStart() {
        assertNull(game.getWinner());
    }

    @ParameterizedTest
    @CsvSource({
        "2, 4, 3, 4", // 狼试图进入水域
        "2, 6, 2, 7", // 大象试图走出棋盘
        "2, 0, 4, 0", // 老鼠试图一次移动两格
        "2, 2, 3, 3", // 豹试图对角线移动
        "6, 4, 6, 5", // P1 试图在 P0 的回合移动
    })
    // 测试各种非法移动会抛出异常
    public void testIllegalMoveException(int fromRow, int fromCol, int toRow, int toCol) {
        assertThrows(IllegalMoveException.class,
                     () -> game.move(fromRow, fromCol, toRow, toCol));
    }

    @Test
    // 测试进行合法移动
    public void testLegalMove() {
        game.move(1, 1, 1, 2);
        assertNull(game.getPiece(1, 1));
        assertNotNull(game.getPiece(1, 2));
    }

    /** 从起始位置，棋子可以进行多少合法移动？ */
    @ParameterizedTest
    @CsvSource({
        "0, 0, 2", // P0 的狮子可以到达两个位置（它在角落）
        "1, 1, 4", // P0 的狗在所有 4 个方向都畅通无阻
        "2, 6, 3", // P0 的大象在边缘：可以到达 3 个位置
        "2, 4, 3", // P0 的狼不能进入水域
        "7, 1, 0", // P1 的猫不能在 P0 的回合移动
        "2, 1, 0", // 方格 (2, 1) 上没有棋子
    })
    // 测试从起始位置的合法移动数量
    public void testGetLegalMovesStart(int row, int col, int expectedNumber) {
        assertEquals(expectedNumber, game.getLegalMoves(row, col).size());
    }

    /**
     * 一个完整的游戏，沿途测试各种情况。
     *
     * 通常每个测试一个断言更好，但至少有一个这种形式的测试似乎是合理的。
     */
    @Test
    public void testFullGame() {
        game.move(2, 0, 3, 0); // michael 前进他的老鼠
        game.move(6, 0, 5, 0); // oz 前进他的大象

        game.move(3, 0, 3, 1); // michael 的老鼠进入水域
        game.move(5, 0, 4, 0); // oz 进一步前进他的大象

        game.move(3, 1, 4, 1); // michael 的老鼠在棋盘上继续游动
        assertEquals(2, game.getLegalMoves(4, 0).size()); // 大象不能捕获水中的老鼠
        game.move(8, 6, 7, 6); // oz 的狮子前进

        game.move(4, 1, 4, 0); // michael 的老鼠捕获 oz 的大象！
        assertEquals(1, game.getLegalMoves(7, 6).size()); // 狮子不能捕获己方棋子
        game.move(6, 6, 5, 6); // oz 的老鼠向前移动

        game.move(4, 0, 4, 1); // michael 的老鼠重新进入水域
        game.move(7, 6, 6, 6); // oz 的狮子前进
        assertNull(game.getPiece(4, 0));
        assertEquals(1, game.getPiece(4, 1).getStrength());
        assertEquals(7, game.getPiece(6, 6).getStrength());

        game.move(0, 0, 0, 1); // michael 的狮子横向移动
        game.move(5, 6, 5, 5); // oz 的老鼠进入水域

        game.move(4, 1, 4, 2); // michael 横向移动他的老鼠（仍在水中）
        game.move(6, 6, 6, 5); // oz 的狮子接近水域
        //assertEquals(2, game.getLegalMoves(6, 1).size()); // 老虎现在可以跳过水域

        game.move(0, 1, 0, 2); // michael 将他的狮子移动到自己的陷阱上
        assertEquals(7, game.getPiece(0, 2).getStrength()); // michael 的棋子不受自己陷阱的影响
        assertEquals(1, game.getLegalMoves(6, 5).size()); // oz 的狮子由于老鼠的存在无法跳过水域
        game.move(5, 5, 5, 4); // oz 将他的老鼠移开

        game.move(2, 4, 2, 5); // michael 将他的狼移入危险境地
        game.move(6, 5, 2, 5); // oz 的狮子跳跃并吃掉 michael 的狼！
        assertTrue(game.getPiece(2, 5).isOwnedBy(oz));

        assertEquals(3, game.getLegalMoves(1, 5).size()); // michael 的猫不能吃 oz 的老虎
        game.move(0, 6, 0, 5); // michael 横向移动他的老虎
        assertEquals(3, game.getLegalMoves(2, 5).size()); // oz 的狮子可以向后跳，但不能吃 michael 的大象
        game.move(2, 5, 1, 5); // oz 的狮子吃掉 michael 的猫（7 > 2）

        game.move(4, 2, 5, 2); // michael 前进他的老鼠
        game.move(1, 5, 1, 4); // oz 横向移动他的狮子

        assertEquals(3, game.getLegalMoves(5, 2).size()); // michael 的老鼠不能捕获狼
        game.move(5, 2, 5, 3); // michael 的老鼠离开水域
        game.move(1, 4, 0, 4); // oz 的狮子进入 michael 的陷阱

        assertEquals(3, game.getLegalMoves(0, 5).size()); // michael 的老虎可以吃掉被困的狮子
        game.move(5, 3, 5, 4); // michael 的老鼠改为吃掉 oz 的老鼠
        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
        game.move(0, 4, 0, 3); // oz 移动到 michael 的兽穴并获胜！

        assertTrue(game.isGameOver());
        assertEquals(oz, game.getWinner());
        assertTrue(game.getLegalMoves(1, 1).isEmpty()); // 游戏结束后没有合法移动
    }
}
