package com.chess.pieces;

import com.chess.Cell;
import com.chess.Piece;
import com.chess.PieceColor;
import com.chess.Type;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super('p', Type.PAWN, color, null);
    }

    @Override
    public boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board) {
        int initRank = initialPos.getRank();
        int initFile = initialPos.getFile();
        int finalRank = finalPos.getRank();
        int finalFile = finalPos.getFile();
        int direction = getColor() == PieceColor.WHITE ? -1 : 1;

        return finalRank == initRank + direction
                && (finalFile == initFile
                && !finalPos.isOccupied() // moving forward
                || Math.abs(finalFile - initFile) == 1
                && finalPos.isOccupied()
                && finalPos.getPiece().getColor() != getColor()) // capturing diagonally

                || finalRank == initRank + 2 * direction
                && finalFile == initFile
                && initRank == (direction == -1 ? 6 : 1)
                && !finalPos.isOccupied()
                && !board[finalRank - direction][finalFile].isOccupied(); // initial double move

    }
}
