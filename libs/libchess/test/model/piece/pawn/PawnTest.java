package model.piece.pawn;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import model.bits.IBitboard;
import model.board.Board;
import model.board.IBoard;
import model.fen.ForsythEdwardsNotation;
import model.piece.Pieces;
import model.reader.IReader;
import model.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnTest {

	static class PieceMock extends Pawn {

		public PieceMock(Pieces variant, IBitboard board) {
			super(variant, board);
		}

		protected IBitboard getWestAttacks(IBitboard pawns) {
			return null;
		}

		protected IBitboard getEastAttacks(IBitboard pawns) {
			return null;
		}

		protected IBitboard getAttackingPawns(IBoard board) {
			return null;
		}

		protected IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares) {
			return null;
		}

		protected IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares) {
			return null;
		}

		protected IBitboard getSinglePushablePawns(IBitboard emptySquares) {
			return null;
		}

		protected IBitboard getDoublePushablePawns(IBitboard emptySquares) {
			return null;
		}

		protected Pieces[] getPromotionPieces() {
			return new Pieces[0];
		}
	}

	private IReader reader;
	private Pawn piece;
	private IBoard board;

	@BeforeEach
	void setup() {
		String inputString = "Q";
		byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);
		InputStream inputStream = new ByteArrayInputStream(bytes);
		reader = new Reader(inputStream);
		board = new Board(new ForsythEdwardsNotation("8/8/8/8/8/8/8/8 w - - 0 1"));
		piece = new PieceMock(Pieces.WhitePawn, board.getWhitePawn().getBitboard());
	}

	@Test
	void testPromotionMask() {
		String expected =
			"""
			11111111
			00000000
			00000000
			00000000
			00000000
			00000000
			00000000
			11111111""";
		assertEquals(expected, Pawn.promotionMask.toString());
	}
}
