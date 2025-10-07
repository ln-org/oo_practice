package jungle;

/**
 * Represents an exception thrown when illegal move is attempted in Jungle.
 * This exception extends {@code RuntimeException} and is used to handle
 * invalid moves, such as moving to an invalid square, attempting an action
 * that is not allowed by the game rules.
 */
public class IllegalMoveException extends RuntimeException {

    /**
     * Constructs an IllegalMoveException with a specified detail message.
     * 
     * @param message message describing the reason for the exception
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
