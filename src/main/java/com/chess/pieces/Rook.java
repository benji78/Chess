package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class Rook extends Piece {

    private boolean moved = false;

    public Rook(PieceColor color) {
        super('r', Type.ROOK, color, null);
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved() {
        moved = true;
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        return isValidStraightMove(initialPos, finalPos, board);
    }
}
