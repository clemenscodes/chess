package bits;

import static model.move.Rays.*;

import java.io.Serializable;
import java.util.ArrayList;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.move.Rays;
import model.writer.Writer;

public class Bitboard implements IBitboard, Serializable {

	private static final int BITS = 64;

	private static final IBitboard[] singleBits = new IBitboard[Board.SIZE * Board.SIZE];

	static {
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < Board.SIZE; file++) {
				int index = Board.getSquareIndex(rank, file);
				singleBits[index] = leftShiftMask(index);
			}
		}
	}

	public static IBitboard getSingleBit(int index) {
		if (index < 0 || index >= singleBits.length) {
			throw new Error("Invalid index " + index);
		}
		return singleBits[index];
	}

	public static boolean checkBit(IBitboard board, int index) {
		return (intersect(board, getSingleBit(index)).getBits() != 0);
	}

	public static IBitboard setBit(IBitboard board, int index) {
		return merge(board, getSingleBit(index));
	}

	public static IBitboard toggleBit(IBitboard board, int index) {
		return toggle(board, getSingleBit(index));
	}

	public static IBitboard toggle(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() ^ b.getBits());
	}

	public static IBitboard unsetBit(IBitboard board, int index) {
		return toggle(setBit(board, index), getSingleBit(index));
	}

	public static IBitboard negate(IBitboard board) {
		return new Bitboard(~board.getBits());
	}

	public static IBitboard mergeMany(IBitboard[] bitboards) {
		long mergedBits = 0L;
		for (var board : bitboards) {
			mergedBits |= board.getBits();
		}
		return new Bitboard(mergedBits);
	}

	public static IBitboard merge(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() | b.getBits());
	}

	public static IBitboard intersect(IBitboard a, IBitboard b) {
		return new Bitboard(a.getBits() & b.getBits());
	}

	public static boolean overlap(IBitboard a, IBitboard b) {
		return (intersect(a, b).getBits() != 0);
	}

	public static IBitboard rightShiftMask(IBitboard board, int bits) {
		return new Bitboard(board.getBits() >>> bits);
	}

	public static IBitboard shift(IBitboard board, int index) {
		long bits = board.getBits();
		return new Bitboard(index < 0 ? bits >>> -index : bits << index);
	}

	public static IBitboard shiftNorth(IBitboard board) {
		return shift(board, Board.NORTH);
	}

	public static IBitboard shiftNorthEast(IBitboard board) {
		return intersect(shift(board, Board.NORTH_EAST), Board.notFirstFile);
	}

	public static IBitboard shiftNorthWest(IBitboard board) {
		return intersect(shift(board, Board.NORTH_WEST), Board.notLastFile);
	}

	public static IBitboard shiftNorthNorthEast(IBitboard board) {
		return intersect(shift(board, Board.NORTH_NORTH_EAST), Board.notFirstFile);
	}

	public static IBitboard shiftNorthNorthWest(IBitboard board) {
		return intersect(shift(board, Board.NORTH_NORTH_WEST), Board.notLastFile);
	}

	public static IBitboard shiftEast(IBitboard board) {
		return intersect(shift(board, Board.EAST), Board.notFirstFile);
	}

	public static IBitboard shiftEastEastNorth(IBitboard board) {
		return intersect(
			shift(board, Board.EAST_EAST_NORTH),
			negate(merge(Board.firstFile, Board.secondFile))
		);
	}

	public static IBitboard shiftEastEastSouth(IBitboard board) {
		return intersect(
			shift(board, Board.EAST_EAST_SOUTH),
			negate(merge(Board.firstFile, Board.secondFile))
		);
	}

	public static IBitboard shiftSouth(IBitboard board) {
		return shift(board, Board.SOUTH);
	}

	public static IBitboard shiftSouthEast(IBitboard board) {
		return intersect(shift(board, Board.SOUTH_EAST), Board.notFirstFile);
	}

	public static IBitboard shiftSouthWest(IBitboard board) {
		return intersect(shift(board, Board.SOUTH_WEST), Board.notLastFile);
	}

	public static IBitboard shiftSouthSouthEast(IBitboard board) {
		return intersect(shift(board, Board.SOUTH_SOUTH_EAST), Board.notFirstFile);
	}

	public static IBitboard shiftSouthSouthWest(IBitboard board) {
		return intersect(shift(board, Board.SOUTH_SOUTH_WEST), Board.notLastFile);
	}

	public static IBitboard shiftWest(IBitboard board) {
		return intersect(shift(board, Board.WEST), Board.notLastFile);
	}

	public static IBitboard shiftWestWestNorth(IBitboard board) {
		return intersect(
			shift(board, Board.WEST_WEST_NORTH),
			negate(merge(Board.lastFile, Board.secondLastFile))
		);
	}

	public static IBitboard shiftWestWestSouth(IBitboard board) {
		return intersect(
			shift(board, Board.WEST_WEST_SOUTH),
			negate(merge(Board.lastFile, Board.secondLastFile))
		);
	}

	public static ArrayList<IBitboard> split(IBitboard board) {
		ArrayList<IBitboard> boards = new ArrayList<>();
		for (int i = 0; i < BITS; i++) {
			IBitboard singleBit = getSingleBit(i);
			if (Bitboard.overlap(board, singleBit)) {
				boards.add(singleBit);
			}
		}
		return boards;
	}

	public static IBitboard leftShiftMask(int bits) {
		return new Bitboard(1L << bits);
	}

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

	private long bits;

	public Bitboard(long bits) {
		setBits(bits);
	}

	public Bitboard() {
		setBits(0);
	}

	public long getBits() {
		return bits;
	}

	public void setBits(long bits) {
		this.bits = bits;
	}

	public boolean overlap(IBitboard board) {
		return overlap(this, board);
	}

	public void merge(IBitboard board) {
		setBits(merge(this, board).getBits());
	}

	public void intersect(IBitboard board) {
		setBits(intersect(this, board).getBits());
	}

	public void leftShift(int bits) {
		setBits(getBits() << bits);
	}

	public void rightShift(int bits) {
		setBits(getBits() >>> bits);
	}

	public void negateBits() {
		setBits(negate(this).getBits());
	}

	public void toggleBits(IBitboard board) {
		setBits(toggle(this, board).getBits());
	}

	public void unsetBitByIndex(int index) {
		setBits(unsetBit(this, index).getBits());
	}

	public void setBitByIndex(int index) {
		setBits(setBit(this, index).getBits());
	}

	public IBitboard copy() {
		return new Bitboard(getBits());
	}

	@Override
	public String toString() {
		return Writer.loopOver(this::appendRank, null).toString();
	}

	private void appendRank(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces) {
		int index = Board.getSquareIndex(rank, file);
		IBitboard mask = getSingleBit(index);
		IBitboard intersection = intersect(this, mask);
		long bit = rightShiftMask(intersection, index).getBits();
		stringBuilder.append(bit == -1 ? 1 : bit);
	}
}
