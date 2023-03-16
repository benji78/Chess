package com.chess;

public class Cell {

    private final int rank; // row index
    private final int file; // column index
    private Piece piece; // the piece on the cell

    public Cell(int rank, int file) {
        this.rank = rank;
        this.file = file;
        piece = null;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece newPiece) {
        piece = newPiece;
        piece.setCell(this);
    }

    public void removePiece() {
        piece = null;
    }

    public String getFEN() {
        String fen;

        // check if the cell is empty
        if (piece == null) {
            fen = "";
        } else {
            // get the FEN notation for the piece
            fen = switch (piece.getType()) {
                case PAWN -> (piece.getColor() == PieceColor.WHITE) ? "P" : "p";
                case KNIGHT -> (piece.getColor() == PieceColor.WHITE) ? "N" : "n";
                case BISHOP -> (piece.getColor() == PieceColor.WHITE) ? "B" : "b";
                case ROOK -> (piece.getColor() == PieceColor.WHITE) ? "R" : "r";
                case QUEEN -> (piece.getColor() == PieceColor.WHITE) ? "Q" : "q";
                case KING -> (piece.getColor() == PieceColor.WHITE) ? "K" : "k";
            };
        }
        return fen;
    }
}