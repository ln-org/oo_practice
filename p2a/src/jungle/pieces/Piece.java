package jungle.pieces;

import jungle.Player;
import jungle.squares.Square;

/**
 * Represents a piece in Jungle game.
 * Each piece has an owner, occupies a board square, has a rank from
 * 1 to 8, which determines its strength (could impact by square),
 * some of the pieces have special abilities, such as swimming or
 * leaping.
 */
public class Piece {
    private Player owner = null;
    private Square square = null;
    private int rank = 0;

    /**
     * Constructs a Piece with owner, initial square, and rank.
     * 
     * @param owner     player who owns this piece
     * @param square    initial square occupied by this piece
     * @param rank      rank of this piece
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        owner.gainOnePiece();
    }

    /**
     * Checks if this piece is owned by the specified player.
     * 
     * @param player player to check ownership against
     * @return  true if this piece is owned by the specified player,
     *          false otherwise
     */
    public boolean isOwnedBy(Player player) {
        return player.equals(this.owner);
    }

    /**
     * Gets the strength of this piece.
     * Nomally equal to rank, but reduce to 0 if in opponent's trap.
     * 
     * @return strength of piece, 0 when in an opponent's Trap
     */
    public int getStrength() {
        // if in traps
        if (
            this.square.isTrap()
            && !this.square.isOwnedBy(this.owner)
        ) {
            return 0;
        }

        return this.rank;
    }

    /**
     * Indicates if this piece can swim.
     * 
     * @return true if piece can swim, false otherwise
     */
    public boolean canSwim() {
        return false;
    }

    /**
     * Indicates if this piece can leap horizontally over water.
     * 
     * @return true if piece can leap horizontally, false otherwise
     */
    public boolean canLeapHorizontally() {
        return false;
    }

    /**
     * Indicates if this piece can leap vertically over water.
     * 
     * @return true if piece can leap vertically, false otherwise
     */
    public boolean canLeapVertically() {
        return false;
    }

    /**
     * Moves piece to a new square.
     * If moves into an opponent's Den, it captures the Den.
     * 
     * @param toSquare square to move
     */
    public void move(Square toSquare) {
        this.square = toSquare;

        if (
            this.square.isDen()
            && !this.square.isOwnedBy(owner)
        ) {
            this.owner.captureDen();
        }
    }

    /**
     * Checks if this piece can defeat a target piece.
     * Piece can defeat another if it has higher or equal strength,
     * or if it is a Rat attacking an Elephant.
     * 
     * @param target the piece to check defeat against
     * @return true if piece can defeat target, false otherwise
     */
    public boolean canDefeat(Piece target) {
        if (
            !target.isOwnedBy(this.owner)
            && (
                this.getStrength() >= target.getStrength()
                || this.rank == 1 && target.rank ==  8 // Rat 1 atk Elephant 8
            )
        ) {
            return true;
        }

        return false;
    }

    /**
     * Captures this piece by setting its square to null, removing
     * its ownership, and decreasing the owner's piece count.
     * 
     */
    public void beCaptured() {
        this.square = null;
        this.owner.loseOnePiece();
        this.owner = null;
    }
}
