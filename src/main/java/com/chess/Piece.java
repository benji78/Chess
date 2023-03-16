package com.chess;

//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.Objects;

public abstract class Piece {
    private final char name;
    private final PieceColor color;
    private final Type type;
    private Cell cell;
//    private BufferedImage img;

    public Piece(char name, Type type, PieceColor color, String img) {
        this.name = color == PieceColor.WHITE ? Character.toUpperCase(name) : name;
        this.type = type;
        this.color = color;
        // the position is set with Cell.setPiece() when it is put on the board
//        try {
//            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResource(img)));
//        } catch (IOException e) {
//            System.out.println("File not found: " + e.getMessage());
//        }
    }


    public PieceColor getColor() {
        return color;
    }

//    public Image getImage() {
//        return img;
//    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        cell = newCell;
    }

    public char getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public abstract boolean isLegalMove(Cell initialPos, Cell finalPos, Cell[][] board);

    public boolean isValidDiagonalMove(Cell startCell, Cell endCell, Cell[][] board) {
        int fileDistance = Math.abs(endCell.getFile() - startCell.getFile());
        int rankDistance = Math.abs(endCell.getRank() - startCell.getRank());
        // Check if it is a diagonal move
        if (fileDistance != rankDistance) {
            return false;
        }

        // Check if the path between the start and end cells is clear
        int fileDirection = endCell.getFile() > startCell.getFile() ? 1 : -1;
//        int fileDirection = Integer.signum(endCell.getFile() - startCell.getFile());
        int rankDirection = endCell.getRank() > startCell.getRank() ? 1 : -1;
//        int rankDirection = Integer.signum(endCell.getRank() - startCell.getRank());
        int file = startCell.getFile() + fileDirection;
        int rank = startCell.getRank() + rankDirection;
        while (file != endCell.getFile() && rank != endCell.getRank()) {
            if (board[file][rank].isOccupied()) {
                return false;
            }
            file += fileDirection;
            rank += rankDirection;
        }

        // Check if the end cell is empty or occupied by an opponent's piece
        Piece endPiece = endCell.getPiece();
        return endPiece == null || endPiece.getColor() != startCell.getPiece().getColor();
    }

    public boolean isValidStraightMove(Cell start, Cell end, Cell[][] board) {
        if (start == end) {
            return false;
        }
        if (start.getRank() == end.getRank()) { // horizontal move
            int direction = Integer.signum(end.getFile() - start.getFile());
            for (int file = start.getFile() + direction; file != end.getFile(); file += direction) {
                if (board[start.getRank()][file].isOccupied()) {
                    return false;
                }
            }
            return true;
        }
        if (start.getFile() == end.getFile()) { // vertical move
            int direction = Integer.signum(end.getRank() - start.getRank());
            for (int rank = start.getRank() + direction; rank != end.getRank(); rank += direction) {
                if (board[rank][start.getFile()].isOccupied()) {
                    return false;
                }
            }
            return true;
        } // not a straight move
        return false;
    }
}
