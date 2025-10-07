"""
Represents main game logic and state for Jungle game.
Manages the game board, players, pieces, turns, and game rules.
"""

from typing import List, Optional, Dict
from .player import Player
from .coordinate import Coordinate
from .illegal_move_exception import IllegalMoveException
from .pieces.piece import Piece
from .pieces.rat import Rat
from .pieces.tiger import Tiger
from .pieces.lion import Lion
from .squares.square import Square
from .squares.plain_square import PlainSquare
from .squares.water_square import WaterSquare
from .squares.den import Den
from .squares.trap import Trap


class Game:
    """
    Main game class for Jungle (Dou Shou Qi).
    
    Manages the game board, players, pieces, and game rules.
    """
    
    # Board height (rows)
    HEIGHT = 9
    
    # Board width (columns)
    WIDTH = 7
    
    # Rows with water squares
    WATER_ROWS = [3, 4, 5]
    
    # Columns with water squares
    WATER_COLS = [1, 2, 4, 5]
    
    # Column index of the Dens
    DEN_COL = 3
    
    def __init__(self, p0: Player, p1: Player):
        """
        Constructs a Game instance with two players.
        Initializes game board with board squares and sets the turn to
        the first player.
        
        Args:
            p0: First player
            p1: Second player
        """
        self._p0 = p0
        self._p1 = p1
        self._players = [p0, p1]
        
        # Initialize game board
        self._squares: List[List[Square]] = [[None] * self.WIDTH 
                                               for _ in range(self.HEIGHT)]
        self._square_to_piece: Dict[Square, Piece] = {}
        
        self._initialize_board_squares()
        self._current_turn = 0
    
    def _initialize_board_squares(self):
        """
        Initializes the game board by setting each square type (Plain,
        Water, Den, Trap) at their respective positions.
        The board is a 9x7 grid where certain squares are designated as
        water squares, and each player's Den and Trap squares are placed
        in specific locations.
        """
        # Set all squares to PlainSquare initially
        for row in range(self.HEIGHT):
            for col in range(self.WIDTH):
                self._squares[row][col] = PlainSquare()
        
        # Set WaterSquares based on predefined rows and columns
        for row in self.WATER_ROWS:
            for col in self.WATER_COLS:
                self._squares[row][col] = WaterSquare()
        
        # Set dens and traps for each player
        # Set p0 den and traps
        p0_den_row = 0
        self._squares[p0_den_row][self.DEN_COL] = Den(self._p0)
        self._squares[p0_den_row][self.DEN_COL - 1] = Trap(self._p0)
        self._squares[p0_den_row][self.DEN_COL + 1] = Trap(self._p0)
        self._squares[p0_den_row + 1][self.DEN_COL] = Trap(self._p0)
        
        # Set p1 den and traps
        p1_den_row = 8
        self._squares[p1_den_row][self.DEN_COL] = Den(self._p1)
        self._squares[p1_den_row][self.DEN_COL - 1] = Trap(self._p1)
        self._squares[p1_den_row][self.DEN_COL + 1] = Trap(self._p1)
        self._squares[p1_den_row - 1][self.DEN_COL] = Trap(self._p1)
    
    def add_starting_pieces(self):
        """
        Adds the starting pieces for both players according to their
        initial positions and ranks.
        """
        initial_piece_configs = [
            # [row, col, rank, player_number]
            # player 0
            [2, 0, 1, 0],
            [1, 5, 2, 0],
            [1, 1, 3, 0],
            [2, 4, 4, 0],
            [2, 2, 5, 0],
            [0, 6, 6, 0],
            [0, 0, 7, 0],
            [2, 6, 8, 0],
            # player 1
            [6, 6, 1, 1],
            [7, 1, 2, 1],
            [7, 5, 3, 1],
            [6, 2, 4, 1],
            [6, 4, 5, 1],
            [8, 0, 6, 1],
            [8, 6, 7, 1],
            [6, 0, 8, 1],
        ]
        
        for piece_config in initial_piece_configs:
            row, col, rank, player_number = piece_config
            self.add_piece(row, col, rank, player_number)
    
    def add_piece(self, row: int, col: int, rank: int, player_number: int):
        """
        Adds a piece at given position on the board.
        
        Args:
            row: Row coordinate of the square on game board
            col: Column coordinate of the square on game board
            rank: Rank of the piece
            player_number: Player number who owns the piece
        """
        player = self.get_player(player_number)
        square = self.get_square(row, col)
        
        if rank == 1:
            piece = Rat(player, square)
        elif rank == 6:
            piece = Tiger(player, square)
        elif rank == 7:
            piece = Lion(player, square)
        else:
            piece = Piece(player, square, rank)
        
        # Put into square_to_piece
        self._square_to_piece[self.get_square(row, col)] = piece
    
    def get_piece(self, row: int, col: int) -> Optional[Piece]:
        """
        Get the piece at a specified position on the board.
        
        Args:
            row: Row coordinate on game board
            col: Column coordinate on game board
            
        Returns:
            Piece at given coordinate, or None if none exists
        """
        square = self.get_square(row, col)
        return self._square_to_piece.get(square)
    
    def move(self, from_row: int, from_col: int, to_row: int, to_col: int):
        """
        Moves a piece from one position to another.
        
        Args:
            from_row: Row coordinate of piece's current position
            from_col: Column coordinate of piece's current position
            to_row: Row coordinate of the destination position
            to_col: Column coordinate of the destination position
            
        Raises:
            IllegalMoveException: If the destination coordinates is not
                                  legal for the piece
        """
        # Check if destination valid
        if Coordinate(to_row, to_col) not in self.get_legal_moves(from_row, from_col):
            raise IllegalMoveException(
                f"({to_row}, {to_col}) is not legal move"
            )
        
        piece = self.get_piece(from_row, from_col)
        target_piece = self.get_piece(to_row, to_col)
        source_square = self.get_square(from_row, from_col)
        target_square = self.get_square(to_row, to_col)
        
        if target_piece is not None:
            target_piece.be_captured()
        
        # Move
        piece.move(self.get_square(to_row, to_col))
        
        # Update square to piece HashMap
        del self._square_to_piece[source_square]
        self._square_to_piece[target_square] = piece
        
        # Go to the other player's turn
        self._next_turn()
    
    def get_player(self, player_number: int) -> Player:
        """
        Get player based on player number.
        
        Args:
            player_number: Player number (0 or 1)
            
        Returns:
            Player with the specified player number
            
        Raises:
            ValueError: If the player number is invalid
        """
        # Check player_number valid
        try:
            Player("check_player_number", player_number)
        except ValueError as e:
            raise e
        
        return self._p0 if player_number == self._p0.get_player_number() else self._p1
    
    def get_winner(self) -> Optional[Player]:
        """
        Determines the winner of the game.
        A player wins if they capture the opponent's Den or if the
        opponent has no pieces left.
        
        Returns:
            The winning player, or None if there is no winner yet
        """
        if (
            self._p0.has_captured_den()  # p0 captures p1's Den
            or not self._p1.has_pieces()  # or p1 has no piece remaining
        ):
            return self._p0  # p0 win
        
        if (
            self._p1.has_captured_den()  # p1 captures p0's Den
            or not self._p0.has_pieces()  # or p0 has no piece remaining
        ):
            return self._p1  # p1 win
        
        return None
    
    def is_game_over(self) -> bool:
        """
        Checks if the game is over.
        
        Returns:
            True if there is a winner, False otherwise
        """
        return self.get_winner() is not None
    
    def get_square(self, row: int, col: int) -> Square:
        """
        Get the square at a specified coordinate on the game board.
        
        Args:
            row: Row coordinate on board
            col: Column coordinate on board
            
        Returns:
            Square at the specified coordinate
            
        Raises:
            IndexError: If coordinates are outside game board
        """
        if (
            (row < 0 or row > self.HEIGHT - 1)  # exceed height
            or (col < 0 or col > self.WIDTH - 1)  # exceed width
        ):
            raise IndexError(
                f"Coordinate exceed board bounds: ({row}, {col})"
            )
        
        return self._squares[row][col]
    
    def get_legal_moves(self, row: int, col: int) -> List[Coordinate]:
        """
        Get legal moves for a piece at a specified coordinate.
        
        Args:
            row: Current row coordinate
            col: Current column coordinate
            
        Returns:
            A list of legal coordinates where the piece can move
        """
        coordinates = []
        
        this_turn_player = self.get_player(self._get_turn() % 2)
        selected_piece = self.get_piece(row, col)
        
        if (
            selected_piece is None  # no piece at this coordinate
            # or piece is not owned by player who can move this turn
            or not selected_piece.is_owned_by(this_turn_player)
            or self.is_game_over()  # or game is over
        ):
            return coordinates  # return empty list
        
        # 4 potential move directions
        directions = [
            Coordinate(-1, 0),  # upward
            Coordinate(1, 0),   # downward
            Coordinate(0, -1),  # leftward
            Coordinate(0, 1),   # rightward
        ]
        
        # Explore square can move to in each of 4 directions
        for direction in directions:
            coordinate = self._explore_direction(
                row,
                col,
                direction.row(),
                direction.col()
            )
            
            # if found a valid square, add to list
            if coordinate is not None:
                coordinates.append(coordinate)
        
        # return all valid squares in list
        return coordinates
    
    def _explore_direction(
        self,
        row: int,
        col: int,
        row_direction: int,
        col_direction: int
    ) -> Optional[Coordinate]:
        """
        Explores a specified direction from a starting position to find if
        there is a square can move to.
        Handles cases where a piece can move directly to an adjacent square,
        swim through water, or leap over water if the piece has that ability.
        
        Args:
            row: Starting row coordinate
            col: Starting column coordinate
            row_direction: Row direction (e.g., -1 for up, 1 for down)
            col_direction: Column direction (e.g., -1 for left, 1 for right)
            
        Returns:
            Target coordinate if the move is valid, or None if not allowed
            
        Raises:
            ValueError: If the direction is not valid
        """
        # Explore (move) direction can only be horizontally or vertically
        if not (
            ((row_direction == -1 or row_direction == 1) and col_direction == 0)
            or (row_direction == 0 and (col_direction == -1 or col_direction == 1))
        ):
            raise ValueError(
                "Row and Col Direction, one should be -1 or 1, "
                "the other should be 0"
            )
        
        try:
            # Currently explore square
            explore_square = self.get_square(row + row_direction,
                                             col + col_direction)
            
            selected_piece = self.get_piece(row, col)
            destination_piece = self.get_piece(row + row_direction,
                                               col + col_direction)
            
            if (
                # Explore directly adjacent square can move to
                (
                    # Square is not water or piece can swim
                    not explore_square.is_water() or selected_piece.can_swim()
                ) and (
                    # Has a piece, but can defeat
                    (destination_piece is not None
                     and selected_piece.can_defeat(destination_piece))
                    # or doesn't have a piece
                    or destination_piece is None
                )
            ):
                return Coordinate(row + row_direction, col + col_direction)
            elif (
                # Explore square can jump to
                (
                    # Adjacent square is water, and with no piece
                    explore_square.is_water() and destination_piece is None
                ) and (
                    # Selected piece can leap vertically
                    # and currently explore vertical direction
                    (selected_piece.can_leap_vertically() and row_direction != 0)
                    # or selected piece can leap horizontally
                    # and currently explore horizontal direction
                    or (selected_piece.can_leap_horizontally() and col_direction != 0)
                )
            ):
                step = 2
                while True:
                    explore_square = self.get_square(row + row_direction * step,
                                                     col + col_direction * step)
                    destination_piece = self.get_piece(row + row_direction * step,
                                                       col + col_direction * step)
                    
                    # Break explore, can't jump if piece occupies
                    # intervening water
                    if explore_square.is_water() and destination_piece is not None:
                        break
                    
                    # Return square is not water, no piece or can be defeated
                    if (
                        not explore_square.is_water()
                        and (
                            destination_piece is None
                            or selected_piece.can_defeat(destination_piece)
                        )
                    ):
                        return Coordinate(row + row_direction * step,
                                          col + col_direction * step)
                    
                    # Explore next square
                    step += 1
        except IndexError as e:
            import sys
            print(f"Exploring: {e}", file=sys.stderr)
        
        return None
    
    def _get_turn(self) -> int:
        """Get current turn number."""
        return self._current_turn
    
    def _next_turn(self):
        """Advance to next turn."""
        self._current_turn += 1
