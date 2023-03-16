package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super('b', Type.BISHOP, color, null);
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        return isValidDiagonalMove(initialPos, finalPos, board);
    }
}
