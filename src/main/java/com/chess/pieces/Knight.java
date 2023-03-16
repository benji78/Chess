package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        super('n', Type.KNIGHT, color, null);
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        // Check if the move is a valid one square move
        int rankDiff = Math.abs(finalPos.getRank() - initialPos.getRank());
        int fileDiff = Math.abs(finalPos.getFile() - initialPos.getFile());
        return ((rankDiff == 1 && fileDiff == 2) || (rankDiff == 2 && fileDiff == 1));
    }
}
