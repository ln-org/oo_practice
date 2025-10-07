"""
Represents an exception thrown when illegal move is attempted in Jungle.
This exception extends RuntimeError and is used to handle
invalid moves, such as moving to an invalid square, attempting an action
that is not allowed by the game rules.
"""


class IllegalMoveException(RuntimeError):
    """
    Exception for illegal moves in Jungle game.
    
    This exception is raised when a player attempts to make a move
    that violates the game rules.
    """
    
    def __init__(self, message: str):
        """
        Constructs an IllegalMoveException with a specified detail message.
        
        Args:
            message: Message describing the reason for the exception
        """
        super().__init__(message)
