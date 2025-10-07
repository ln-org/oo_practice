"""
Represents a coordinate on the Jungle game board.
Each coordinate consists of a row and column index, which are
used to locate squares on the game board.
"""


class Coordinate:
    """
    A coordinate on the game board.
    
    Attributes:
        row_index: The row index of the coordinate
        col_index: The column index of the coordinate
    """
    
    _HASH_MULTIPLIER = 10
    
    def __init__(self, row: int, col: int):
        """
        Constructs a Coordinate with the row and column indexes.
        
        Args:
            row: Row index of the coordinate
            col: Column index of the coordinate
        """
        self._row_index = row
        self._col_index = col
    
    def row(self) -> int:
        """
        Gets the row index of coordinate.
        
        Returns:
            Row index
        """
        return self._row_index
    
    def col(self) -> int:
        """
        Gets the column index of coordinate.
        
        Returns:
            Column index
        """
        return self._col_index
    
    def __eq__(self, other) -> bool:
        """
        Checks if this coordinate is equal to another object.
        Two coordinates are considered equal if they have same
        row and column index.
        
        Args:
            other: The object to compare with
            
        Returns:
            True if other is a Coordinate with same row and column indexes,
            False otherwise
        """
        if self is other:
            return True
        
        if not isinstance(other, Coordinate):
            return False
        
        return self.row() == other.row() and self.col() == other.col()
    
    def __hash__(self) -> int:
        """
        Generates hash code for this coordinate based on its row
        and column indexes.
        
        Returns:
            Hash code for this coordinate
        """
        return self.row() * self._HASH_MULTIPLIER + self.col()
    
    def __str__(self) -> str:
        """
        Returns a string representation of this coordinate.
        
        Returns:
            A string in the format "row: <row>, col: <col>"
        """
        return f"row: {self.row()}, col: {self.col()}"
