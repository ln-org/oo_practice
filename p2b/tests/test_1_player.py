"""Player 类的测试。"""

import pytest
from jungle import Player


class TestPlayer:
    """Player 类的测试用例。"""
    
    @pytest.fixture
    def setup(self):
        """设置测试用的玩家对象。"""
        self.michael = Player("Michael", 0)
        self.oz = Player("Ozgur", 1)
        return self
    
    def test_exists(self, setup):
        """测试玩家对象存在。"""
        assert setup.michael is not None
    
    def test_get_name(self, setup):
        """测试获取玩家姓名。"""
        assert setup.michael.get_name() == "Michael"
    
    def test_get_player_number(self, setup):
        """测试获取玩家编号。"""
        assert setup.michael.get_player_number() == 0
        assert setup.oz.get_player_number() == 1
    
    def test_has_captured_den_false(self, setup):
        """测试初始状态下未占领兽穴。"""
        assert not setup.michael.has_captured_den()
    
    def test_has_captured_den_true(self, setup):
        """测试占领兽穴后状态为已占领。"""
        setup.michael.capture_den()
        assert setup.michael.has_captured_den()
    
    def test_has_pieces_false(self, setup):
        """测试初始状态下没有棋子。"""
        assert not setup.michael.has_pieces()
    
    def test_gain_lose_pieces(self, setup):
        """测试获得和失去棋子。"""
        setup.michael.gain_one_piece()
        assert setup.michael.has_pieces()
        setup.michael.lose_one_piece()
        assert not setup.michael.has_pieces()
    
    # 边界条件测试
    def test_create_player_invalid_number(self):
        """测试使用无效编号创建玩家。"""
        with pytest.raises(ValueError, match="player_number should be 0 or 1"):
            Player("Alice", 2)
        
        with pytest.raises(ValueError, match="player_number should be 0 or 1"):
            Player("Bob", -1)
    
    def test_create_player_empty_name(self):
        """测试使用空名称创建玩家。"""
        with pytest.raises(ValueError, match="Player name cannot be None or empty"):
            Player("", 0)
    
    def test_create_player_none_name(self):
        """测试使用 None 作为名称创建玩家。"""
        with pytest.raises(ValueError, match="Player name cannot be None or empty"):
            Player(None, 0)
    
    def test_lose_piece_when_none(self):
        """测试玩家没有棋子时失去棋子。"""
        player = Player("Alice", 0)
        with pytest.raises(RuntimeError, match="No pieces left to lose"):
            player.lose_one_piece()
