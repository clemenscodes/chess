package model.bits;

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

	public static IBitboard shift(IBitboard piece, Directions direction) {
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

	public static boolean isEdge(IBitboard piece, Directions direction) {
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

	public static boolean westEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstFile);
	}

	public static boolean eastEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.lastFile);
	}

	public static boolean northEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.eighthRank);
	}

	public static boolean southEdge(IBitboard piece) {
		return Bitboard.overlap(piece, Board.firstRank);
	}

	public static boolean canSlide(IBitboard piece, IBoard board, Directions direction) {
		return !isEdge(piece, direction) && Bitboard.pathFree(shift(piece, direction), board);
	}
}
