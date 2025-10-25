"""
表示丛林游戏棋盘上的普通方格。
"""

from .square import Square


class PlainSquare(Square):
    """没有特殊属性的普通方格。"""
    
    def __init__(self):
        """构造一个没有拥有者的普通方格。"""
        super().__init__(None)

    def is_water(self) -> bool:
        """指示此方格不是水方格。"""
        return False
    
    def is_den(self) -> bool:
        """指示此方格不是兽穴。"""
        return False

    def is_trap(self) -> bool:
        """指示此方格不是陷阱。"""
        return False
