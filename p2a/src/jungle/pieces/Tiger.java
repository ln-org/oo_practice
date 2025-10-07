package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Tiger piece in Jungle game.
 * Tiger can leap horizontally over water squares.
 */
public class Tiger extends Piece {

    /**
     * Constructs a Tiger with owner and square.
     * Tiger has a rank of 6.
     * 
     * @param owner     player who owns this Tiger
     * @param square    square occupied by this Tiger
     */
    public Tiger(Player owner, Square square) {
        super(owner, square, 6);
    }

    /**
     * Indicates that the Tiger can leap horizontally.
     * 
     * @return true
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }
}
