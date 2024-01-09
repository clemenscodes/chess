package model.board;

import model.bits.IBitboard;
import model.fen.IForsythEdwardsNotation;
import model.piece.IPiece;
import model.piece.Pieces;
import model.piece.bishop.BlackBishop;
import model.piece.bishop.WhiteBishop;
import model.piece.king.BlackKing;
import model.piece.king.WhiteKing;
import model.piece.knight.BlackKnight;
import model.piece.knight.WhiteKnight;
import model.piece.pawn.BlackPawn;
import model.piece.pawn.WhitePawn;
import model.piece.queen.BlackQueen;
import model.piece.queen.WhiteQueen;
import model.piece.rook.BlackRook;
import model.piece.rook.WhiteRook;

public interface IBoard {
	IForsythEdwardsNotation getFen();

	WhiteKing getWhiteKing();

	WhiteQueen getWhiteQueen();

	WhiteRook getWhiteRook();

	WhiteKnight getWhiteKnight();

	WhiteBishop getWhiteBishop();

	WhitePawn getWhitePawn();

	BlackKing getBlackKing();

	BlackQueen getBlackQueen();

	BlackRook getBlackRook();

	BlackKnight getBlackKnight();

	BlackBishop getBlackBishop();

	BlackPawn getBlackPawn();

	IBitboard getWhitePieces();

	IBitboard getBlackPieces();

	IBitboard getOccupiedSquares();

	IBitboard getEmptySquares();

	IBitboard[] getAllPieces();

	IBitboard getOpponentPieces();

	IBitboard getFriendlyPieces();

	IBitboard getAllOpponentAttacks();

	Pieces getPieceByIndex(int index);

	IPiece getPiece(Pieces kind);

	void capturePiece(int index);

	void setPieces();

	boolean isSquareAttacked(Square square);
}
