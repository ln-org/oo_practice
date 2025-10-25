"""
表示丛林游戏中的狮子棋子。
狮子可以水平和垂直跳跃越过水方格。
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Lion(Piece):
    """
    丛林游戏中的狮子棋子。
    
    狮子的等级为7，可以水平和垂直跳跃越过水方格。
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        使用拥有者和方格构造一个狮子。
        狮子的等级为7。
        
        Args:
            owner: 拥有这个狮子的玩家
            square: 这个狮子占据的方格
        """
        super().__init__(owner, square, 7)
    
    def can_leap_horizontally(self) -> bool:
        """
        指示狮子可以水平跳跃。
        
        Returns:
            True
        """
        return True
    
    def can_leap_vertically(self) -> bool:
        """
        指示狮子可以垂直跳跃。
        
        Returns:
            True
        """
        return True
