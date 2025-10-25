"""
表示丛林游戏棋盘上的兽穴方格。
占领对手的兽穴即可获得胜利。
"""

from typing import TYPE_CHECKING
from .square import Square

if TYPE_CHECKING:
    from ..player import Player


class Den(Square):
    """
    游戏棋盘上的兽穴方格。
    
    每个玩家都有一个兽穴。占领对手的兽穴即可获胜。
    """
    
    def __init__(self, owner: 'Player'):
        """
        构造一个有拥有者的兽穴。
        
        Args:
            owner: 拥有该兽穴的玩家
        """
        super().__init__(owner)
    
    def is_den(self) -> bool:
        """
        指示该方格是兽穴。
        
        Returns:
            True
        """
        return True
    
    def is_water(self) -> bool:
        """
        指示该方格不是水域方格。
        
        Returns:
            False
        """
        return False
    
    def is_trap(self) -> bool:
        """
        指示该方格不是陷阱。
        
        Returns:
            False
        """
        return False
