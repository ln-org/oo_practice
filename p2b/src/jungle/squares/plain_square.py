"""
Represents a Plain square on the Jungle game board.
"""

from .square import Square


class PlainSquare(Square):
    """A plain square with no special properties."""
    
    def __init__(self):
        """Constructs a PlainSquare with no owner."""
        super().__init__(None)
