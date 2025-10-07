import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jungle.Player;
import jungle.pieces.Lion;
import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Square;
import jungle.squares.Trap;

/**
 * Piece 类及其子类的测试用例。
 */
public class PieceTest {

    private Player michael, oz;
    private Square land, michaelsDen, ozsTrap;
    private Piece michaelsWolf, michaelsLion, michaelsElephant, ozsRat;

    @BeforeEach
    public void setup() {
        // 玩家
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);

        // 方格
        land = new PlainSquare();
        michaelsDen = new Den(michael);
        ozsTrap = new Trap(oz);

        // 棋子
        michaelsWolf = new Piece(michael, land, 4);
        michaelsLion = new Lion(michael, ozsTrap);
        ozsRat = new Rat(oz, land);
        michaelsElephant = new Piece(michael, land, 8);
    }

    @Test
    // 测试棋子属于正确的玩家
    public void testOwner() {
        assertTrue(michaelsWolf.isOwnedBy(michael));
    }

    @Test
    // 测试棋子不属于错误的玩家
    public void testNonOwner() {
        assertFalse(michaelsWolf.isOwnedBy(oz));
    }

    @Test
    // 测试棋子的强度
    public void testStrength() {
        assertEquals(4, michaelsWolf.getStrength());
    }

    @Test
    // 测试陷阱中棋子的强度（应为 0）
    public void testTrappedStrength() {
        assertEquals(0, michaelsLion.getStrength());
    }

    @Test
    // 测试不能游泳的棋子
    public void testCanSwimFalse() {
        assertFalse(michaelsWolf.canSwim());
    }

    @Test
    // 测试能游泳的棋子（老鼠）
    public void testCanSwimTrue() {
        assertTrue(ozsRat.canSwim());
    }

    @Test
    // 测试棋子能否跳跃（狮子）
    public void testCanLeap() {
        assertTrue(michaelsLion.canLeapVertically());
    }

    @Test
    // 测试棋子能击败对手
    public void testCanDefeat() {
        assertTrue(michaelsWolf.canDefeat(ozsRat));
    }

    @Test
    // 测试棋子不能击败对手
    public void testCannotDefeat() {
        assertFalse(ozsRat.canDefeat(michaelsWolf));
    }

    @Test
    // 测试击败陷阱中的对手
    public void testDefeatTrapped() {
        assertTrue(ozsRat.canDefeat(michaelsLion));
    }

    @Test
    // 测试老鼠击败大象（特殊规则）
    public void testRatDefeatElephant() {
        assertTrue(ozsRat.canDefeat(michaelsElephant));
    }

    @Test
    // 测试棋子移动
    public void testMove() {
        michaelsLion.move(ozsTrap);
        assertEquals(0, michaelsLion.getStrength());
    }

    @Test
    // 测试移动到对手兽穴获胜
    public void testMoveToDen() {
        ozsRat.move(michaelsDen);
        assertTrue(oz.hasCapturedDen());
    }

    @Test
    // 测试移动到自己兽穴不获胜
    public void testMoveToOwnDen() {
        michaelsWolf.move(michaelsDen);
        assertFalse(michael.hasCapturedDen());
    }

    @Test
    // 测试玩家拥有棋子
    public void testHasPieces() {
        assertTrue(oz.hasPieces());
    }

    @Test
    // 测试玩家失去所有棋子
    public void testLoseAllPieces() {
        ozsRat.beCaptured();
        assertFalse(oz.hasPieces());
    }
}
