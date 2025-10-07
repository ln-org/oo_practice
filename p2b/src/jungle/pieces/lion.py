"""
Represents a Lion piece in the Jungle game.
Lion can leap both horizontally and vertically over water squares.
"""

from typing import TYPE_CHECKING
from .piece import Piece

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Lion(Piece):
    """
    A Lion piece in the Jungle game.
    
    The Lion has rank 7 and can leap both horizontally and vertically
    over water squares.
    """
    
    def __init__(self, owner: 'Player', square: 'Square'):
        """
        Constructs a Lion with the owner and square.
        The Lion has a rank of 7.
        
        Args:
            owner: Player who owns this Lion
            square: Square occupied by this Lion
        """
        super().__init__(owner, square, 7)
    
    def can_leap_horizontally(self) -> bool:
        """
        Indicates Lion can leap horizontally.
        
        Returns:
            True
        """
        return True
    
    def can_leap_vertically(self) -> bool:
        """
        Indicates Lion can leap vertically.
        
        Returns:
            True
        """
        return True
