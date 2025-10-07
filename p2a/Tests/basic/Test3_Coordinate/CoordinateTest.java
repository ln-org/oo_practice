import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jungle.Coordinate;

/**
 * Coordinate 类的测试用例。
 */
public class CoordinateTest {

    @Test
    // 测试获取坐标的行号
    public void testRow() {
        Coordinate c = new Coordinate(2, 3);
        assertEquals(2, c.row());
    }

    @Test
    // 测试获取坐标的列号
    public void testCol() {
        Coordinate c = new Coordinate(2, 3);
        assertEquals(3, c.col());
    }
}
