"""
表示丛林游戏中的老鼠棋子。
老鼠是最弱的棋子（等级1）但具有游泳能力。
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Rat(Piece):
    """
    丛林游戏中的老鼠棋子。
    
    老鼠的等级为1，可以游过水方格。
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        使用拥有者和方格构造一个老鼠。
        老鼠的等级为1。
        
        Args:
            owner: 拥有这个老鼠的玩家
            square: 这个老鼠占据的方格
        """
        super().__init__(owner, square, 1)
    
    def can_swim(self) -> bool:
        """
        指示老鼠可以游泳。
        
        Returns:
            True
        """
        return True
