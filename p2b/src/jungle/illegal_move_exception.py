"""
表示在丛林游戏中尝试进行非法移动时抛出的异常。
此异常继承了RuntimeError，用于处理无效的移动，
例如移动到无效的方格、尝试执行游戏规则不允许的动作。
"""


class IllegalMoveException(RuntimeError):
    """
    丛林游戏中非法移动的异常。
    
    当玩家尝试进行违反游戏规则的移动时抛出此异常。
    """
    
    def __init__(self, message: str):
        """
        使用指定的详细信息构造一个IllegalMoveException。
        
        Args:
            message: 描述异常原因的信息
        """
        super().__init__(message)
