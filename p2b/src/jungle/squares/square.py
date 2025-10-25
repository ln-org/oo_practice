"""
表示7x9丛林游戏棋盘的一个方格。
每个方格可以有一个拥有者，并且可以表示特定类型，
如兽穴、陷阱、水或普通方格。
"""

from abc import ABC, abstractmethod
from typing import TYPE_CHECKING, Optional

if TYPE_CHECKING:
    from ..player import Player


class Square(ABC):
    """
    游戏棋盘上方格的抽象基类。
    
    Attributes:
        owner: 拥有此方格的玩家（可以为None）
    """
    
    def __init__(self, owner: Optional['Player'] = None):
        """
        使用拥有者构造一个方格。
        
        Args:
            owner: 拥有此方格的玩家，如果没有拥有者可以为None
        """
        self._owner = owner
    
    def is_owned_by(self, player: 'Player') -> bool:
        """
        检查此方格是否属于指定玩家。
        
        Args:
            player: 要检查所有权的玩家
            
        Returns:
            如果玩家拥有此方格则返回True，否则返回False
        """
        return player == self._owner
    
    @abstractmethod
    def is_water(self) -> bool:
        """
        指示此方格是否为水方格。
        默认返回False，由WaterSquare重写。
        
        Returns:
            如果此方格是水方格则返回True，否则返回False
        """
        pass
    
    @abstractmethod
    def is_den(self) -> bool:
        """
        指示此方格是否为兽穴。
        默认返回False，由Den重写。
        
        Returns:
            如果此方格是兽穴则返回True，否则返回False
        """
        pass

    @abstractmethod
    def is_trap(self) -> bool:
        """
        指示此方格是否为陷阱。
        默认返回False，由Trap重写。
        
        Returns:
            如果此方格是陷阱则返回True，否则返回False
        """
        pass
