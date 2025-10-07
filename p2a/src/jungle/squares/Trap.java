package jungle.squares;

import jungle.Player;

/**
 * Represents a Trap square on the Jungle game board.
 * Trap weakens opponent pieces.
 */
public class Trap extends Square {

    /**
     * Constructs a Trap with owner.
     *
     * @param owner  player who owns this Trap
     */
    public Trap(Player owner) {
        super(owner);
    }

    /**
     * Indicates that this square is a Trap.
     * 
     * @return true, as this square is a Trap
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
