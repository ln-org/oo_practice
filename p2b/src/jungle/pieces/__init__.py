"""
Pieces package for Jungle game.
Contains all piece types including base Piece class and special pieces.
"""

from .piece import Piece
from .rat import Rat
from .tiger import Tiger
from .lion import Lion

__all__ = ['Piece', 'Rat', 'Tiger', 'Lion']
