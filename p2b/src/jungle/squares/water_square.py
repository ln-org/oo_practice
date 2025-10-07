"""
Represents a Water square on the Jungle game board.
A Water square is a special type only rat can enter,
some specific pieces can leap.
"""

from .square import Square


class WaterSquare(Square):
    """
    A Water square on the game board.
    
    Only Rats can enter water squares. Tigers and Lions can leap over them.
    """
    
    def __init__(self):
        """Constructs a WaterSquare with no owner."""
        super().__init__(None)
    
    def is_water(self) -> bool:
        """
        Indicates that this square is a Water square.
        
        Returns:
            True, as this square is a Water square
        """
        return True
