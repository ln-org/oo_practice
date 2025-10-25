"""
表示丛林游戏中的老虎棋子。
老虎可以水平跳跃越过水方格。
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Tiger(Piece):
    """
    丛林游戏中的老虎棋子。
    
    老虎的等级为6，可以水平跳跃越过水方格。
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        使用拥有者和方格构造一个老虎。
        老虎的等级为6。
        
        Args:
            owner: 拥有这个老虎的玩家
            square: 这个老虎占据的方格
        """
        super().__init__(owner, square, 6)
    
    def can_leap_horizontally(self) -> bool:
        """
        指示老虎可以水平跳跃。
        
        Returns:
            True
        """
        return True
