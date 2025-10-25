"""
表示丛林游戏棋盘上的陷阱方格。
陷阱会削弱对手的棋子。
"""

from typing import TYPE_CHECKING
from .square import Square

if TYPE_CHECKING:
    from ..player import Player


class Trap(Square):
    """
    游戏棋盘上的陷阱方格。
    
    陷阱会削弱踩上它们的对手棋子。
    """
    
    def __init__(self, owner: 'Player'):
        """
        构造一个有拥有者的陷阱。
        
        Args:
            owner: 拥有该陷阱的玩家
        """
        super().__init__(owner)
    
    def is_trap(self) -> bool:
        """
        指示该方格是陷阱。
        
        Returns:
            True，因为该方格是陷阱
        """
        return True
    
    def is_water(self) -> bool:
        """
        指示该方格不是水域方格。
        
        Returns:
            False
        """
        return False
    
    def is_den(self) -> bool:
        """
        指示该方格不是兽穴。
        
        Returns:
            False
        """
        return False
