"""
Represents a Plain square on the Jungle game board.
"""

from .square import Square


class PlainSquare(Square):
    """A plain square with no special properties."""
    
    def __init__(self):
        """Constructs a PlainSquare with no owner."""
        super().__init__(None)

    def is_water(self) -> bool:
        """Indicates that this square is not a Water square."""
        return False
    
    def is_den(self) -> bool:
        """Indicates that this square is not a Den."""
        return False

    def is_trap(self) -> bool:
        """Indicates that this square is not a Trap."""
        return False
