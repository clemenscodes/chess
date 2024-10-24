package model;

import api.Pieces;
import api.Square;

abstract class King extends Piece implements IKing {

    King(Pieces variant) {
        super(variant);
    }

    King(Pieces variant, IBitboard board) {
        super(variant, board);
    }

    public IBitboard getAttacks(IBitboard piece, IBoard board) {
        IBitboard eastAttack = Bitboard.shiftEast(piece);
        IBitboard westAttack = Bitboard.shiftWest(piece);
        IBitboard[] horizontals = new IBitboard[] { piece, eastAttack, westAttack };
        IBitboard horizontalAttack = Bitboard.mergeMany(horizontals);
        IBitboard northAttack = Bitboard.shiftNorth(horizontalAttack);
        IBitboard southAttack = Bitboard.shiftSouth(horizontalAttack);
        IBitboard[] all = new IBitboard[] { horizontalAttack, northAttack, southAttack };
        IBitboard allAttacks = Bitboard.mergeMany(all);
        IBitboard filterKing = Bitboard.toggle(allAttacks, piece);
        return removeFriendlyPieces(filterKing, board);
    }

    @Override
    public IBitboard getAllAttacks(IBoard board) {
        return getAttacks(getBitboard(), board);
    }

    @Override
    public boolean isInvalidMove(int source, int destination, IBoard board) {
        Square src = Board.getSquare(source);
        Square dst = Board.getSquare(destination);
        if (isValidKingCastle(src, dst, board)) {
            return false;
        }
        if (isValidQueenCastle(src, dst, board)) {
            return false;
        }
        return !(sourceSquareHasPiece(source) &&
                pieceCanMoveToDestination(source, destination, board) &&
                kingSafe(source, destination, board));
    }

    @Override
    protected IMove unsafeMove(int source, int destination, IBoard board) {
        Square src = Board.getSquare(source);
        Square dst = Board.getSquare(destination);
        IForsythEdwardsNotation fen = board.getFen();
        if (Move.isKingCastle(src, dst, board)) {
            return new KingCastleMove(src, dst, board);
        }
        if (Move.isQueenCastle(src, dst, board)) {
            return new QueenCastleMove(src, dst, board);
        }
        if (!fen.getCastling().equals("_")) {
            fen.kingMove();
        }
        if (Move.isCapture(Bitboard.getSingleBit(destination), board)) {
            return new CaptureMove(src, dst, board);
        }
        return new QuietMove(src, dst, board);
    }

    @Override
    protected boolean kingSafe(int source, int destination, IBoard board) {
        IBoard simulatedBoard = simulateMove(source, destination, board);
        boolean kingAttacked = Bitboard.overlap(
                simulatedBoard.getKing(board.getFen().isWhite()),
                simulatedBoard.getAllOpponentAttacks());
        if (kingAttacked) {
            throw new Error("King can not move into an attack");
        }
        return true;
    }

    private boolean isValidKingCastle(Square src, Square dst, IBoard board) {
        return Move.isKingCastle(src, dst, board) && Move.canKingCastle(src, dst, board);
    }

    private boolean isValidQueenCastle(Square src, Square dst, IBoard board) {
        return Move.isQueenCastle(src, dst, board) && Move.canQueenCastle(src, dst, board);
    }
}
