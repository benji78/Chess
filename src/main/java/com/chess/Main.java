package com.chess;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Benji's CLI Chess!");
        System.out.println("To play you just need to input a UCI move (ex: 'e2e4' or 'b8c6') and in case of promotion you need to add the piece type at the end like 'e7e8Q'.");
        board.printBoard();
        while (true) {
            System.out.println(board.getTurn() + " to move: ");
            if (board.move(scanner.nextLine())) {
                board.printBoard();
            }
            if (board.isCheckmate()) {
                System.out.println("Checkmate!, " + board.getTurn() + " wins!");
                break;
            } else if (board.isDraw()) {
                System.out.println("Draw!");
                break;
            }
        }
        scanner.close();
    }
}
// @TODO
// castling
// enPassant
// isCheck
// isCheckmate
//   return this.isCheck() && moves().length === 0
// isStalemate
// isDraw
//   return this.isStalemate() || this.insufficientMaterial() || this.fiftyMoveRule()
// JavaFX