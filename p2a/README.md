# Jungle Game

This project is a Java implementation of the board game **Jungle**. The game involves two players who control various animal pieces with different strengths and abilities, aiming to capture the opponent's Den while avoiding or defeating the opponent's pieces.

## Project Structure
```
CS5001-p2-jungle
├── README.md
├── Tests
│   ├── JUnitBuildTest.sh
│   ├── JUnitRunTest.sh
│   ├── buildSubmission.sh
│   ├── lib
│   │   └── junit-platform-console-standalone-1.11.1.jar
│   ├── mytest
│   │   └── Test
│   │       ├── MyTest.java
│   │       └── info.sh
│   └── practical.config
└── src
    └── jungle
        ├── Coordinate.java
        ├── Game.java
        ├── IllegalMoveException.java
        ├── Player.java
        ├── pieces
        │   ├── Lion.java
        │   ├── Piece.java
        │   ├── Rat.java
        │   └── Tiger.java
        └── squares
            ├── Den.java
            ├── PlainSquare.java
            ├── Square.java
            ├── Trap.java
            └── WaterSquare.java

```

## Classes Overview
### Game
The `Game` class is the core of the project, managing the game board, pieces, and turn-based logic. It handles:
- Setting up the board with different types of squares (e.g., water, traps, dens).
- Adding pieces for each player at the start of the game.
- Validating and executing moves.
- Determining the winner and checking if the game is over.

### Player
The `Player` class represents each player, including their pieces, score, and captured status.

### Coordinate
The `Coordinate` class represents positions on the board and includes methods for comparison and hash generation.

### IllegalMoveException
The `IllegalMoveException` class is a custom exception for handling illegal moves in the game.

### Pieces
- `Piece`: The base class for all game pieces, including methods for movement and capturing logic.
- `Lion`, `Tiger`, `Rat`: Specific animal pieces with unique abilities such as leaping or swimming.

### Squares
- `Square`: The base class for all board squares.
- `Den`, `Trap`, `WaterSquare`, `PlainSquare`: Specific types of squares with unique properties, such as Dens that players must protect and Traps that weaken opponent pieces.


## Explanations to Some Design
### Legal Moves and Direction Exploration
The methods `getLegalMoves` and `exploreDirection` in the `Game` class handle the core logic for determining valid moves for each piece in the Jungle (Dou Shou Qi) game. Here’s an overview of the design rationale and specific behaviors implemented in these methods.

#### `getLegalMoves(int row, int col)`

The `getLegalMoves` method generates a list of valid moves for a piece located at a specified position (`row`, `col`). This method first verifies if the piece belongs to the player whose turn it currently is and whether the game is still ongoing. If either condition is not met, it returns an empty list, indicating that no moves are allowed.

- **Player and Turn Verification**: The method retrieves the piece at the specified coordinates and checks if it belongs to the player whose turn it is. If the piece does not match the current player, the method immediately returns an empty list.
  
- **Four Directions of Movement**: The method defines four directions (up, down, left, right) as possible moves. Each direction is represented as a `Coordinate` offset (e.g., `(-1, 0)` for upward movement).
  
- **Exploration of Legal Moves**: For each direction, `exploreDirection` is called to check if the piece can move in that direction. If `exploreDirection` returns a valid coordinate, it is added to the list of legal moves. Finally, the method returns the complete list of coordinates where the piece can legally move.

#### `exploreDirection(int row, int col, int rowDirection, int colDirection)`

The `exploreDirection` method determines if there is a valid square for the piece to move to in a specified direction. It considers various movement restrictions and abilities based on the piece type and board features (such as water or traps).

1. **Direction Validation**: The method only allows movement along one axis at a time, ensuring that the direction is either horizontal or vertical (no diagonal moves). If the input direction is invalid, an `IllegalArgumentException` is thrown.

2. **Adjacent Move Check**: The method first checks if the piece can move to the adjacent square in the specified direction. A move is valid if:
   - The square is either a land square or a water square (if the piece can swim, like the Rat).
   - The destination square does not contain an opponent’s piece that is too strong to be defeated by the current piece.

3. **Leaping Over Water**: For specific pieces, like the Lion and Tiger, the method checks if they can leap over water squares. This leap is only permitted if:
   - The adjacent square in the direction is a water square with no occupying piece.
   - The piece has the ability to leap in the specified direction (either horizontally or vertically).
   - The destination square after leaping is not water and is either empty or contains a piece that the leaping piece can defeat.

4. **Step-by-Step Exploration for Leaping**: If the piece can leap, the method iteratively moves further along the specified direction, checking each square until it finds a valid landing square or encounters an obstacle:
   - If an intermediate water square contains a piece, the leap is halted.
   - If a non-water square is reached, and it is either empty or contains a weaker piece, it is considered a valid landing square.

5. **Exception Handling**: Any out-of-bounds coordinates are caught by an `IndexOutOfBoundsException`, which ensures that attempts to access invalid board coordinates are safely handled.

This design allows flexibility for different piece abilities while adhering to the game rules. The use of step-by-step exploration in `exploreDirection` ensures that complex moves, such as leaping over water, are handled appropriately.


## Additional Tests

The `MyTest` class contains unit tests to validate specific rules and edge cases in the Jungle game. Key tests include:

### 1. `testInvalidPlayerName`
Checks that creating a `Player` with an invalid name (`null` or empty) throws an `IllegalArgumentException`. Ensures that players have valid names.

### 2. `testLosePieceBoundary`
Verifies that attempting to reduce a player’s piece count below zero throws an `IllegalStateException`. This enforces the boundary that players cannot have negative pieces.

### 3. `testCannotLeapOverPieceInWater`
Tests that `Tiger` and `Lion` cannot leap over water if a water square is occupied by an opponent's piece (e.g., a `Rat`):
- Without obstacles, both pieces initially have 4 legal moves.
- After placing `Rat` pieces in the water as obstacles:
  - The `Tiger`'s legal moves reduce to 2 (up and down only).
  - The `Lion`'s legal moves reduce to 3 (up, down, and left).

### Running Tests
Change directory to CS5001-p2-jungle directory, and use Command Line
```
stacscheck Tests
```

## Getting Started
### 1. Compile :
Change directory to CS5001-p2-jungle directory, and use Command Line
```
find src -name "*.java" | xargs javac -d out
```

### 2. Usage
To start a game, create instances of `Player` and `Game` in a Java environment. For example:
``` java
import jungle.*;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("p0", 0);
        Player player2 = new Player("p1", 1);

        Game game = new Game(player1, player2);
        game.addStartingPieces();

        // Example move (with appropriate validation)
        game.move(2, 0, 3, 0); // Move a piece from (2, 0) to (3, 0)

        if (game.isGameOver()) {
            System.out.println("The winner is: " + game.getWinner().getName());
        }
    }
}

```

### 3. Example Gameplay

- Initialize a game with two players.
- Use the move() method to control pieces on the board.
- Capture the opponent's Den or all of their pieces to win the game.