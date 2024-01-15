package model.piece;

import model.bits.Bitboard;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;

public enum Rays {
	West,
	NorthWest,
	North,
	NorthEast,
	East,
	SouthEast,
	South,
	SouthWest;

	public static IBitboard getDiagonalRays(IBitboard slider, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] {
				getRay(slider, board, NorthEast),
				getRay(slider, board, NorthWest),
				getRay(slider, board, SouthWest),
				getRay(slider, board, SouthEast),
			}
		);
	}

	public static IBitboard getHorizontalRays(IBitboard slider, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getRay(slider, board, East), getRay(slider, board, West) }
		);
	}

	public static IBitboard getVerticalRays(IBitboard slider, IBoard board) {
		return Bitboard.mergeMany(
			new IBitboard[] { getRay(slider, board, North), getRay(slider, board, South) }
		);
	}

	private static IBitboard getRay(IBitboard piece, IBoard board, Rays direction) {
		if (piece.getBits() == 0) {
			return piece;
		}
		IBitboard ray = new Bitboard();
		IBitboard slider = piece.copy();
		while (canSlide(slider, board, direction)) {
			slider = shift(slider, direction);
			ray.merge(slider);
		}
		slider = shift(slider, direction);
		if (isEnemyCollision(slider, board)) {
			ray.merge(slider);
		}
		return ray;
	}

	private static IBitboard shift(IBitboard slider, Rays direction) {
		return switch (direction) {
			case West -> Bitboard.shiftWest(slider);
			case NorthWest -> Bitboard.shiftNorthWest(slider);
			case North -> Bitboard.shiftNorth(slider);
			case NorthEast -> Bitboard.shiftNorthEast(slider);
			case East -> Bitboard.shiftEast(slider);
			case SouthEast -> Bitboard.shiftSouthEast(slider);
			case South -> Bitboard.shiftSouth(slider);
			case SouthWest -> Bitboard.shiftSouthWest(slider);
		};
	}

	private static boolean canSlide(IBitboard slider, IBoard board, Rays direction) {
		return !isEdge(slider, direction) && pathFree(shift(slider, direction), board);
	}

	private static boolean isEdge(IBitboard slider, Rays direction) {
		return switch (direction) {
			case West -> isWestEdge(slider);
			case North -> isNorthEdge(slider);
			case East -> isEastEdge(slider);
			case South -> isSouthEdge(slider);
			case NorthWest -> isEdge(slider, North) || isEdge(slider, West);
			case NorthEast -> isEdge(slider, North) || isEdge(slider, East);
			case SouthEast -> isEdge(slider, South) || isEdge(slider, East);
			case SouthWest -> isEdge(slider, South) || isEdge(slider, West);
		};
	}

	private static boolean pathFree(IBitboard slider, IBoard board) {
		if (isFriendlyCollision(slider, board)) {
			return false;
		}
		return !isEnemyCollision(slider, board);
	}

	private static boolean isFriendlyCollision(IBitboard slider, IBoard board) {
		return Bitboard.overlap(slider, board.getPieces(board.getFen().isWhite()));
	}

	private static boolean isEnemyCollision(IBitboard slider, IBoard board) {
		return Bitboard.overlap(slider, board.getPieces(!board.getFen().isWhite()));
	}

	private static boolean isWestEdge(IBitboard slider) {
		return Bitboard.overlap(slider, Board.firstFile);
	}

	private static boolean isEastEdge(IBitboard slider) {
		return Bitboard.overlap(slider, Board.lastFile);
	}

	private static boolean isNorthEdge(IBitboard slider) {
		return Bitboard.overlap(slider, Board.eighthRank);
	}

	private static boolean isSouthEdge(IBitboard slider) {
		return Bitboard.overlap(slider, Board.firstRank);
	}
}
