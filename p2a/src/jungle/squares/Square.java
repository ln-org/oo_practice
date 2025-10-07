package jungle.squares;

import jungle.Player;

/**
 * Represents a square of the 7 x 9 Jungle game board.
 * Each square can have an owner, and may represent a specific type such as
 * a Den, Trap, Water, or Plain square.
 */
public abstract class Square {
    private Player owner;

    /**
     * Constructs a Square with owner.
     * 
     * @param owner player who owns this square, can be null if no owner
     */
    public Square(Player owner) {
        this.owner = owner;
    }

    /**
     * Checks if this square is owned by the specified player.
     * 
     * @param player player to check ownership against
     * @return true if player owns this square, false otherwise
     */
    public boolean isOwnedBy(Player player) {
        return player.equals(this.owner);
    }

    /**
     * Indicates if this square is a Water square.
     * By default, returns false, is overridden by {@code WaterSquare}.
     * 
     * @return true if this square is a Water square, false otherwise
     */
    public boolean isWater() {
        return false;
    }

    /**
     * Indicates if this square is a Den.
     * By default, returns false, is overridden by by {@code Den}.
     *
     * @return true if this square is a Den, false otherwise
     */
    public boolean isDen() {
        return false;
    }

    /**
     * Indicates if this square is a Trap.
     * By default, returns false, is overridden by {@code Trap}.
     *
     * @return true if this square is a Trap, false otherwise
     */
    public boolean isTrap() {
        return false;
    }
}
