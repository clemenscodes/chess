package model;

import api.Pieces;
import api.Square;
import java.io.IOException;
import java.util.ArrayList;

interface IBoard {
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
