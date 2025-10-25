"""
表示丛林游戏棋盘上的水域方格。
水域方格是一种特殊类型，只有老鼠可以进入，
某些特定棋子可以跳跃通过。
"""

from .square import Square


class WaterSquare(Square):
    """
    游戏棋盘上的水域方格。
    
    只有老鼠可以进入水域方格。老虎和狮子可以跳跃通过它们。
    """
    
    def __init__(self):
        """构造一个无拥有者的水域方格。"""
        super().__init__(None)
    
    def is_water(self) -> bool:
        """
        指示该方格是水域方格。
        
        Returns:
            True，因为该方格是水域方格
        """
        return True
    
    def is_den(self) -> bool:
        """
        指示该方格不是兽穴。
        
        Returns:
            False
        """
        return False
    
    def is_trap(self) -> bool:
        """
        指示该方格不是陷阱。
        
        Returns:
            False
        """
        return False
