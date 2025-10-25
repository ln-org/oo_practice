"""
表示丛林游戏中的一个棋子。
每个棋子都有一个拥有者，占据一个棋盘方格，具有1到8的等级，
等级决定了它的力量（可能受到方格影响），
一些棋子具有特殊能力，如游泳或跳跃。
"""

from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Piece:
    """
    丛林游戏中的棋子。
    
    Attributes:
        owner: 拥有这个棋子的玩家
        square: 这个棋子当前占据的方格
        rank: 这个棋子的等级（1-8）
    """
    
    def __init__(self, owner: 'Player', square: 'Square', rank: int):
        """
        使用拥有者、初始方格和等级构造一个棋子。
        
        Args:
            owner: 拥有这个棋子的玩家
            square: 这个棋子占据的初始方格
            rank: 这个棋子的等级
        """
        self._owner = owner
        self._square = square
        self._rank = rank
        owner.gain_one_piece()
    
    def is_owned_by(self, player: 'Player') -> bool:
        """
        检查此棋子是否属于指定玩家。
        
        Args:
            player: 要检查所有权的玩家
            
        Returns:
            如果此棋子属于指定玩家则返回True，
            否则返回False
        """
        return player == self._owner
    
    def get_strength(self) -> int:
        """
        获取此棋子的力量。
        通常等于等级，但如果在对手的陷阱中则降为0。
        
        Returns:
            棋子的力量，在对手陷阱中时为0
        """
        # 如果在陷阱中
        if self._square.is_trap() and not self._square.is_owned_by(self._owner):
            return 0
        
        return self._rank
    
    def can_swim(self) -> bool:
        """
        指示此棋子是否可以游泳。
        
        Returns:
            如果棋子可以游泳则返回True，否则返回False
        """
        return False
    
    def can_leap_horizontally(self) -> bool:
        """
        指示此棋子是否可以水平跳跃越过水。
        
        Returns:
            如果棋子可以水平跳跃则返回True，否则返回False
        """
        return False
    
    def can_leap_vertically(self) -> bool:
        """
        指示此棋子是否可以垂直跳跃越过水。
        
        Returns:
            如果棋子可以垂直跳跃则返回True，否则返回False
        """
        return False
    
    def move(self, to_square: 'Square'):
        """
        将棋子移动到新方格。
        如果移动到对手的兽穴，则占领该兽穴。
        
        Args:
            to_square: 要移动到的方格
        """
        self._square = to_square
        
        if self._square.is_den() and not self._square.is_owned_by(self._owner):
            self._owner.capture_den()
    
    def can_defeat(self, target: 'Piece') -> bool:
        """
        检查此棋子是否可以击败目标棋子。
        如果棋子有更高或相等的力量，或者是老鼠攻击大象，
        棋子就可以击败另一个棋子。
        
        Args:
            target: 要检查击败的棋子
            
        Returns:
            如果棋子可以击败目标则返回True，否则返回False
        """
        if not target.is_owned_by(self._owner) and (
            self.get_strength() >= target.get_strength()
            or (self._rank == 1 and target._rank == 8)  # 老鼠1攻击大象8
        ):
            return True
        
        return False
    
    def be_captured(self):
        """
        通过将其方格设置为None、移除其所有权并减少拥有者的棋子数量
        来捕获此棋子。
        """
        self._square = None
        self._owner.lose_one_piece()
        self._owner = None
