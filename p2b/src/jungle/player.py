"""
Represents a player in a Jungle game.

A player is identified by a player number (0 or 1).
Each player has a count of remaining pieces and
can capture the opponent's Den.
"""


class Player:
    """
    A player in the Jungle game.
    
    Attributes:
        name: The player's name
        player_number: Unique identifier (0 or 1)
        is_opponent_den_captured: Whether opponent's den has been captured
        num_of_pieces: Number of pieces the player currently has
    """
    
    def __init__(self, name: str, player_number: int):
        """
        Constructs a Player with a specified name and player number.
        
        Args:
            name: Name of player
            player_number: Unique identifier, should be either 0 or 1
            
        Raises:
            ValueError: If player_number is not 0 or 1
            ValueError: If name is None or an empty string
        """
        # check player_number valid
        if player_number not in (0, 1):
            raise ValueError(
                f"player_number should be 0 or 1, but: {player_number}"
            )
        
        # check player_name valid
        if name is None or name == "":
            raise ValueError("Player name cannot be None or empty.")
        
        self._name = name
        self._player_number = player_number
        self._is_opponent_den_captured = False
        self._num_of_pieces = 0
    
    def get_name(self) -> str:
        """
        Gets player name.
        
        Returns:
            Player name
        """
        return self._name
    
    def get_player_number(self) -> int:
        """
        Gets player number.
        
        Returns:
            Player number
        """
        return self._player_number
    
    def capture_den(self):
        """
        Captures the opponent's Den. Sets status of opponent's Den
        is_opponent_den_captured to True.
        """
        self._is_opponent_den_captured = True
    
    def has_captured_den(self) -> bool:
        """
        Checks if opponent's Den has been captured.
        
        Returns:
            True if opponent's Den is captured, False otherwise
        """
        return self._is_opponent_den_captured
    
    def has_pieces(self) -> bool:
        """
        Checks if player has pieces.
        
        Returns:
            True if player has pieces, False otherwise
        """
        return self._num_of_pieces > 0
    
    def gain_one_piece(self):
        """Increases number of pieces the player has by one."""
        self._num_of_pieces += 1
    
    def lose_one_piece(self):
        """
        Decreases the number of pieces the player has by one.
        
        Raises:
            RuntimeError: If the player has no pieces left to lose
        """
        if self._num_of_pieces > 0:
            self._num_of_pieces -= 1
        else:
            raise RuntimeError("No pieces left to lose.")
