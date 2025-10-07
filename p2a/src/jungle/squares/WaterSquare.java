package jungle.squares;

/**
 * Represents a Water square on the Jungle game board.
 * A Water square is a special type only rat can enter,
 * some specific pieces can leap.
 */
public class WaterSquare extends Square {

    /**
     * Constructs a WaterSquare with no owner.
     */
    public WaterSquare() {
        super(null);
    }

    /**
     * Indicates that this square is a Water square.
     * 
     * @return true, as this square is a Water square
     */
    @Override
    public boolean isWater() {
        return true;
    }
}
