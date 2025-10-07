"""Piece 类的测试。"""

import pytest
from jungle import Player
from jungle.pieces import Piece, Rat, Lion
from jungle.squares import PlainSquare, Den, Trap


class TestPiece:
    """Piece 类的测试用例。"""
    
    @pytest.fixture
    def setup(self):
        """设置测试用的棋子对象。"""
        # 玩家
        self.michael = Player("Michael", 0)
        self.oz = Player("Ozgur", 1)
        
        # 方格
        self.land = PlainSquare()
        self.michaels_den = Den(self.michael)
        self.ozs_trap = Trap(self.oz)
        
        # 棋子
        self.michaels_wolf = Piece(self.michael, self.land, 4)
        self.michaels_lion = Lion(self.michael, self.ozs_trap)
        self.ozs_rat = Rat(self.oz, self.land)
        self.michaels_elephant = Piece(self.michael, self.land, 8)
        
        return self
    
    def test_owner(self, setup):
        """测试棋子有正确的所有者。"""
        assert setup.michaels_wolf.is_owned_by(setup.michael)
    
    def test_non_owner(self, setup):
        """测试棋子不属于非所有者。"""
        assert not setup.michaels_wolf.is_owned_by(setup.oz)
    
    def test_strength(self, setup):
        """测试棋子强度。"""
        assert setup.michaels_wolf.get_strength() == 4
    
    def test_trapped_strength(self, setup):
        """测试被困的棋子强度为 0。"""
        assert setup.michaels_lion.get_strength() == 0
    
    def test_can_swim_false(self, setup):
        """测试狼不能游泳。"""
        assert not setup.michaels_wolf.can_swim()
    
    def test_can_swim_true(self, setup):
        """测试老鼠能游泳。"""
        assert setup.ozs_rat.can_swim()
    
    def test_can_leap(self, setup):
        """测试狮子能垂直跳跃。"""
        assert setup.michaels_lion.can_leap_vertically()
    
    def test_can_defeat(self, setup):
        """测试狼能战胜老鼠。"""
        assert setup.michaels_wolf.can_defeat(setup.ozs_rat)
    
    def test_cannot_defeat(self, setup):
        """测试老鼠不能战胜狼。"""
        assert not setup.ozs_rat.can_defeat(setup.michaels_wolf)
    
    def test_defeat_trapped(self, setup):
        """测试老鼠能战胜被困的狮子。"""
        assert setup.ozs_rat.can_defeat(setup.michaels_lion)
    
    def test_rat_defeat_elephant(self, setup):
        """测试老鼠能战胜大象（特殊规则）。"""
        assert setup.ozs_rat.can_defeat(setup.michaels_elephant)
    
    def test_move(self, setup):
        """测试棋子移动。"""
        setup.michaels_lion.move(setup.ozs_trap)
        assert setup.michaels_lion.get_strength() == 0
    
    def test_move_to_den(self, setup):
        """测试移动到对手兽穴可占领它。"""
        setup.ozs_rat.move(setup.michaels_den)
        assert setup.oz.has_captured_den()
    
    def test_move_to_own_den(self, setup):
        """测试移动到自己的兽穴不会占领它。"""
        setup.michaels_wolf.move(setup.michaels_den)
        assert not setup.michael.has_captured_den()
    
    def test_has_pieces(self, setup):
        """测试玩家创建棋子后有棋子。"""
        assert setup.oz.has_pieces()
    
    def test_lose_all_pieces(self, setup):
        """测试棋子被吃掉后玩家失去所有棋子。"""
        setup.ozs_rat.be_captured()
        assert not setup.oz.has_pieces()
