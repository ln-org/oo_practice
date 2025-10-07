"""
Squares package for Jungle game.
Contains all square types on the game board.
"""

from .square import Square
from .plain_square import PlainSquare
from .water_square import WaterSquare
from .den import Den
from .trap import Trap

__all__ = ['Square', 'PlainSquare', 'WaterSquare', 'Den', 'Trap']
