import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jungle.Player;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Square;
import jungle.squares.Trap;
import jungle.squares.WaterSquare;

/**
 * Square 类及其子类的测试用例。
 */
public class SquareTest {

    private static Square land, water, michaelsDen, ozsTrap;
    private static Player michael, oz;

    @BeforeAll
    // 设置测试数据：玩家和各种类型的方格
    public static void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
        land = new PlainSquare();
        water = new WaterSquare();
        michaelsDen = new Den(michael);
        ozsTrap = new Trap(oz);
    }

    @Test
    // 测试方格对象存在
    public void testExists() {
        assertNotNull(land);
    }

    @Test
    // 测试普通方格没有主人
    public void testNoOwner() {
        assertFalse(land.isOwnedBy(michael));
    }

    @Test
    // 测试兽穴有正确的主人
    public void testOwner() {
        assertTrue(michaelsDen.isOwnedBy(michael));
    }

    @Test
    // 测试兽穴不属于错误的玩家
    public void testWrongOwner() {
        assertFalse(michaelsDen.isOwnedBy(oz));
    }

    @Test
    // 测试水域方格的 isWater 方法
    public void testWater() {
        assertTrue(water.isWater());
    }

    @Test
    // 测试非水域方格的 isWater 方法
    public void testNotWater() {
        assertFalse(ozsTrap.isWater());
    }

    @Test
    // 测试兽穴方格的 isDen 方法
    public void testDen() {
        assertTrue(michaelsDen.isDen());
    }

    @Test
    // 测试陷阱方格的 isTrap 方法
    public void testTrap() {
        assertTrue(ozsTrap.isTrap());
    }
}
