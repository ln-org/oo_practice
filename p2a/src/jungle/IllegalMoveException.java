package jungle;

/**
 * 表示在丛林游戏中尝试进行非法移动时抛出的异常。
 * 此异常继承了{@code RuntimeException}，用于处理无效的移动，
 * 例如移动到无效的方格、尝试执行游戏规则不允许的动作。
 */
public class IllegalMoveException extends RuntimeException {

    /**
     * 使用指定的详细信息构造一个IllegalMoveException。
     * 
     * @param message 描述异常原因的信息
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
