import java.io.IOException;
import java.util.*;
/*
 * @author Chandan Yadav
 * version 1.0
 * @since 12/16/2020
 */

public class Main {

	public static void main(String[] args) {

		ChessBoard board = new ChessBoard();
		String color = "white";

		System.out.println("Welcome to EverChess" + "\n");
		int count = 1;
		while (true) {
			System.out.println(board);
			System.out.println("Move:" + count + " " + color + " make a move");

			Scanner sc = new Scanner(System.in);
			String move = sc.nextLine();

			try {
				board.performMove(move, color, true);
			} catch (IOException e) {
				// Ask for user input again
				System.out.println("Invalid input!");
				continue;
			}
			ChessPiece[][] oldBoard = board.chessBoard.clone();
			for (int i = 0; i < 8; i++) {
				// Check if any black pawns reached the final destination
				if (oldBoard[0][i] != null && oldBoard[0][i].getColor().equals("black")) {
					System.out.println("Black won the Game");
					return;
				}
			}

			for (int i = 0; i < 8; i++) {
				if (oldBoard[7][i] != null && oldBoard[7][i].getColor().equals("white")) {
					// Check if any white pawns reached the final destination
					System.out.println("White won the Game");
					return;
				}
			}

			if (!board.canAnyPieceMakeAnyMove(colorToggle(color))) {
				// Check if any opponent pawns can move else declare the current player as the
				// winner
				System.out.println(color + " wins the game");
				return;
			}

			board.checkIfCaptureExists(colorToggle(color));
			board.chessBoard = oldBoard;

			if (!ChessBoard.pawnstrike) {

				color = colorToggle(color);
			}

			count++;
		}
	}

	public static String colorToggle(String color) {
		if (color.equals("white"))
			return "black";

		return "white";
	}

}
