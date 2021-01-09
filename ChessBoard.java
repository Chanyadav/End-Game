import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @author Chandan Yadav
 * version 1.0
 * @since 12/16/2020
 */
public class ChessBoard {

	public ChessPiece[][] chessBoard = new ChessPiece[8][8];
	public static int strike[] = new int[2];
	public static boolean pawnstrike = false;
	public static boolean captureExists = false;
	public static boolean moveExists = false;
	public static List<String> positions = new ArrayList<>();

	// initialize ChessBoard
	public ChessBoard() {
		for (int i = 0; i < chessBoard.length; i++) {
			for (int j = 0; j < chessBoard[0].length; j++) {
				chessBoard[i][j] = null;

			}
		}

		for (int i = 0; i < 8; i++) {
			chessBoard[1][i] = new Pawn("white");
			positions.add(1 + "" + i);
		}

		for (int i = 0; i < 8; i++) {
			chessBoard[6][i] = new Pawn("black");
			positions.add(6 + "" + i);
		}

	}

	// Perform the given move
	public void performMove(String move, String color, boolean actuallyMove) throws IOException {

		int[] moveArray = parseInput(move);
		for (int i : moveArray) {
			if (i == -1) {
				throw new IOException();
			}
		}
		if (pawnstrike) {
			if (strike[0] == moveArray[0] && strike[1] == moveArray[1] && !ChessBoard.captureExists) {
				System.out.println("Same pawn cannot be moved");
				throw new IOException();
			}
		} 

		if (chessBoard[moveArray[0]][moveArray[1]] == null) {
			// check if a piece exists there
			throw new IOException();
		}

		if (!chessBoard[moveArray[0]][moveArray[1]].getColor().equals(color)) {
			// check if u are moving the same color piece
			throw new IOException();
		}

		if (chessBoard[moveArray[2]][moveArray[3]] != null) { // check if the destination point is not null and has a
			// piece of opposite color
			if (chessBoard[moveArray[2]][moveArray[3]].getColor().equals(color)) {
				throw new IOException();
			}
		}

		if (chessBoard[moveArray[0]][moveArray[1]].validateMove(chessBoard, moveArray[0], moveArray[1], moveArray[2],
				moveArray[3], actuallyMove)) {

			// Switch the two spots on the board because the move was valid
			if (actuallyMove) {
				if (pawnstrike) {
					strike[0] = moveArray[2];
					strike[1] = moveArray[3];
				}

				ChessBoard.positions.remove(moveArray[0] + "" + moveArray[1]);
				ChessBoard.positions.add(moveArray[2] + "" + moveArray[3]);

				chessBoard[moveArray[2]][moveArray[3]] = chessBoard[moveArray[0]][moveArray[1]];
				chessBoard[moveArray[0]][moveArray[1]] = null;
				ChessBoard.moveExists = false;

			}

		} else if (!actuallyMove && !chessBoard[moveArray[0]][moveArray[1]].validMove) {
			return;
		} else {
			throw new IOException();
		}

	}

	// Override the toString() method in the default constructor
	public String toString() {

		String string = "";
		int fileCount = 0;
		for (ChessPiece[] pieces : chessBoard) {
			int rankCount = 0;
			for (ChessPiece piece : pieces) {
				if (piece == null) {
					if (fileCount % 2 == 0) {
						if (rankCount % 2 == 0) {
							string += "##";
						} else {
							string += "  ";
						}
					} else {
						if (rankCount % 2 == 0) {
							string += "  ";
						} else {
							string += "##";
						}
					}
				} else {
					string += piece;
				}
				string += " ";
				rankCount++;
			}
			fileCount++;
			string += "\n";
		}

		String reverseString = "";

		reverseString += "  a  b  c  d  e  f  g  h \n";
		String[] stringSplit = string.split("\n");
		for (int x = stringSplit.length - 1; x >= 0; x--) {
			reverseString += x + 1 + " " + stringSplit[x] + "\n";
		}

		return reverseString;
	}

	// parse the given input move to corresponding co-ordinates
	public static int[] parseInput(String move) {
		int[] returnArray = new int[4];

		String[] split = move.split(" ");
		returnArray[1] = charToInt(Character.toLowerCase(split[0].charAt(0)));
		returnArray[0] = Integer.parseInt(move.charAt(1) + "") - 1;

		returnArray[3] = charToInt(Character.toLowerCase(split[1].charAt(0)));
		returnArray[2] = Integer.parseInt(split[1].charAt(1) + "") - 1;
		return returnArray;

	}

