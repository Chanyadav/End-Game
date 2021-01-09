/*
 * @author Chandan Yadav
 * version 1.0
 * @since 12/16/2020
 */
public class Pawn extends ChessPiece {

	public Pawn(String color) {
		this.color = color;
		this.validMove = true;
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	public String toString() {
		return color.charAt(0) + "p";
	}

	@Override
	public boolean validateMove(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol,
			boolean flag) {
		// TODO Auto-generated method stub

		if (color.equals("white")) {
			if (currentRow > newRow) {
				return false;
			}
		} else {
			if (newRow > currentRow) {
				return false;
			}
		}

		if (currentCol == newCol) {
			// Not taking a piece

			if (color.equals("white")) {
				if (board[currentRow + 1][currentCol] != null) {

					return false;
				}
			} else {
				if (board[currentRow - 1][currentCol] != null) {

					return false;
				}
			}

			if (Math.abs(newRow - currentRow) > 1) {
				return false;
			}

			if (flag && ChessBoard.captureExists ) {
				System.out.println("Capture Exists please capture the opponent pawn");
				return false;
			}
			if (flag)
				ChessBoard.pawnstrike = false;

		}

		else {
			// Taking a piece
			if (Math.abs(newCol - currentCol) != 1 || Math.abs(newRow - currentRow) != 1) {

				return false;
			}

			if (board[newRow][newCol] == null) {

				return false;
			}

			if (flag) {
				ChessBoard.pawnstrike = true;
			}

		}

		board[currentRow][currentCol].validMove = true;
		return true;

	}
}
