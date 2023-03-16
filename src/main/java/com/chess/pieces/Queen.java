package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        super('q', Type.QUEEN, color, null);
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        return isValidDiagonalMove(initialPos, finalPos, board) || isValidStraightMove(initialPos, finalPos, board);
    }
}
