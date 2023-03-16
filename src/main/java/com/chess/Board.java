package com.chess;

import com.chess.pieces.*;

public class Board {
    private final Cell[][] board;
    private Piece whiteKing, blackKing;
    private PieceColor turn = PieceColor.WHITE;

    public Board() {
        board = new Cell[8][8];
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        initializePieces();
    }

    public PieceColor getTurn() {
        return turn;
    }

    public boolean isCheckmate() {
//        return this.isCheck(turn) && moves().length == 0;
        return false;
    }

    public boolean isDraw() {
        return this.isStalemate() || this.isInsufficientMaterialDraw() || this.fiftyMoveRule();
    }

    public boolean isStalemate() {
        return false;
    }

    public boolean isInsufficientMaterialDraw() {
        return false;
    }

    public boolean fiftyMoveRule() {
        return false;
    }

    public void initializePieces() {
        // Create and place the pawns
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(PieceColor.BLACK));
            board[6][i].setPiece(new Pawn(PieceColor.WHITE));
        }
        // Create and place the rooks
        board[0][0].setPiece(new Rook(PieceColor.BLACK));
        board[0][7].setPiece(new Rook(PieceColor.BLACK));
        board[7][0].setPiece(new Rook(PieceColor.WHITE));
        board[7][7].setPiece(new Rook(PieceColor.WHITE));
        // Create and place the knights
        board[0][1].setPiece(new Knight(PieceColor.BLACK));
        board[0][6].setPiece(new Knight(PieceColor.BLACK));
        board[7][1].setPiece(new Knight(PieceColor.WHITE));
        board[7][6].setPiece(new Knight(PieceColor.WHITE));
        // Create and place the bishops
        board[0][2].setPiece(new Bishop(PieceColor.BLACK));
        board[0][5].setPiece(new Bishop(PieceColor.BLACK));
        board[7][2].setPiece(new Bishop(PieceColor.WHITE));
        board[7][5].setPiece(new Bishop(PieceColor.WHITE));
        // Create and place the queens
        board[0][3].setPiece(new Queen(PieceColor.BLACK));
        board[7][3].setPiece(new Queen(PieceColor.WHITE));
        // Create and place the kings
        board[0][4].setPiece(new King(PieceColor.BLACK));
        blackKing = board[0][4].getPiece();
        board[7][4].setPiece(new King(PieceColor.WHITE));
        whiteKing = board[7][4].getPiece();
    }

    private boolean isValidPosition(String uci) {
        return uci.matches("[a-h][1-8][a-h][1-8][qrbnQRBN]?"); // check if the move is on the board
    }

    private Cell getCell(char file, char rank) {
        return board[7 - (rank - '1')][file - 'a']; // get starting rank and file (0-7)
    }

    public boolean canPromote(Piece piece, String uci) {
        return piece.getType() == Type.PAWN
                && (piece.getColor() == PieceColor.WHITE ? '8' : '1') == uci.charAt(3);
    }

    public boolean move(String uci) {
        if (!isValidPosition(uci)) {
            System.out.println("Invalid move!");
            return false;
        }
        Cell initialPos = getCell(uci.charAt(0), uci.charAt(1));
        Cell finalPos = getCell(uci.charAt(2), uci.charAt(3));

        Piece piece = initialPos.getPiece();
        if (piece == null) {
            System.out.println("Wrong starting position!");
            return false;
        }
        if (piece.getColor() != turn) {
            System.out.println("It's not " + turn.opposite() + "'s turn!");
            return false;
        }
        if (initialPos.equals(finalPos)) {
            System.out.println("You have not moved!");
            return false;
        }
        if (!piece.isLegalMove(initialPos, finalPos, board)) {
            System.out.println("Illegal move!");
            return false;
        }
        if (canPromote(piece, uci)) {
            if (uci.length() != 5) {
                System.out.println("Missing promotion piece");
                return false;
            }
            switch (uci.charAt(4)) { // get promotion piece
                case 'q', 'Q' -> finalPos.setPiece(new Queen(piece.getColor()));
                case 'r', 'R' -> finalPos.setPiece(new Rook(piece.getColor()));
                case 'b', 'B' -> finalPos.setPiece(new Bishop(piece.getColor()));
                case 'n', 'N' -> finalPos.setPiece(new Knight(piece.getColor()));
            }
        } else { // Update the board state
            finalPos.setPiece(initialPos.getPiece());
        }
        initialPos.removePiece();

        // Change the turn to the next player
        turn = (turn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        return true;
    }

    public boolean isCheck(PieceColor color) {
        Cell kingPos = getKing(color).getCell();

        // Check if any opposing piece can attack the king
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = board[rank][file].getPiece();
                if (piece != null && piece.getColor() != color) {
                    if (piece.isLegalMove(board[rank][file], kingPos, board)) {
                        return true;
                    }
                }
            }
        }
        // King is not in check
        return false;
    }

    private Piece getKing(PieceColor color) {
        return color == PieceColor.WHITE ? whiteKing : blackKing;
    }

    public String generateFEN() {
        StringBuilder fen = new StringBuilder();
        int emptySquares;

        // loop through each row
        for (int i = 0; i < 8; i++) {
            emptySquares = 0;
            for (int j = 0; j < 8; j++) {
                String pieceFEN = board[i][j].getFEN();
                if (pieceFEN.equals("")) {
                    emptySquares++;
                } else {
                    // if empty squares count, add count
                    if (emptySquares > 0) {
                        fen.append(emptySquares);
                        emptySquares = 0;
                    }
                    fen.append(pieceFEN);
                }
            }
            // if empty squares count, add count
            if (emptySquares > 0) {
                fen.append(emptySquares);
            }
            // add slash except for last row
            if (i < board.length - 1) {
                fen.append("/");
            }
        }
        return fen.toString();
    }

    public void printBoard() {
        System.out.println("   ________________________");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " |");
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j].getPiece();
                char pieceName = piece == null ? '.' : piece.getName();
                System.out.print(" " + pieceName + " ");
            }
            System.out.println("|");
        }
        System.out.println("   ------------------------");
        System.out.println("    a  b  c  d  e  f  g  h");
    }

//    public void printBoard() { // 4 letters chess board
//        for (char letter = 'a'; letter <= 'h'; letter++) {
//            System.out.print("    " + letter);
//        }
//        System.out.println();
//        for (int i = 0; i < 8; i++) {
//            System.out.print(8 - i + " ");
//            for (int j = 0; j < 8; j++) {
//                Piece piece = board[i][j].getPiece();
//                String pieceName = piece == null ? "----" :
//                        piece.getColor() == PieceColor.WHITE ? piece.getName() : piece.getName();
//                System.out.print(" " + pieceName);
//            }
//            System.out.println();
//        }
//    }
}
