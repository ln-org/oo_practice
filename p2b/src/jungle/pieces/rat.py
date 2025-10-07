"""
Represents a Rat piece in Jungle game.
Rat is the weakest piece (rank 1) but has ability to swim.
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Rat(Piece):
    """
    A Rat piece in the Jungle game.
    
    The Rat has rank 1 and can swim through water squares.
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        Constructs a Rat with owner and square.
        Rat has a rank of 1.
        
        Args:
            owner: Player who owns this Rat
            square: Square occupied by this Rat
        """
        super().__init__(owner, square, 1)
    
    def can_swim(self) -> bool:
        """
        Indicates that the Rat can swim.
        
        Returns:
            True
        """
        return True
