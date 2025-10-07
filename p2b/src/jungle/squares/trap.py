"""
Represents a Trap square on the Jungle game board.
Trap weakens opponent pieces.
"""

from typing import TYPE_CHECKING
from .square import Square

if TYPE_CHECKING:
    from ..player import Player


class Trap(Square):
    """
    A Trap square on the game board.
    
    Traps weaken opponent pieces that step on them.
    """
    
    def __init__(self, owner: 'Player'):
        """
        Constructs a Trap with owner.
        
        Args:
            owner: Player who owns this Trap
        """
        super().__init__(owner)
    
    def is_trap(self) -> bool:
        """
        Indicates that this square is a Trap.
        
        Returns:
            True, as this square is a Trap
        """
        return True
