package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class King extends Piece {

    private boolean moved = false;

    public King(PieceColor color) {
        super('k', Type.KING, color, null);
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved() {
        moved = true;
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        // Check if the move is a valid one square move
        int rankDiff = Math.abs(finalPos.getRank() - initialPos.getRank());
        int fileDiff = Math.abs(finalPos.getFile() - initialPos.getFile());
        return (rankDiff == 1 || rankDiff == 0) && (fileDiff == 1 || fileDiff == 0);
    }
}
