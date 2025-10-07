package jungle.squares;

import jungle.Player;

/**
 * Represents a Den square on the Jungle game board.
 * Capturing the opponent's Den results in a victory.
 */
public class Den extends Square {
    /**
     * Constructs a Den with owner.
     * 
     * @param owner player who owns this Den
     */
    public Den(Player owner) {
        super(owner);
    }

    /**
     * Indicates this square is a Den.
     * 
     * @return true
     */
    @Override
    public boolean isDen() {
        return true;
    }
}
