"""Coordinate 类的测试。"""

import pytest
from jungle import Coordinate


class TestCoordinate:
    """Coordinate 类的测试用例。"""
    
    def test_row(self):
        """测试从坐标获取行号。"""
        c = Coordinate(2, 3)
        assert c.row() == 2
    
    def test_col(self):
        """测试从坐标获取列号。"""
        c = Coordinate(2, 3)
        assert c.col() == 3
    
    # 完整性测试
    def test_coordinate_equality(self):
        """测试坐标相等性。"""
        coord1 = Coordinate(1, 2)
        coord2 = Coordinate(1, 2)
        coord3 = Coordinate(2, 1)
        
        assert coord1 == coord2
        assert coord1 != coord3
        assert coord2 != coord3
    
    def test_coordinate_equality_with_self(self):
        """测试坐标与自身相等。"""
        coord = Coordinate(5, 6)
        assert coord == coord
    
    def test_coordinate_equality_with_none(self):
        """测试坐标与 None 比较。"""
        coord = Coordinate(1, 2)
        assert coord != None
    
    def test_coordinate_equality_with_other_type(self):
        """测试坐标与其他类型比较。"""
        coord = Coordinate(1, 2)
        assert coord != "not a coordinate"
        assert coord != 123
        assert coord != [1, 2]
    
    def test_coordinate_hash(self):
        """测试坐标哈希值。"""
        coord1 = Coordinate(1, 2)
        coord2 = Coordinate(1, 2)
        coord3 = Coordinate(2, 1)
        
        # 相等的坐标应该有相同的哈希值
        assert hash(coord1) == hash(coord2)
        
        # 可以在集合中使用
        coord_set = {coord1, coord2, coord3}
        assert len(coord_set) == 2
    
    def test_coordinate_string(self):
        """测试坐标字符串表示。"""
        coord = Coordinate(3, 4)
        assert str(coord) == "row: 3, col: 4"
