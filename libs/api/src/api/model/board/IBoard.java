package api.model.board;

import api.model.bits.IBitboard;
import api.model.fen.IForsythEdwardsNotation;
import api.model.piece.IPiece;
import api.model.piece.Pieces;
import api.model.piece.bishop.IBishop;
import api.model.piece.king.IKing;
import api.model.piece.knight.IKnight;
import api.model.piece.pawn.IPawn;
import api.model.piece.queen.IQueen;
import api.model.piece.rook.IRook;
import java.io.IOException;
import java.util.ArrayList;

public interface IBoard {
	IForsythEdwardsNotation getFen();

	IKing getWhiteKing();

	IQueen getWhiteQueen();

	IRook getWhiteRook();

	IKnight getWhiteKnight();

	IBishop getWhiteBishop();

	IPawn getWhitePawn();

	IKing getBlackKing();

	IQueen getBlackQueen();

	IRook getBlackRook();

	IKnight getBlackKnight();

	IBishop getBlackBishop();

	IPawn getBlackPawn();

	IPiece[] getAllWhitePieces();

	IBitboard getWhitePieces();

	IPiece[] getAllBlackPieces();

	IBitboard getBlackPieces();

	IBitboard getOccupiedSquares();

	IBitboard getEmptySquares();

	IBitboard[] getAllPieces();

	IBitboard getPieces(boolean getWhite);

	IBitboard getAllOpponentAttacks();

	IBitboard getAllFriendlyAttacks();

	IBitboard getKing(boolean getWhite);

	IPiece getPiece(Square square);

	IPiece getPieceByKind(Pieces kind);

	Pieces getPieceByIndex(int index);

	void capturePiece(int index);

	void setPieces();

	IBoard deepCopy() throws IOException, ClassNotFoundException;

	boolean kingUnsafe();

	boolean isSquareAttacked(Square square);

	boolean isSquareEmpty(Square square);

	String getPiecePlacementData();

	ArrayList<Square[]> getAllMoves(boolean getWhite);
}
