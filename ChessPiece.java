/*
 * @author Chandan Yadav
 * version 1.0
 * @since 12/16/2020
 */
public abstract class ChessPiece {
	public String color;
	public boolean validMove;

	public abstract String getColor();

	public abstract boolean validateMove(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol,
			boolean flag);

}
