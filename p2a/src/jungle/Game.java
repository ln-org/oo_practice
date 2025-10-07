package jungle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.pieces.Tiger;
import jungle.pieces.Lion;
import jungle.squares.Square;
import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Trap;
import jungle.squares.WaterSquare;

/**
 * Represents main game logic and state for Jungle game.
 * Manages the game board, players, pieces, turns, and game rules.
 */
public class Game {
    /** Board height (rows). */
    public static final int HEIGHT = 9;

    /** Board width (columns). */
    public static final int WIDTH = 7;

    /** Rows with water squares. */
    public static final int[] WATER_ROWS = {3, 4, 5};

    /** Columns with water squares. */
    public static final int[] WATER_COLS = {1, 2, 4, 5};

    /** Column index of the Dens. */
    public static final int DEN_COL = 3;


    private int currentTurn;
    private Player p0, p1;
    private Player[] players = new Player[2];
    private Square[][] squares = new Square[HEIGHT][WIDTH]; // game board
    private HashMap<Square, Piece> squareToPiece
        = new HashMap<Square, Piece>();

    /**
     * Constructs a Game instance with two players.
     * Initializes game board with board squares and sets the turn to
     * the first player.
     * 
     * @param p0 first player
     * @param p1 second player
     */
    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
        players[0] = p0;
        players[1] = p1;