	public static int charToInt(char ch) {
		switch (ch) {
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		default:
			return -1;
		}
	}

	// Check if there is any capture available
	public void checkIfCaptureExists(String color) {
		ChessPiece[][] oldBoard = chessBoard.clone();
		ChessBoard.captureExists = false;

		int x = 0;
		int y = 0;
		for (String s : ChessBoard.positions) {
			x = Integer.parseInt(s.charAt(0) + "");
			y = Integer.parseInt(s.charAt(1) + "");

			if (chessBoard[x][y] != null) {
				if (chessBoard[x][y].getColor().equals(color)) {
					// System.out.println(coordinatesToMoveString(x, y, w, z));
					if (color.equals("white")) {

						if ((x + 1 < 8) && (y - 1 >= 0) && (y + 1 < 8)) {

							if ((chessBoard[x + 1][y - 1] != null
									&& !chessBoard[x + 1][y - 1].getColor().equals(chessBoard[x][y].getColor()))
									|| (chessBoard[x + 1][y + 1] != null && !chessBoard[x + 1][y + 1].getColor()
											.equals(chessBoard[x][y].getColor()))) {
								ChessBoard.captureExists = true;
								break;
							}
						}
					}

					if (color.equals("black")) {
						if ((x - 1 >= 0) && (y - 1 >= 0) && (y + 1 < 8)) {

							if ((chessBoard[x - 1][y - 1] != null
									&& !chessBoard[x - 1][y - 1].getColor().equals(chessBoard[x][y].getColor()))
									|| (chessBoard[x - 1][y + 1] != null && !chessBoard[x - 1][y + 1].getColor()
											.equals(chessBoard[x][y].getColor()))) {
								ChessBoard.captureExists = true;
								break;
							}
						}
					}
				}
			}
		}
		chessBoard = oldBoard;
	}

	// Check if any pieces can make a valid move
	public boolean canAnyPieceMakeAnyMove(String color) {

		ChessPiece[][] oldBoard = chessBoard.clone();

		for (int x = 0; x < chessBoard.length; x++) {
			for (int y = 0; y < chessBoard[0].length; y++) {
				// Check this piece against every other piece...
				for (int w = 0; w < chessBoard.length; w++) {
					for (int z = 0; z < chessBoard[0].length; z++) {
						try {
							if (chessBoard[x][y] != null) {
								if (chessBoard[x][y].getColor().equals(color)) {
									performMove(coordinatesToMoveString(x, y, w, z), chessBoard[x][y].getColor(),
											false);
									if (!chessBoard[x][y].validMove) {
										return true;
									}
									chessBoard = oldBoard;
									return true;
								}
							}
							chessBoard = oldBoard;
						} catch (Exception e) {
							chessBoard = oldBoard;
						}
					}
				}
			}
		}

		chessBoard = oldBoard;
		return false;
	}

	private String coordinatesToMoveString(int row, int col, int newRow, int newCol) {

		String returnString = "";

		switch (col) {
		case 0:
			returnString += 'a';
			break;
		case 1:
			returnString += 'b';
			break;
		case 2:
			returnString += 'c';
			break;
		case 3:
			returnString += 'd';
			break;
		case 4:
			returnString += 'e';
			break;
		case 5:
			returnString += 'f';
			break;
		case 6:
			returnString += 'g';
			break;
		case 7:
			returnString += 'h';
			break;
		default:
			returnString += 'a';
			break;
		}

		int addInt = row + 1;

		returnString += addInt + "";

		returnString += " ";

		switch (newCol) {
		case 0:
			returnString += 'a';
			break;
		case 1:
			returnString += 'b';
			break;
		case 2:
			returnString += 'c';
			break;
		case 3:
			returnString += 'd';
			break;
		case 4:
			returnString += 'e';
			break;
		case 5:
			returnString += 'f';
			break;
		case 6:
			returnString += 'g';
			break;
		case 7:
			returnString += 'h';
			break;
		default:
			returnString += 'a';
			break;
		}

		addInt = newRow + 1;

		returnString += addInt + "";
		return returnString;
	}

}
