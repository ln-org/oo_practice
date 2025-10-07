"""Game 类的简单测试。"""

import pytest
from jungle import Game, Player, IllegalMoveException


class TestSimpleGame:
    """Game 类的测试用例，使用只有少量棋子的简单设置。"""
    
    @pytest.fixture
    def setup(self):
        """设置测试用的游戏对象。"""
        self.michael = Player("Michael", 0)
        self.oz = Player("Ozgur", 1)
        self.game = Game(self.michael, self.oz)
        return self
    
    def test_height(self, setup):
        """测试游戏棋盘高度常量。"""
        assert setup.game.HEIGHT == 9
    
    def test_width(self, setup):
        """测试游戏棋盘宽度常量。"""
        assert setup.game.WIDTH == 7
    
    def test_exists(self, setup):
        """测试游戏对象存在。"""
        assert setup.game is not None
    
    def test_no_piece(self, setup):
        """测试从空方格获取棋子。"""
        assert setup.game.get_piece(1, 0) is None
    
    def test_add_piece(self, setup):
        """测试添加棋子。"""
        setup.game.add_piece(1, 0, 3, 0)  # 玩家 0 的狗在 (1,0)
        assert setup.game.get_piece(1, 0) is not None
    
    def test_add_piece_occupied(self, setup):
        """测试向已占用的方格添加棋子会替换它。"""
        setup.game.add_piece(1, 0, 3, 0)  # 玩家 0 的狗在 (1,0)
        setup.game.add_piece(1, 0, 2, 0)  # 玩家 0 的猫替换它
        assert setup.game.get_piece(1, 0).get_strength() == 2
    
    def test_get_bad_square(self, setup):
        """测试获取无效坐标的方格。"""
        with pytest.raises(IndexError):
            setup.game.get_square(0, 9)
    
    def test_get_bad_square_negative(self, setup):
        """测试获取负坐标的方格。"""
        with pytest.raises(IndexError):
            setup.game.get_square(-1, 2)
    
    def test_water(self, setup):
        """测试水域方格。"""
        assert setup.game.get_square(4, 5).is_water()
    
    def test_not_water(self, setup):
        """测试非水域方格。"""
        assert not setup.game.get_square(4, 6).is_water()
    
    def test_den(self, setup):
        """测试兽穴方格。"""
        assert setup.game.get_square(8, 3).is_den()
    
    def test_den_owner(self, setup):
        """测试兽穴有正确的所有者。"""
        assert setup.game.get_square(8, 3).is_owned_by(setup.oz)
    
    def test_trap(self, setup):
        """测试陷阱方格。"""
        assert setup.game.get_square(0, 2).is_trap()
    
    def test_get_player(self, setup):
        """测试根据编号获取玩家。"""
        assert setup.game.get_player(1) == setup.oz
    
    def test_get_player_bad(self, setup):
        """测试使用无效编号获取玩家。"""
        with pytest.raises(ValueError):
            setup.game.get_player(3)
    
    def test_no_legal_moves(self, setup):
        """测试空方格没有合法移动。"""
        assert len(setup.game.get_legal_moves(0, 0)) == 0
    
    def test_legal_moves_bad_square(self, setup):
        """测试无效坐标的合法移动。"""
        with pytest.raises(IndexError):
            setup.game.get_legal_moves(-2, 7)
    
    def test_move_from_bad_square(self, setup):
        """测试从无效坐标移动。"""
        with pytest.raises(IndexError):
            setup.game.move(-1, 7, 0, 7)
    
    def test_move_legal(self, setup):
        """测试合法移动。"""
        setup.game.add_piece(2, 4, 4, 0)  # 放置 michael 的狼
        wolf = setup.game.get_piece(2, 4)
        setup.game.add_piece(6, 2, 5, 1)  # 放置 oz 的豹
        setup.game.move(2, 4, 2, 3)  # michael 的狼向右移动
        assert setup.game.get_piece(2, 3) == wolf
        assert setup.game.get_piece(2, 4) is None
    
    def test_enter_den_win_game(self, setup):
        """测试进入对手兽穴赢得游戏。"""
        setup.game.add_piece(8, 2, 2, 0)  # michael 的猫在 oz 的兽穴附近
        setup.game.add_piece(6, 2, 4, 1)  # oz 的狼
        
        setup.game.move(8, 2, 8, 3)  # michael 的猫进入 oz 的兽穴
        assert setup.game.get_winner() == setup.michael
        assert setup.game.is_game_over()
    
    def test_no_pieces_lose_game(self, setup):
        """测试没有棋子输掉游戏。"""
        setup.game.add_piece(2, 4, 4, 0)  # 放置 michael 的狼，oz 没有棋子
        assert setup.game.get_winner() == setup.michael
        assert setup.game.is_game_over()
    
    def test_tiger_horizontal_jump(self, setup):
        """测试老虎可以横向跳过水域。"""
        setup.game.add_piece(3, 0, 6, 0)  # 放置 michael 的老虎在水边
        setup.game.add_piece(7, 1, 2, 1)  # oz 也有一个棋子
        assert len(setup.game.get_legal_moves(3, 0)) == 3  # 老虎可以横向跳过水域
        setup.game.move(3, 0, 3, 3)  # 跳跃且不抛出异常
    
    def test_tiger_vertical_jump(self, setup):
        """测试老虎不能纵向跳过水域。"""
        setup.game.add_piece(2, 1, 6, 0)  # 放置 michael 的老虎在水后面
        setup.game.add_piece(7, 1, 2, 1)  # oz 也有一个棋子
        assert len(setup.game.get_legal_moves(2, 1)) == 3  # 老虎不能纵向跳过水域
        with pytest.raises(IllegalMoveException):
            setup.game.move(2, 1, 6, 1)
    
    def test_lion_vertical_jump(self, setup):
        """测试狮子可以纵向跳过水域。"""
        setup.game.add_piece(2, 1, 7, 0)  # 放置 michael 的狮子在水后面
        setup.game.add_piece(7, 1, 2, 1)  # oz 也有一个棋子
        assert len(setup.game.get_legal_moves(2, 1)) == 4  # 狮子可以纵向跳过水域
        setup.game.move(2, 1, 6, 1)
