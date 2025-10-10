"""
Represents a square of the 7 x 9 Jungle game board.
Each square can have an owner, and may represent a specific type such as
a Den, Trap, Water, or Plain square.
"""

from abc import ABC, abstractmethod
from typing import TYPE_CHECKING, Optional

if TYPE_CHECKING:
    from ..player import Player


class Square(ABC):
    """
    Abstract base class for squares on the game board.
    
    Attributes:
        owner: The player who owns this square (can be None)
    """
    
    def __init__(self, owner: Optional['Player'] = None):
        """
        Constructs a Square with owner.
        
        Args:
            owner: Player who owns this square, can be None if no owner
        """
        self._owner = owner
    
    def is_owned_by(self, player: 'Player') -> bool:
        """
        Checks if this square is owned by the specified player.
        
        Args:
            player: Player to check ownership against
            
        Returns:
            True if player owns this square, False otherwise
        """
        return player == self._owner
    
    @abstractmethod
    def is_water(self) -> bool:
        """
        Indicates if this square is a Water square.
        By default, returns False, is overridden by WaterSquare.
        
        Returns:
            True if this square is a Water square, False otherwise
        """
        pass
    
    @abstractmethod
    def is_den(self) -> bool:
        """
        Indicates if this square is a Den.
        By default, returns False, is overridden by Den.
        
        Returns:
            True if this square is a Den, False otherwise
        """
        pass

    @abstractmethod
    def is_trap(self) -> bool:
        """
        Indicates if this square is a Trap.
        By default, returns False, is overridden by Trap.
        
        Returns:
            True if this square is a Trap, False otherwise
        """
        pass
