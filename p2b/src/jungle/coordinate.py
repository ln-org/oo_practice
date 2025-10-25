"""
表示丛林游戏棋盘上的一个坐标。
每个坐标由行和列索引组成，用于在游戏棋盘上定位方格。
"""


class Coordinate:
    """
    游戏棋盘上的坐标。
    
    Attributes:
        row_index: 坐标的行索引
        col_index: 坐标的列索引
    """
    
    _HASH_MULTIPLIER = 10
    
    def __init__(self, row: int, col: int):
        """
        使用行和列索引构造一个坐标。
        
        Args:
            row: 坐标的行索引
            col: 坐标的列索引
        """
        self._row_index = row
        self._col_index = col
    
    def row(self) -> int:
        """
        获取坐标的行索引。
        
        Returns:
            行索引
        """
        return self._row_index
    
    def col(self) -> int:
        """
        获取坐标的列索引。
        
        Returns:
            列索引
        """
        return self._col_index
    
    def __eq__(self, other) -> bool:
        """
        检查此坐标是否与另一个对象相等。
        如果两个坐标具有相同的行和列索引，则认为它们相等。
        
        Args:
            other: 要比较的对象
            
        Returns:
            如果other是具有相同行和列索引的坐标则返回True，
            否则返回False
        """
        if self is other:
            return True
        
        if not isinstance(other, Coordinate):
            return False
        
        return self.row() == other.row() and self.col() == other.col()
    
    def __hash__(self) -> int:
        """
        根据此坐标的行和列索引生成哈希码。
        
        Returns:
            此坐标的哈希码
        """
        return self.row() * self._HASH_MULTIPLIER + self.col()
    
    def __str__(self) -> str:
        """
        返回此坐标的字符串表示。
        
        Returns:
            格式为"row: <row>, col: <col>"的字符串
        """
        return f"row: {self.row()}, col: {self.col()}"
