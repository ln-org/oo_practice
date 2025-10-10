"""
Represents a Den square on the Jungle game board.
Capturing the opponent's Den results in a victory.
"""

from typing import TYPE_CHECKING
from .square import Square

if TYPE_CHECKING:
    from ..player import Player


class Den(Square):
    """
    A Den square on the game board.
    
    Each player has a Den. Capturing the opponent's Den wins the game.
    """
    
    def __init__(self, owner: 'Player'):
        """
        Constructs a Den with owner.
        
        Args:
            owner: Player who owns this Den
        """
        super().__init__(owner)
    
    def is_den(self) -> bool:
        """
        Indicates this square is a Den.
        
        Returns:
            True
        """
        return True
    
    def is_water(self) -> bool:
        """
        Indicates that this square is not a Water square.
        
        Returns:
            False
        """
        return False
    
    def is_trap(self) -> bool:
        """
        Indicates that this square is not a Trap.
        
        Returns:
            False
        """
        return False
