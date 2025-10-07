"""
Represents a piece in Jungle game.
Each piece has an owner, occupies a board square, has a rank from
1 to 8, which determines its strength (could impact by square),
some of the pieces have special abilities, such as swimming or
leaping.
"""

from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from ..player import Player
    from ..squares.square import Square


class Piece:
    """
    A piece in the Jungle game.
    
    Attributes:
        owner: The player who owns this piece
        square: The square currently occupied by this piece
        rank: The rank of this piece (1-8)
    """
    
    def __init__(self, owner: 'Player', square: 'Square', rank: int):
        """
        Constructs a Piece with owner, initial square, and rank.
        
        Args:
            owner: Player who owns this piece
            square: Initial square occupied by this piece
            rank: Rank of this piece
        """
        self._owner = owner
        self._square = square
        self._rank = rank
        owner.gain_one_piece()
    
    def is_owned_by(self, player: 'Player') -> bool:
        """
        Checks if this piece is owned by the specified player.
        
        Args:
            player: Player to check ownership against
            
        Returns:
            True if this piece is owned by the specified player,
            False otherwise
        """
        return player == self._owner
    
    def get_strength(self) -> int:
        """
        Gets the strength of this piece.
        Normally equal to rank, but reduced to 0 if in opponent's trap.
        
        Returns:
            Strength of piece, 0 when in an opponent's Trap
        """
        # if in traps
        if self._square.is_trap() and not self._square.is_owned_by(self._owner):
            return 0
        
        return self._rank
    
    def can_swim(self) -> bool:
        """
        Indicates if this piece can swim.
        
        Returns:
            True if piece can swim, False otherwise
        """
        return False
    
    def can_leap_horizontally(self) -> bool:
        """
        Indicates if this piece can leap horizontally over water.
        
        Returns:
            True if piece can leap horizontally, False otherwise
        """
        return False
    
    def can_leap_vertically(self) -> bool:
        """
        Indicates if this piece can leap vertically over water.
        
        Returns:
            True if piece can leap vertically, False otherwise
        """
        return False
    
    def move(self, to_square: 'Square'):
        """
        Moves piece to a new square.
        If moves into an opponent's Den, it captures the Den.
        
        Args:
            to_square: Square to move to
        """
        self._square = to_square
        
        if self._square.is_den() and not self._square.is_owned_by(self._owner):
            self._owner.capture_den()
    
    def can_defeat(self, target: 'Piece') -> bool:
        """
        Checks if this piece can defeat a target piece.
        Piece can defeat another if it has higher or equal strength,
        or if it is a Rat attacking an Elephant.
        
        Args:
            target: The piece to check defeat against
            
        Returns:
            True if piece can defeat target, False otherwise
        """
        if not target.is_owned_by(self._owner) and (
            self.get_strength() >= target.get_strength()
            or (self._rank == 1 and target._rank == 8)  # Rat 1 atk Elephant 8
        ):
            return True
        
        return False
    
    def be_captured(self):
        """
        Captures this piece by setting its square to None, removing
        its ownership, and decreasing the owner's piece count.
        """
        self._square = None
        self._owner.lose_one_piece()
        self._owner = None
