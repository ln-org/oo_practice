import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jungle.Player;

/**
 * Player 类的测试用例。
 */
public class PlayerTest {

    private Player michael;
    private Player oz;

    @BeforeEach
    // 设置测试玩家
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
    }

    @Test
    // 测试玩家对象存在
    public void testExists() {
        assertNotNull(michael);
    }

    @Test
    // 测试获取玩家姓名
    public void testGetName() {
        assertEquals("Michael", michael.getName());
    }

    @Test
    // 测试获取玩家编号
    public void testGetPlayerNumber() {
        assertEquals(0, michael.getPlayerNumber());
        assertEquals(1, oz.getPlayerNumber());
    }

    @Test
    // 测试玩家初始未占领兽穴
    public void testHasCapturedDenFalse() {
        assertFalse(michael.hasCapturedDen());
    }

    @Test
    // 测试玩家占领兽穴后状态
    public void testHasCapturedDenTrue() {
        michael.captureDen();
        assertTrue(michael.hasCapturedDen());
    }

    @Test
    // 测试玩家初始没有棋子
    public void testHasPiecesFalse() {
        assertFalse(michael.hasPieces());
    }

    @Test
    // 测试获得和失去棋子
    public void testGainLosePieces() {
        michael.gainOnePiece();
        assertTrue(michael.hasPieces());
        michael.loseOnePiece();
        assertFalse(michael.hasPieces());
    }
}
