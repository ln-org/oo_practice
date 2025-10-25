"""
表示丛林游戏中的一个玩家。

玩家由玩家编号（0或1）标识。
每个玩家都有剩余棋子的数量，并且可以占领对手的兽穴。
"""


class Player:
    """
    丛林游戏中的玩家。
    
    Attributes:
        name: 玩家姓名
        player_number: 唯一标识符（0或1）
        is_opponent_den_captured: 对手兽穴是否已被占领
        num_of_pieces: 玩家当前拥有的棋子数量
    """
    
    def __init__(self, name: str, player_number: int):
        """
        使用指定的姓名和玩家编号构造一个玩家。
        
        Args:
            name: 玩家姓名
            player_number: 唯一标识符，应该是0或1
            
        Raises:
            ValueError: 如果player_number不是0或1
            ValueError: 如果name是None或空字符串
        """
        # 检查玩家编号有效性
        if player_number not in (0, 1):
            raise ValueError(
                f"player_number should be 0 or 1, but: {player_number}"
            )
        
        # 检查玩家姓名有效性
        if name is None or name == "":
            raise ValueError("Player name cannot be None or empty.")
        
        self._name = name
        self._player_number = player_number
        self._is_opponent_den_captured = False
        self._num_of_pieces = 0
    
    def get_name(self) -> str:
        """
        获取玩家姓名。
        
        Returns:
            玩家姓名
        """
        return self._name
    
    def get_player_number(self) -> int:
        """
        获取玩家编号。
        
        Returns:
            玩家编号
        """
        return self._player_number
    
    def capture_den(self):
        """
        占领对手的兽穴。将对手兽穴的状态
        is_opponent_den_captured设置为True。
        """
        self._is_opponent_den_captured = True
    
    def has_captured_den(self) -> bool:
        """
        检查对手的兽穴是否已被占领。
        
        Returns:
            如果对手的兽穴被占领则返回True，否则返回False
        """
        return self._is_opponent_den_captured
    
    def has_pieces(self) -> bool:
        """
        检查玩家是否还有棋子。
        
        Returns:
            如果玩家还有棋子则返回True，否则返回False
        """
        return self._num_of_pieces > 0
    
    def gain_one_piece(self):
        """将玩家拥有的棋子数量增加一个。"""
        self._num_of_pieces += 1
    
    def lose_one_piece(self):
        """
        将玩家拥有的棋子数量减少一个。
        
        Raises:
            RuntimeError: 如果玩家没有剩余棋子可以失去
        """
        if self._num_of_pieces > 0:
            self._num_of_pieces -= 1
        else:
            raise RuntimeError("No pieces left to lose.")
