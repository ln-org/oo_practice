"""Square 类的测试。"""

import pytest
from jungle import Player
from jungle.squares import Square, PlainSquare, WaterSquare, Den, Trap


class TestSquare:
    """Square 类的测试用例。"""
    
    @pytest.fixture(scope="class")
    def setup(self):
        """设置测试用的方格对象。"""
        class Setup:
            def __init__(self):
                self.michael = Player("Michael", 0)
                self.oz = Player("Ozgur", 1)
                self.land = PlainSquare()
                self.water = WaterSquare()
                self.michaels_den = Den(self.michael)
                self.ozs_trap = Trap(self.oz)
        
        return Setup()
    
    def test_exists(self, setup):
        """测试方格对象存在。"""
        assert setup.land is not None
    
    def test_no_owner(self, setup):
        """测试普通方格没有所有者。"""
        assert not setup.land.is_owned_by(setup.michael)
    
    def test_owner(self, setup):
        """测试兽穴有正确的所有者。"""
        assert setup.michaels_den.is_owned_by(setup.michael)
    
    def test_wrong_owner(self, setup):
        """测试兽穴不属于错误的玩家。"""
        assert not setup.michaels_den.is_owned_by(setup.oz)
    
    def test_water(self, setup):
        """测试水域方格是水域。"""
        assert setup.water.is_water()
    
    def test_not_water(self, setup):
        """测试陷阱不是水域。"""
        assert not setup.ozs_trap.is_water()
    
    def test_den(self, setup):
        """测试兽穴是兽穴。"""
        assert setup.michaels_den.is_den()
    
    def test_trap(self, setup):
        """测试陷阱是陷阱。"""
        assert setup.ozs_trap.is_trap()
