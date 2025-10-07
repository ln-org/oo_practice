import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jungle.Game;
import jungle.IllegalMoveException;
import jungle.Player;
import jungle.pieces.Piece;

/** Game 类的测试用例，使用简单的设置，只有少量棋子。 */
public class SimpleGameTest {

    private Player michael, oz;
    private Game game;

    @BeforeEach
    // 设置测试游戏和玩家
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
        game = new Game(michael, oz);
    }

    @Test
    // 测试游戏板高度
    public void testHeight() {
        assertEquals(9, Game.HEIGHT);
    }

    @Test
    // 测试游戏板宽度
    public void testWidth() {
        assertEquals(7, Game.WIDTH);
    }

    @Test
    // 测试游戏对象存在
    public void testExists() {
        assertNotNull(game);
    }

    @Test
    // 测试方格没有棋子
    public void testNoPiece() {
        assertNull(game.getPiece(1, 0));
    }

    @Test
    // 测试添加棋子
    public void testAddPiece() {
        game.addPiece(1, 0, 3, 0); // Player 0 的狗在 (1,0)
        assertNotNull(game.getPiece(1, 0));
    }

    @Test
    // 测试在已占据方格添加棋子（替换）
    public void testAddPieceOccupied() {
        game.addPiece(1, 0, 3, 0); // Player 0 的狗在 (1,0)
        game.addPiece(1, 0, 2, 0); // Player 0 的猫替换它
        assertEquals(2, game.getPiece(1, 0).getStrength());
    }

    @Test
    // 测试获取错误方格抛出异常
    public void testGetBadSquare() {
        // 调用 game.getSquare(0, 9) 抛出 IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class,
                     () -> game.getSquare(0, 9));
    }

    @Test
    // 测试获取负数坐标方格抛出异常
    public void testGetBadSquareNegative() {
        assertThrows(IndexOutOfBoundsException.class,
                     () -> game.getSquare(-1, 2));
    }

    @Test
    // 测试水域方格
    public void testWater() {
        assertTrue(game.getSquare(4, 5).isWater());
    }

    @Test
    // 测试非水域方格
    public void testNotWater() {
        assertFalse(game.getSquare(4, 6).isWater());
    }

    @Test
    // 测试兽穴方格
    public void testDen() {
        assertTrue(game.getSquare(8, 3).isDen());
    }

    @Test
    // 测试兽穴属于正确的玩家
    public void testDenOwner() {
        assertTrue(game.getSquare(8, 3).isOwnedBy(oz));
    }

    @Test
    // 测试陷阱方格
    public void testTrap() {
        assertTrue(game.getSquare(0, 2).isTrap());
    }

    @Test
    // 测试获取玩家
    public void testGetPlayer() {
        assertEquals(oz, game.getPlayer(1));
    }

    @Test
    // 测试获取错误编号的玩家抛出异常
    public void testGetPlayerBad() {
        assertThrows(IllegalArgumentException.class,
                     () -> game.getPlayer(3));
    }

    @Test
    // 测试空方格没有合法移动
    public void testNoLegalMoves() {
        assertTrue(game.getLegalMoves(0, 0).isEmpty());
    }

    @Test
    // 测试从错误方格获取合法移动抛出异常
    public void testLegalMovesBadSquare() {
        assertThrows(IndexOutOfBoundsException.class,
                     () -> game.getLegalMoves(-2, 7));
    }

    @Test
    // 测试从错误方格移动抛出异常
    public void testMoveFromBadSquare() {
        assertThrows(IndexOutOfBoundsException.class,
                     () -> game.move(-1, 7, 0, 7));
    }

    @Test
    // 测试进行合法移动
    public void testMoveLegal() {
        game.addPiece(2, 4, 4, 0); // 放置 michael 的狼
        Piece wolf = game.getPiece(2, 4);
        game.addPiece(6, 2, 5, 1); // 放置 oz 的豹
        game.move(2, 4, 2, 3); // michael 的狼向右移动
        assertEquals(wolf, game.getPiece(2, 3));
        assertNull(game.getPiece(2, 4));
    }

    @Test
    // 测试进入对手兽穴获胜游戏
    public void testEnterDenWinGame() {
        game.addPiece(8, 2, 2, 0); // michael 的猫靠近 oz 的兽穴
        game.addPiece(6, 2, 4, 1); // oz 的狼

        game.move(8, 2, 8, 3); // michael 的猫进入 oz 的兽穴
        assertEquals(michael, game.getWinner());
        assertTrue(game.isGameOver());
    }

    @Test
    // 测试没有棋子输掉游戏
    public void testNoPiecesLoseGame() {
        game.addPiece(2, 4, 4, 0); // 放置 michael 的狼，oz 没有棋子
        assertEquals(michael, game.getWinner());
        assertTrue(game.isGameOver());
    }

    @Test
    // 测试老虎横向跳跃
    public void testTigerHorizontalJump() {
        game.addPiece(3, 0, 6, 0); // 放置 michael 的老虎紧邻水域
        game.addPiece(7, 1, 2, 1); // oz 也有一个棋子
        assertEquals(3, game.getLegalMoves(3, 0).size()); // 老虎可以横向跳跃水域
        game.move(3, 0, 3, 3);  // 跳跃且不抛出异常
    }

    @Test
    // 测试老虎无法纵向跳跃
    public void testTigerVerticalJump() {
        game.addPiece(2, 1, 6, 0); // 放置 michael 的老虎在水域后面
        game.addPiece(7, 1, 2, 1); // oz 也有一个棋子
        assertEquals(3, game.getLegalMoves(2, 1).size()); // 老虎不能纵向跳跃水域
        assertThrows(IllegalMoveException.class,
                     () -> game.move(2, 1, 6, 1));
    }

    @Test
    // 测试狮子纵向跳跃
    public void testLionVerticalJump() {
        game.addPiece(2, 1, 7, 0); // 放置 michael 的狮子在水域后面
        game.addPiece(7, 1, 2, 1); // oz 也有一个棋子
        assertEquals(4, game.getLegalMoves(2, 1).size()); // 狮子可以纵向跳跃水域
        game.move(2, 1, 6, 1);
    }
}
