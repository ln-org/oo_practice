package jungle;

/**
 * Reresents a player in a Jungle game.
 * 
 * A player is identity by a player number (0 or 1).
 * Each player have a count of remaining pieces and
 * can capture the opponent's Den.
 */
public class Player {
    private String name = null;
    private int playerNumber = -1;
    private boolean isOpponentDenCaptured = false;
    private int numOfPieces = 0;

    /**
     * Constructs a Player with a specified name and player number.
     * 
     * @param name          name of player
     * @param playerNumber  unique identifier, should be either 0 or 1
     * @throws IllegalArgumentException if {@code playerNumber} is not 0 or 1.
     * @throws IllegalArgumentException if {@code name} is {@code null} or an
     *                                  empty string.
     */
    public Player(String name, int playerNumber) {
        // check playNumber valid
        if (
            playerNumber != 0 && playerNumber != 1
        ) {
            throw new IllegalArgumentException(
                String.format(
                    "playerNumber should be 0 or 1, but: %d",
                    playerNumber
                )
            );
        }

        // check playName valid
        if (
            name == null || name.isEmpty()
        ) {
            throw new IllegalArgumentException(
                "Player name cannot be null or empty."
            );
        }

        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * Gets player name.
     * 
     * @return player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets player number.
     * 
     * @return player number
     */
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    /**
     * Captures the opponent's Den. Sets status of opponent's Den
     * {@code isOpponentDenCaptured} to {@code true}.
     */
    public void captureDen() {
        this.isOpponentDenCaptured = true;
    }

    /**
     * Checks opponent's Den has been captured.
     * 
     * @return true if opponent's Den is captured, false otherwise
     */
    public boolean hasCapturedDen() {
        return isOpponentDenCaptured;
    }

    /**
     * Checks if player has pieces.
     * 
     * @return true player has pieces, false otherwise
     */
    public boolean hasPieces() {
        return numOfPieces > 0;
    }

    /**
     * Increases number of pieces the player has by one.
     */
    public void gainOnePiece() {
        numOfPieces++;
    }

    /**
     * Decreases the number of pieces the player has by one.
     * 
     * @throws IllegalStateException if the player has no pieces left to lose.
     */
    public void loseOnePiece() {
        if (numOfPieces > 0) {
            numOfPieces--;
        } else {
            throw new IllegalStateException("No pieces left to lose.");
        }
    }
}