        this.initializeBoardSquares();
        this.currentTurn = 0;
    }

    /**
     * Initializes the game board by setting each square type (Plain,
     * Water, Den, Trap) at their respective positions.
     * The board is a 9x7 grid where certain squares are designated as
     * water squares, and each player's Den and Trap squares are placed
     * in specific locations.
     */
    private void initializeBoardSquares() {
        // set all squares to PlainSquare initially
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                squares[row][col] = new PlainSquare();
            }
        }

        // set WaterSquares based on predefined rows and columns
        for (int row : WATER_ROWS) {
            for (int col : WATER_COLS) {
                squares[row][col] = new WaterSquare();
            }
        }

        // Set dens and traps for each player
        // set p0 den and traps
        int p0DenRow = 0;
        squares[p0DenRow][DEN_COL] = new Den(p0);
        squares[p0DenRow][DEN_COL - 1] = new Trap(p0);
        squares[p0DenRow][DEN_COL + 1] = new Trap(p0);
        squares[p0DenRow + 1][DEN_COL] = new Trap(p0);

        // set p1 den and traps
        int p1DenRow = 8;
        squares[p1DenRow][DEN_COL] = new Den(p1);
        squares[p1DenRow][DEN_COL - 1] = new Trap(p1);
        squares[p1DenRow][DEN_COL + 1] = new Trap(p1);
        squares[p1DenRow - 1][DEN_COL] = new Trap(p1);
    }

    /**
     * Adds the starting pieces for both players according to their
     * initial positions and ranks.
     */
    public void addStartingPieces() {
        int[][] initialPieceConfigs = {
                // {row, col, rank, playerNumber}
                // play 0
                {2, 0, 1, 0},
                {1, 5, 2, 0},
                {1, 1, 3, 0},
                {2, 4, 4, 0},
                {2, 2, 5, 0},
                {0, 6, 6, 0},
                {0, 0, 7, 0},
                {2, 6, 8, 0},
                // play 1
                {6, 6, 1, 1},
                {7, 1, 2, 1},
                {7, 5, 3, 1},
                {6, 2, 4, 1},
                {6, 4, 5, 1},
                {8, 0, 6, 1},
                {8, 6, 7, 1},
                {6, 0, 8, 1},
        };

        for (int[] pieceConig : initialPieceConfigs) {
            int row = pieceConig[0];
            int col = pieceConig[1];
            int rank = pieceConig[2];
            int playerNumber = pieceConig[3];

            addPiece(row, col, rank, playerNumber);
        }
    }

    /**
     * Adds a piece a given position on the board.
     * 
     * @param row          row coordinate of the square on game board.
     * @param col          column coordinate of the square on game board.
     * @param rank         rank of the piece
     * @param playerNumber player number who owns the piece
     */
    public void addPiece(int row, int col, int rank, int playerNumber) {
        Player player = getPlayer(playerNumber);
        Square square = getSquare(row, col);

        Piece piece;
        if (rank == 1) {
            piece = new Rat(player, square);
        } else if (rank == 6) {
            piece = new Tiger(player, square);
        } else if (rank == 7) {
            piece = new Lion(player, square);
        } else {
            piece = new Piece(player, square, rank);
        }

        // put in to squareToPiece
        squareToPiece.put(getSquare(row, col), piece);
    }

    /**
     * Get the piece at a specified position on the board.
     * 
     * @param row row coordinate on game board.
     * @param col column coordinate on game board.
     * @return piece at given coordinate, or null if none exists
     */
    public Piece getPiece(int row, int col) {
        Square square = getSquare(row, col);
        return squareToPiece.get(square);
    }

    /**
     * Moves a piece from one position to another.
     * 
     * @param fromRow row coordinate of piece's current position
     * @param fromCol column coordinate of piece's current position
     * @param toRow   row coordinate of the destination position
     * @param toCol   column coordinate of the destination position
     * @throws IllegalMoveException if the destination coordinates is not
     *                              legal for the piece
     */
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        // check if destination valid
        if (
            !getLegalMoves(fromRow, fromCol)
                .contains(new Coordinate(toRow, toCol))
        ) {
            throw new IllegalMoveException(
                String.format("(%d, %d) is not legal move", toRow, toCol)
            );
        }

        Piece piece = getPiece(fromRow, fromCol);
        Piece targetPiece = getPiece(toRow, toCol);
        Square sourceSquare = getSquare(fromRow, fromCol);
        Square targetSquare = getSquare(toRow, toCol);

        if (targetPiece != null) {
            targetPiece.beCaptured();
        }

        // move
        piece.move(getSquare(toRow, toCol));

        // update square to piece HashMap
        squareToPiece.remove(sourceSquare, piece);
        squareToPiece.put(targetSquare, piece);
        // go to the other player's turn
        nextTurn();
    }

    /**
     * Get player based on player number.
     * 
     * @param playerNumber player number (0 or 1)
     * @return player with the specified player number
     * @throws IllegalArgumentException if the player number is invalid
     */
    public Player getPlayer(int playerNumber) {
        // check playNumber valid, valid number can succed to create a player
        try {
            Player player = new Player("checkPlayerNumber", playerNumber);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return playerNumber == p0.getPlayerNumber() ? p0 : p1;
    }

    /**
     * Determines the winner of the game.
     * A player wins if they capture the opponent's Den or if the
     * opponent has no pieces left.
     * 
     * @return the winning player, or null if there is no winner yet
     */
    public Player getWinner() {
        if (
            p0.hasCapturedDen() // p0 captures p1's Den
            || !p1.hasPieces()  // or p1 has no piece remaining
        ) {
            return p0;          // p0 win
        }

        if (
            p1.hasCapturedDen() // p1 captures p1's Den
            || !p0.hasPieces()  // or p0 has no piece remaining
        ) {
            return p1;          // p1 win
        }

        return null;
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if there is a winner, false otherwise
     */
    public boolean isGameOver() {
        return getWinner() != null;
    }

    /**
     * Get the square at a specified coordinate on the game board.
     * 
     * @param row row coordinate on board.
     * @param col col coordinate on board.
     * @return square at the specified coordinate
     * @throws IndexOutOfBoundsException if coordinates are outside game board
     */
    public Square getSquare(int row, int col) {
        if (
            (row < 0 || row > HEIGHT - 1)   // exceed height
            || (col < 0 || col > WIDTH - 1) // exceed width
        ) {
            throw new IndexOutOfBoundsException(
                String.format("Coordinate exceed board bounds: (%d, %d)", row, col)
            );
        }

        return squares[row][col];
    }

    /**
     * Get legal moves for a piece at a specified coordinate.
     * 
     * @param row current row coordinate
     * @param col current col coordinate
     * @return a list of legal coordinates where the piece can move
     */
    public List<Coordinate> getLegalMoves(int row, int col) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();

        Player thisTurnPlayer = getPlayer(getTrun() % 2);
        Piece selectedPiece = getPiece(row, col);

        if (
            selectedPiece == null   // no piece at this coordinate
            // or piece is not owner by play can move this turn
            || !selectedPiece.isOwnedBy(thisTurnPlayer)
            || isGameOver()         // or game is over
        ) {
            return coordinates;       // return empty list
        }

        // 4 potential move direction
        Coordinate[] directions = {
            new Coordinate(-1, 0),  // upward
            new Coordinate(1, 0),  // downward
            new Coordinate(0, -1),  // leftward
            new Coordinate(0, 1),  // rightward
        };

        // Explore squrare can move to in each of 4 directions
        for (Coordinate direction : directions) {
            Coordinate coordinate = exploreDirection(row,
                                                     col,
                                                     direction.row(),
                                                     direction.col());

            // if found a valid square, add to list
            if (coordinate != null) {
                coordinates.add(coordinate);
            }
        }

        // return all valid square in list
        return coordinates;
    }

    /**
     * Explores a specified direction from a starting position to find if
     * there is a square can move to.
     * Handles cases where a piece can move directly to an adjacent square,
     * swim through water, or leap over water if the piece has that ability.
     * 
     * @param row           starting row coordinate
     * @param col           starting column coordinate
     * @param rowDirection  row direction (e.g., -1 for up, 1 for down)
     * @param colDirection  column direction (e.g., -1 for left, 1 for right)
     * @return target coordinate if the move is valid, or null if not allowed
     * @throws IllegalArgumentException if the direction is not valid
     */
    private Coordinate exploreDirection(int row,
                                        int col,
                                        int rowDirection,
                                        int colDirection) {

        // explore (move) direction only can be horizontally or vertically
        if (
            !((rowDirection == -1 || rowDirection == 1)  && colDirection == 0)
            && !(rowDirection == 0 && (colDirection == -1 || colDirection == 1))
        ) {
            throw new IllegalArgumentException(
                "Row and Col Direction, one should be -1 or 1,"
                + " the other should be 0"
            );
        }

        try {
            // currently explore squre
            Square exploreSqure = getSquare(row + rowDirection,
                                            col + colDirection);

            Piece selectedPiece = getPiece(row, col);
            Piece distinationPiece = getPiece(row + rowDirection,
                                              col + colDirection);

            if (
                // explore directly adjacent square can move to
                (
                    // squre is not water or piece can swim,
                    !exploreSqure.isWater() || selectedPiece.canSwim()
                ) && (
                    // has a piece, but can defeat
                    distinationPiece != null
                    && selectedPiece.canDefeat(distinationPiece)
                    // or doesn't have a piece
                    || distinationPiece == null
                )
            ) {
                return new Coordinate(row + rowDirection, col + colDirection);
            } else if (
                // explore square can jump to
                (
                    // adjacent square is water, and with no piece
                    exploreSqure.isWater() && distinationPiece == null
                ) && (
                    // selected piece can leap vertically
                    // and currentlly explore vertical direction
                    selectedPiece.canLeapVertically() && rowDirection != 0
                    // or selected piece can leap vertically
                    // and currentlly explore horizontal direction
                    || selectedPiece.canLeapHorizontally() && colDirection != 0
                )
            ) {
                int step = 2;
                while (true) {
                    exploreSqure = getSquare(row + rowDirection * step,
                                             col + colDirection * step);
                    distinationPiece = getPiece(row + rowDirection * step,
                                                col + colDirection * step);

                    // break explore, can't jump if piece occupies
                    // intervening water
                    if (
                        exploreSqure.isWater()
                        && distinationPiece != null
                    ) {
                        break;
                    }

                    // reture squre is not water, no piece or can be defeated
                    if (
                        !exploreSqure.isWater()
                        && (
                            distinationPiece == null
                            || selectedPiece.canDefeat(distinationPiece)
                        )
                    ) {
                        return new Coordinate(row + rowDirection * step,
                                              col + colDirection * step);
                    }

                    // explore next square
                    step++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Exploring: " + e.getMessage());
        }
        return null;
    }

    private int getTrun() {
        return this.currentTurn;
    }

    private void nextTurn() {
        this.currentTurn++;
    }
}
