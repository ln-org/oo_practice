package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Lion piece in the Jungle game.
 * Lion can leap both horizontally and vertically over water squares.
 */
public class Lion extends Piece {

    /**
     * Constructs a Lion with the owner and square.
     * The Lion has a rank of 7.
     * 
     * @param owner     the player who owns this Lion
     * @param square    square occupied by this Lion
     */
    public Lion(Player owner, Square square) {
        super(owner, square, 7);
    }

    /**
     * Indicates Lion can leap horizontally.
     * 
     * @return true
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }

    /**
     * Indicates Lion can leap vertically .
     * 
     * @return true
     */
    @Override
    public boolean canLeapVertically() {
        return true;
    }
}
