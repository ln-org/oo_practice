"""
Represents a Tiger piece in Jungle game.
Tiger can leap horizontally over water squares.
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Tiger(Piece):
    """
    A Tiger piece in the Jungle game.
    
    The Tiger has rank 6 and can leap horizontally over water squares.
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        Constructs a Tiger with owner and square.
        Tiger has a rank of 6.
        
        Args:
            owner: Player who owns this Tiger
            square: Square occupied by this Tiger
        """
        super().__init__(owner, square, 6)
    
    def can_leap_horizontally(self) -> bool:
        """
        Indicates that the Tiger can leap horizontally.
        
        Returns:
            True
        """
        return True
