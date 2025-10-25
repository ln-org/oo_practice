"""
表示丛林游戏的主要游戏逻辑和状态。
管理游戏棋盘、玩家、棋子、回合和游戏规则。
"""

from typing import List, Optional, Dict
from .player import Player
from .coordinate import Coordinate
from .illegal_move_exception import IllegalMoveException
from .pieces.piece import Piece
from .pieces.rat import Rat
from .pieces.tiger import Tiger
from .pieces.lion import Lion
from .squares.square import Square
from .squares.plain_square import PlainSquare
from .squares.water_square import WaterSquare
from .squares.den import Den
from .squares.trap import Trap


class Game:
    """
    丛林（斗兽棋）的主游戏类。
    
    管理游戏棋盘、玩家、棋子和游戏规则。
    """
    
    # 棋盘高度（行数）
    HEIGHT = 9
    
    # 棋盘宽度（列数）
    WIDTH = 7
    
    # 有水方格的行
    WATER_ROWS = [3, 4, 5]
    
    # 有水方格的列
    WATER_COLS = [1, 2, 4, 5]
    
    # 兽穴的列索引
    DEN_COL = 3
    
    def __init__(self, p0: Player, p1: Player):
        """
        使用两个玩家构造一个游戏实例。
        用棋盘方格初始化游戏棋盘，并将回合设置为第一个玩家。
        
        Args:
            p0: 第一个玩家
            p1: 第二个玩家
        """
        self._p0 = p0
        self._p1 = p1
        self._players = [p0, p1]
        
        # 初始化游戏棋盘
        self._squares: List[List[Square]] = [[None] * self.WIDTH 
                                               for _ in range(self.HEIGHT)]
        self._square_to_piece: Dict[Square, Piece] = {}
        
        self._initialize_board_squares()
        self._current_turn = 0
    
    def _initialize_board_squares(self) -> None:
        """
        通过在各自位置设置每种方格类型（平原、水域、兽穴、陷阱）来初始化游戏棋盘。
        棋盘是一个9x7的网格，其中某些方格被指定为水域方格，
        每个玩家的兽穴和陷阱方格都放置在特定位置。
        """
        # 首先将所有方格设置为普通方格
        for row in range(self.HEIGHT):
            for col in range(self.WIDTH):
                self._squares[row][col] = PlainSquare()
        
        # 根据预定义的行和列设置水域方格
        for row in self.WATER_ROWS:
            for col in self.WATER_COLS:
                self._squares[row][col] = WaterSquare()
        
        # 为每个玩家设置兽穴和陷阱
        # 设置p0的兽穴和陷阱
        p0_den_row = 0
        self._squares[p0_den_row][self.DEN_COL] = Den(self._p0)
        self._squares[p0_den_row][self.DEN_COL - 1] = Trap(self._p0)
        self._squares[p0_den_row][self.DEN_COL + 1] = Trap(self._p0)
        self._squares[p0_den_row + 1][self.DEN_COL] = Trap(self._p0)
        
        # 设置p1的兽穴和陷阱
        p1_den_row = 8
        self._squares[p1_den_row][self.DEN_COL] = Den(self._p1)
        self._squares[p1_den_row][self.DEN_COL - 1] = Trap(self._p1)
        self._squares[p1_den_row][self.DEN_COL + 1] = Trap(self._p1)
        self._squares[p1_den_row - 1][self.DEN_COL] = Trap(self._p1)
    
    def add_starting_pieces(self):
        """
        根据初始位置和等级为两个玩家添加起始棋子。
        """
        initial_piece_configs = [
            # [行, 列, 等级, 玩家编号]
            # 玩家0
            [2, 0, 1, 0],
            [1, 5, 2, 0],
            [1, 1, 3, 0],
            [2, 4, 4, 0],
            [2, 2, 5, 0],
            [0, 6, 6, 0],
            [0, 0, 7, 0],
            [2, 6, 8, 0],
            # 玩家1
            [6, 6, 1, 1],
            [7, 1, 2, 1],
            [7, 5, 3, 1],
            [6, 2, 4, 1],
            [6, 4, 5, 1],
            [8, 0, 6, 1],
            [8, 6, 7, 1],
            [6, 0, 8, 1],
        ]
        
        for piece_config in initial_piece_configs:
            row, col, rank, player_number = piece_config
            self.add_piece(row, col, rank, player_number)
    
    def add_piece(self, row: int, col: int, rank: int, player_number: int):
        """
        在棋盘上的给定位置添加一个棋子。
        
        Args:
            row: 游戏棋盘上方格的行坐标
            col: 游戏棋盘上方格的列坐标
            rank: 棋子的等级
            player_number: 拥有该棋子的玩家编号
        """
        player = self.get_player(player_number)
        square = self.get_square(row, col)
        
        if rank == 1:
            piece = Rat(player, square)
        elif rank == 6:
            piece = Tiger(player, square)
        elif rank == 7:
            piece = Lion(player, square)
        else:
            piece = Piece(player, square, rank)
        
        # 放入方格到棋子的映射中
        self._square_to_piece[self.get_square(row, col)] = piece
    
    def get_piece(self, row: int, col: int) -> Optional[Piece]:
        """
        获取棋盘上指定位置的棋子。
        
        Args:
            row: 游戏棋盘上的行坐标
            col: 游戏棋盘上的列坐标
        
        Returns:
            给定位置的棋子，如果没有棋子则返回None
        """
        square = self.get_square(row, col)
        return self._square_to_piece.get(square)
    
    def move(self, from_row: int, from_col: int, to_row: int, to_col: int):
        """
        将棋子从一个位置移动到另一个位置。
        
        Args:
            from_row: 棋子当前位置的行坐标
            from_col: 棋子当前位置的列坐标
            to_row: 目标位置的行坐标
            to_col: 目标位置的列坐标
            
        Raises:
            IllegalMoveException: 如果目标坐标对于该棋子不合法
        """
        # 检查目标位置是否有效
        if Coordinate(to_row, to_col) not in self.get_legal_moves(from_row, from_col):
            raise IllegalMoveException(
                f"({to_row}, {to_col}) is not legal move"
            )
        
        piece = self.get_piece(from_row, from_col)
        target_piece = self.get_piece(to_row, to_col)
        source_square = self.get_square(from_row, from_col)
        target_square = self.get_square(to_row, to_col)
        
        if target_piece is not None:
            target_piece.be_captured()
        
        # 移动棋子
        piece.move(self.get_square(to_row, to_col))
        
        # 更新方格到棋子的映射
        del self._square_to_piece[source_square]
        self._square_to_piece[target_square] = piece
        
        # 轮到另一个玩家
        self._next_turn()
    
    def get_player(self, player_number: int) -> Player:
        """
        根据玩家编号获取玩家。
        
        Args:
            player_number: 玩家编号（0或1）
            
        Returns:
            具有指定玩家编号的玩家
            
        Raises:
            ValueError: 如果玩家编号无效
        """
        # 检查玩家编号是否有效
        try:
            Player("check_player_number", player_number)
        except ValueError as e:
            raise e
        
        return self._p0 if player_number == self._p0.get_player_number() else self._p1
    
    def get_winner(self) -> Optional[Player]:
        """
        确定游戏的获胜者。
        如果玩家占领对手的兽穴或对手没有棋子剩余，则该玩家获胜。
        
        Returns:
            获胜的玩家，如果还没有获胜者则返回None
        """
        if (
            self._p0.has_captured_den()  # p0占领了p1的兽穴
            or not self._p1.has_pieces()  # 或者p1没有剩余棋子
        ):
            return self._p0  # p0获胜
        
        if (
            self._p1.has_captured_den()  # p1占领了p0的兽穴
            or not self._p0.has_pieces()  # 或者p0没有剩余棋子
        ):
            return self._p1  # p1获胜
        
        return None
    
    def is_game_over(self) -> bool:
        """
        检查游戏是否结束。
        
        Returns:
            如果有获胜者则返回True，否则返回False
        """
        return self.get_winner() is not None
    
    def get_square(self, row: int, col: int) -> Square:
        """
        获取游戏棋盘上指定坐标的方格。
        
        Args:
            row: 棋盘上的行坐标
            col: 棋盘上的列坐标
            
        Returns:
            指定坐标的方格
            
        Raises:
            IndexError: 如果坐标在游戏棋盘外
        """
        if (
            (row < 0 or row > self.HEIGHT - 1)  # 超出高度
            or (col < 0 or col > self.WIDTH - 1)  # 超出宽度
        ):
            raise IndexError(
                f"Coordinate exceed board bounds: ({row}, {col})"
            )
        
        return self._squares[row][col]
    
    def get_legal_moves(self, row: int, col: int) -> List[Coordinate]:
        """
        获取指定坐标处棋子的合法移动。
        
        Args:
            row: 当前行坐标
            col: 当前列坐标
            
        Returns:
            棋子可以移动的合法坐标列表
        """
        coordinates = []
        
        this_turn_player = self.get_player(self._get_turn() % 2)
        selected_piece = self.get_piece(row, col)
        
        if (
            selected_piece is None  # 该坐标没有棋子
            # 或者棋子不属于当前回合可以移动的玩家
            or not selected_piece.is_owned_by(this_turn_player)
            or self.is_game_over()  # 或者游戏已经结束
        ):
            return coordinates  # 返回空列表
        
        # 4个潜在的移动方向
        directions = [
            Coordinate(-1, 0),  # 向上
            Coordinate(1, 0),   # 向下
            Coordinate(0, -1),  # 向左
            Coordinate(0, 1),   # 向右
        ]
        
        # 在4个方向中探索可以移动到的方格
        for direction in directions:
            coordinate = self._explore_direction(
                row,
                col,
                direction.row(),
                direction.col()
            )
            
            # 如果找到有效方格，添加到列表中
            if coordinate is not None:
                coordinates.append(coordinate)
        
        # 返回列表中所有有效方格
        return coordinates
    
    def _explore_direction(
        self,
        row: int,
        col: int,
        row_direction: int,
        col_direction: int
    ) -> Optional[Coordinate]:
        """
        从起始位置探索指定方向，查找是否有可以移动到的方格。
        处理棋子可以直接移动到相邻方格、在水中游泳或跳跃过水域的情况
        （如果棋子具有该能力）。
        
        Args:
            row: 起始行坐标
            col: 起始列坐标
            row_direction: 行方向（例如，-1表示向上，1表示向下）
            col_direction: 列方向（例如，-1表示向左，1表示向右）
            
        Returns:
            如果移动有效则返回目标坐标，如果不允许则返回None
            
        Raises:
            ValueError: 如果方向无效
        """
        # 探索（移动）方向只能是水平或垂直的
        if not (
            ((row_direction == -1 or row_direction == 1) and col_direction == 0)
            or (row_direction == 0 and (col_direction == -1 or col_direction == 1))
        ):
            raise ValueError(
                "Row and Col Direction, one should be -1 or 1, "
                "the other should be 0"
            )
        
        try:
            # 当前探索的方格
            explore_square = self.get_square(row + row_direction,
                                             col + col_direction)
            
            selected_piece = self.get_piece(row, col)
            destination_piece = self.get_piece(row + row_direction,
                                               col + col_direction)
            
            if (
                # 探索直接相邻的可移动方格
                (
                    # 方格不是水域或棋子可以游泳
                    not explore_square.is_water() or selected_piece.can_swim()
                ) and (
                    # 有棋子但可以击败
                    (destination_piece is not None
                     and selected_piece.can_defeat(destination_piece))
                    # 或者没有棋子
                    or destination_piece is None
                )
            ):
                return Coordinate(row + row_direction, col + col_direction)
            elif (
                # 探索可以跳跃到的方格
                (
                    # 相邻方格是水域，并且没有棋子
                    explore_square.is_water() and destination_piece is None
                ) and (
                    # 选中的棋子可以垂直跳跃
                    # 并且当前探索垂直方向
                    (selected_piece.can_leap_vertically() and row_direction != 0)
                    # 或者选中的棋子可以水平跳跃
                    # 并且当前探索水平方向
                    or (selected_piece.can_leap_horizontally() and col_direction != 0)
                )
            ):
                step = 2
                while True:
                    explore_square = self.get_square(row + row_direction * step,
                                                     col + col_direction * step)
                    destination_piece = self.get_piece(row + row_direction * step,
                                                       col + col_direction * step)
                    
                    # 停止探索，如果棋子占据了
                    # 中间的水域则无法跳跃
                    if explore_square.is_water() and destination_piece is not None:
                        break
                    
                    # 返回的方格不是水域，没有棋子或可以被击败
                    if (
                        not explore_square.is_water()
                        and (
                            destination_piece is None
                            or selected_piece.can_defeat(destination_piece)
                        )
                    ):
                        return Coordinate(row + row_direction * step,
                                          col + col_direction * step)
                    
                    # 探索下一个方格
                    step += 1
        except IndexError as e:
            import sys
            print(f"Exploring: {e}", file=sys.stderr)
        
        return None
    
    def _get_turn(self) -> int:
        """获取当前回合数。"""
        return self._current_turn
    
    def _next_turn(self):
        """进入下一回合。"""
        self._current_turn += 1
