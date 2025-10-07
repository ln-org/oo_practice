"""Game 类的完整测试。"""

import pytest
from jungle import Game, Player, IllegalMoveException


class TestFullGame:
    """Game 类的测试用例，包含所有棋子。"""
    
    @pytest.fixture
    def setup(self):
        """设置包含所有棋子的测试游戏。"""
        self.michael = Player("Michael", 0)
        self.oz = Player("Ozgur", 1)
        self.game = Game(self.michael, self.oz)
        self.game.add_starting_pieces()
        return self
    
    def test_piece_placed_strength(self, setup):
        """测试放置的棋子有正确的强度。"""
        assert setup.game.get_piece(2, 4).get_strength() == 4
    
    def test_piece_placed_owner(self, setup):
        """测试放置的棋子有正确的所有者。"""
        assert setup.game.get_piece(2, 4).is_owned_by(setup.michael)
    
    def test_empty_square(self, setup):
        """测试空方格没有棋子。"""
        assert setup.game.get_piece(2, 3) is None
    
    def test_placed_rat(self, setup):
        """测试放置的老鼠能游泳。"""
        assert setup.game.get_piece(2, 0).can_swim()
    
    def test_placed_lion(self, setup):
        """测试放置的狮子能纵向跳跃。"""
        assert setup.game.get_piece(8, 6).can_leap_vertically()
    
    def test_placed_dog(self, setup):
        """测试放置的狗有正确的强度。"""
        assert setup.game.get_piece(1, 1).get_strength() == 3
    
    def test_no_winner_at_start(self, setup):
        """测试游戏开始时没有赢家。"""
        assert setup.game.get_winner() is None
    
    @pytest.mark.parametrize("from_row,from_col,to_row,to_col", [
        (2, 4, 3, 4),  # 狼试图进入水域
        (2, 6, 2, 7),  # 大象试图走出棋盘
        (2, 0, 4, 0),  # 老鼠试图一次移动两格
        (2, 2, 3, 3),  # 豹试图对角线移动
        (6, 4, 6, 5),  # P1 试图在 P0 的回合移动
    ])
    def test_illegal_move_exception(self, setup, from_row, from_col, to_row, to_col):
        """测试各种非法移动会抛出异常。"""
        with pytest.raises(IllegalMoveException):
            setup.game.move(from_row, from_col, to_row, to_col)
    
    def test_legal_move(self, setup):
        """测试进行合法移动。"""
        setup.game.move(1, 1, 1, 2)
        assert setup.game.get_piece(1, 1) is None
        assert setup.game.get_piece(1, 2) is not None
    
    @pytest.mark.parametrize("row,col,expected_number", [
        (0, 0, 2),  # P0 的狮子可以到达两个位置（它在角落）
        (1, 1, 4),  # P0 的狗在所有 4 个方向都畅通无阻
        (2, 6, 3),  # P0 的大象在边缘：可以到达 3 个位置
        (2, 4, 3),  # P0 的狼不能进入水域
        (7, 1, 0),  # P1 的猫不能在 P0 的回合移动
        (2, 1, 0),  # 方格 (2, 1) 上没有棋子
    ])
    def test_get_legal_moves_start(self, setup, row, col, expected_number):
        """测试从起始位置的合法移动数量。"""
        assert len(setup.game.get_legal_moves(row, col)) == expected_number
    
    def test_full_game(self, setup):
        """
        一个完整的游戏，沿途测试各种情况。
        
        通常每个测试一个断言更好，但至少有一个这种形式的测试似乎是合理的。
        """
        setup.game.move(2, 0, 3, 0)  # michael 前进他的老鼠
        setup.game.move(6, 0, 5, 0)  # oz 前进他的大象
        
        setup.game.move(3, 0, 3, 1)  # michael 的老鼠进入水域
        setup.game.move(5, 0, 4, 0)  # oz 进一步前进他的大象
        
        setup.game.move(3, 1, 4, 1)  # michael 的老鼠在棋盘上继续游动
        assert len(setup.game.get_legal_moves(4, 0)) == 2  # 大象不能捕获水中的老鼠
        setup.game.move(8, 6, 7, 6)  # oz 的狮子前进
        
        setup.game.move(4, 1, 4, 0)  # michael 的老鼠捕获 oz 的大象！
        assert len(setup.game.get_legal_moves(7, 6)) == 1  # 狮子不能捕获己方棋子
        setup.game.move(6, 6, 5, 6)  # oz 的老鼠向前移动
        
        setup.game.move(4, 0, 4, 1)  # michael 的老鼠重新进入水域
        setup.game.move(7, 6, 6, 6)  # oz 的狮子前进
        assert setup.game.get_piece(4, 0) is None
        assert setup.game.get_piece(4, 1).get_strength() == 1
        assert setup.game.get_piece(6, 6).get_strength() == 7
        
        setup.game.move(0, 0, 0, 1)  # michael 的狮子横向移动
        setup.game.move(5, 6, 5, 5)  # oz 的老鼠进入水域
        
        setup.game.move(4, 1, 4, 2)  # michael 横向移动他的老鼠（仍在水中）
        setup.game.move(6, 6, 6, 5)  # oz 的狮子接近水域
        
        setup.game.move(0, 1, 0, 2)  # michael 将他的狮子移动到自己的陷阱上
        assert setup.game.get_piece(0, 2).get_strength() == 7  # michael 的棋子不受自己陷阱的影响
        assert len(setup.game.get_legal_moves(6, 5)) == 1  # oz 的狮子由于老鼠的存在无法跳过水域
        setup.game.move(5, 5, 5, 4)  # oz 将他的老鼠移开
        
        setup.game.move(2, 4, 2, 5)  # michael 将他的狼移入危险境地
        setup.game.move(6, 5, 2, 5)  # oz 的狮子跳跃并吃掉 michael 的狼！
        assert setup.game.get_piece(2, 5).is_owned_by(setup.oz)
        
        assert len(setup.game.get_legal_moves(1, 5)) == 3  # michael 的猫不能吃 oz 的老虎
        setup.game.move(0, 6, 0, 5)  # michael 横向移动他的老虎
        assert len(setup.game.get_legal_moves(2, 5)) == 3  # oz 的狮子可以向后跳，但不能吃 michael 的大象
        setup.game.move(2, 5, 1, 5)  # oz 的狮子吃掉 michael 的猫（7 > 2）
        
        setup.game.move(4, 2, 5, 2)  # michael 前进他的老鼠
        setup.game.move(1, 5, 1, 4)  # oz 横向移动他的狮子
        
        assert len(setup.game.get_legal_moves(5, 2)) == 3  # michael 的老鼠不能捕获狼
        setup.game.move(5, 2, 5, 3)  # michael 的老鼠离开水域
        setup.game.move(1, 4, 0, 4)  # oz 的狮子进入 michael 的陷阱
        
        assert len(setup.game.get_legal_moves(0, 5)) == 3  # michael 的老虎可以吃掉被困的狮子
        setup.game.move(5, 3, 5, 4)  # michael 的老鼠改为吃掉 oz 的老鼠
        assert not setup.game.is_game_over()
        assert setup.game.get_winner() is None
        setup.game.move(0, 4, 0, 3)  # oz 移动到 michael 的兽穴并获胜！
        
        assert setup.game.is_game_over()
        assert setup.game.get_winner() == setup.oz
        assert len(setup.game.get_legal_moves(1, 1)) == 0  # 游戏结束后没有合法移动
