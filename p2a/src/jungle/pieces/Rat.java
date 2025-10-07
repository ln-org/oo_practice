package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a Rat piece in Jungle game.
 * Rat is the weakest piece (rank 1) but has ability to swim.
 */
public class Rat extends Piece {

    /**
     * Constructs a Rat with owner and square.
     * Rat has a rank of 1.
     * 
     * @param owner     player who owns this Rat
     * @param square    square occupied by this Rat
     */
    public Rat(Player owner, Square square) {
        super(owner, square, 1);
    }

    /**
     * Indicates that the Rat can swim.
     * 
     * @return true
     */
    @Override
    public boolean canSwim() {
        return true;
    }
}
