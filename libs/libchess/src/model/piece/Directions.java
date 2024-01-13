package model.piece;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;

public enum Directions {
	West,
	NorthWest,
	North,
	NorthEast,
	East,
	SouthEast,
	South,
	SouthWest;

	public static IBitboard getDiagonalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				getRay(piece, board, NorthEast),
				getRay(piece, board, NorthWest),
				getRay(piece, board, SouthWest),
				getRay(piece, board, SouthEast),
			}
		);
	}

	public static IBitboard getHorizontalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getRay(piece, board, East), getRay(piece, board, West) }
		);
	}

	public static IBitboard getVerticalRays(IBitboard piece, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getRay(piece, board, North), getRay(piece, board, South) }
		);
	}

	private static IBitboard getRay(IBitboard piece, IBoard board, Directions direction) {
		IBitboard ray = new Bitboard();
		IBitboard shift = piece.copy();
		while (Directions.canSlide(shift, board, direction)) {
			shift = Directions.shift(shift, direction);
			ray.merge(shift);
		}
		shift = Directions.shift(shift, direction);
		if (isEnemyCollision(shift, board)) {
			ray.merge(shift);
		}
		return ray;
	}

	private static IBitboard shift(IBitboard piece, Directions direction) {
		return switch (direction) {
			case West -> Bitboard.shiftWest(piece);
			case NorthWest -> Bitboard.shiftNorthWest(piece);
			case North -> Bitboard.shiftNorth(piece);
			case NorthEast -> Bitboard.shiftNorthEast(piece);
			case East -> Bitboard.shiftEast(piece);
			case SouthEast -> Bitboard.shiftSouthEast(piece);
			case South -> Bitboard.shiftSouth(piece);
			case SouthWest -> Bitboard.shiftSouthWest(piece);
		};
	}

	private static boolean canSlide(IBitboard piece, IBoard board, Directions direction) {
		return !isEdge(piece, direction) && pathFree(shift(piece, direction), board);
	}

	private static boolean isFriendlyCollision(IBitboard piece, IBoard board) {
		return Bitboard.overlap(piece, board.getPieces(board.getFen().isWhite()));
	}

	private static boolean isEnemyCollision(IBitboard piece, IBoard board) {
		return Bitboard.overlap(piece, board.getPieces(!board.getFen().isWhite()));
	}

	private static boolean pathFree(IBitboard slided, IBoard board) {
		if (isFriendlyCollision(slided, board)) {
			return false;
		}
		return !isEnemyCollision(slided, board);
	}

	private static boolean isEdge(IBitboard piece, Directions direction) {
		return switch (direction) {
			case West -> westEdge(piece);
			case NorthWest -> isEdge(piece, North) || isEdge(piece, West);
			case North -> northEdge(piece);
			case NorthEast -> isEdge(piece, North) || isEdge(piece, East);
			case East -> eastEdge(piece);
			case SouthEast -> isEdge(piece, South) || isEdge(piece, East);
			case South -> southEdge(piece);
			case SouthWest -> isEdge(piece, South) || isEdge(piece, West);
		};
	}

	private static boolean westEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstFile);
	}

	private static boolean eastEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.lastFile);
	}

	private static boolean northEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.eighthRank);
	}

	private static boolean southEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstRank);
	}
}
