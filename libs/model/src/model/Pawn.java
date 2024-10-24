package model;

import api.Pieces;
import api.Square;
import api.State;
import java.io.IOException;

abstract class Pawn extends Piece implements IPawn {

    protected abstract IBitboard getWestAttacks(IBitboard pawns);

    protected abstract IBitboard getEastAttacks(IBitboard pawns);

    protected abstract IBitboard getAttackingPawns(IBoard board);

    protected abstract IBitboard getSinglePushTargets(IBitboard pawn, IBitboard emptySquares);

    protected abstract IBitboard getDoublePushTargets(IBitboard pawn, IBitboard emptySquares);

    protected abstract IBitboard getSinglePushablePawns(IBitboard emptySquares);

    protected abstract IBitboard getDoublePushablePawns(IBitboard emptySquares);

    protected abstract Pieces[] getPromotionPieces();

    static final IBitboard promotionMask = Bitboard.merge(Board.firstRank, Board.eighthRank);

    Pawn(Pieces variant) {
        super(variant);
    }

    Pawn(Pieces variant, IBitboard board) {
        super(variant, board);
    }

    public boolean isInvalidMove(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        return !(sourceSquareHasPiece(source, board) &&
                pieceCanMoveToDestination(source, destination, board) &&
                kingSafe(source, destination, board, reader, writer));
    }

    public IMove move(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        if (isInvalidMove(source, destination, board, reader, writer)) {
            throw new Error("Invalid move");
        }
        return unsafeMove(source, destination, board, reader, writer);
    }

    public IBitboard getTargets(IBitboard piece, IBoard board) {
        IBitboard pushTargets = getPushTargets(piece, board.getEmptySquares());
        IBitboard attackTargets = getAttacks(piece, board);
        return Bitboard.merge(pushTargets, attackTargets);
    }

    public IBitboard getAttacks(IBitboard piece, IBoard board) {
        IBitboard enPassantMask = board.getFen().getEnPassantMask();
        IBitboard westAttacks = getWestAttacks(piece);
        IBitboard eastAttacks = getEastAttacks(piece);
        IBitboard regularAttacks = Bitboard.merge(westAttacks, eastAttacks);
        IBitboard enemyAttacks = Bitboard.intersect(
                regularAttacks,
                board.getPieces(!board.getFen().isWhite()));
        if (Bitboard.overlap(regularAttacks, enPassantMask)) {
            enemyAttacks.merge(enPassantMask);
        }
        return removeFriendlyPieces(enemyAttacks, board);
    }

    @Override
    public IBitboard getAllAttacks(IBoard board) {
        return getAttacks(getBitboard(), board);
    }

    @Override
    protected boolean pieceCanMoveToDestination(int source, int destination, IBoard board) {
        return Bitboard.checkBit(getTargets(Bitboard.getSingleBit(source), board), destination);
    }

    private boolean sourceSquareHasPiece(int source, IBoard board) {
        return Bitboard.checkBit(getMovablePieces(board), source);
    }

    private IBitboard getMovablePieces(IBoard board) {
        IBitboard pushablePawns = getPushablePawns(board.getEmptySquares());
        IBitboard attackingPawns = getAttackingPawns(board);
        return Bitboard.merge(pushablePawns, attackingPawns);
    }

    private IBitboard getPushablePawns(IBitboard emptySquares) {
        IBitboard singlePushablePawns = getSinglePushablePawns(emptySquares);
        IBitboard doublePushablePawns = getDoublePushablePawns(emptySquares);
        return Bitboard.merge(singlePushablePawns, doublePushablePawns);
    }

    private IBitboard getPushTargets(IBitboard pawn, IBitboard emptySquares) {
        IBitboard singlePushTargets = getSinglePushTargets(pawn, emptySquares);
        IBitboard doublePushTargets = getDoublePushTargets(pawn, emptySquares);
        return Bitboard.merge(singlePushTargets, doublePushTargets);
    }

    private boolean kingSafe(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        IBoard simulatedBoard = simulateMove(source, destination, board, reader, writer);
        boolean kingSafety = !Bitboard.overlap(
                board.getKing(board.getFen().isWhite()),
                simulatedBoard.getAllOpponentAttacks());
        if (!kingSafety) {
            throw new Error("King is in check");
        }
        return true;
    }

    public IBoard simulateMove(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        IBoard copiedBoard = null;
        try {
            copiedBoard = board.deepCopy();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        assert copiedBoard != null;
        unsafeMove(source, destination, copiedBoard, reader, writer);
        return copiedBoard;
    }

    private IMove unsafeMove(
            int source,
            int destination,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        Square src = Board.getSquare(source);
        Square dst = Board.getSquare(destination);
        IBitboard destinationBit = Bitboard.getSingleBit(destination);
        IPiece pawn = board.getPiece(Board.getSquare(source));
        if (Move.isPromotion(destinationBit)) {
            return promotePawn(src, dst, board, reader, writer);
        }
        if (Move.isEnPassant(destinationBit, board)) {
            return new EnPassantCaptureMove(src, dst, board, pawn);
        }
        if (Move.isCapture(destinationBit, board)) {
            return new PawnCaptureMove(src, dst, board, pawn);
        }
        return pawnPush(src, dst, board);
    }

    private IMove promotePawn(
            Square src,
            Square dst,
            IBoard board,
            IReader<String> reader,
            IWriter<State> writer) {
        writer.write(State.Promotion);
        System.out.println(
                "Pawn promotion! Select the piece you want: Q (Queen), R (Rook), N (Knight), B (Bishop)");
        Pieces piece = Piece.getSelectedPiece(getPromotionPieces(), getSelection(reader));
        IBitboard destinationBit = Bitboard.getSingleBit(Board.getIndex(dst));
        IMove move = Move.isCapture(destinationBit, board)
                ? makePromotionCapture(src, dst, piece, board)
                : makePromotion(src, dst, piece, board);
        writer.write(State.Playing);
        return move;
    }

    private String getSelection(IReader<String> reader) {
        String userInput = reader.read();
        System.out.println(userInput);
        while (userInput == null || !userInput.matches("[QRNB]")) {
            System.err.println("Invalid selection");
            userInput = reader.read();
        }
        return userInput;
    }

    private IMove pawnPush(Square source, Square destination, IBoard board) {
        IPiece pawn = board.getPiece(source);
        int moveIndexDifference = Math.abs(Board.getIndex(destination) - Board.getIndex(source));
        return (moveIndexDifference == Board.SIZE)
                ? new SinglePawnPushMove(source, destination, board, pawn)
                : new DoublePawnPushMove(source, destination, board, pawn);
    }

    private IMove makePromotion(Square src, Square dst, Pieces piece, IBoard board) {
        Pieces[] pieces = getPromotionPieces();
        if (piece == pieces[0]) {
            return new QueenPromotionMove(src, dst, board);
        }
        if (piece == pieces[1]) {
            return new RookPromotionMove(src, dst, board);
        }
        if (piece == pieces[2]) {
            return new KnightPromotionMove(src, dst, board);
        }
        return new BishopPromotionMove(src, dst, board);
    }

    private IMove makePromotionCapture(Square src, Square dst, Pieces piece, IBoard board) {
        Pieces[] pieces = getPromotionPieces();
        if (piece == pieces[0]) {
            return new QueenPromotionCaptureMove(src, dst, board);
        }
        if (piece == pieces[1]) {
            return new RookPromotionCaptureMove(src, dst, board);
        }
        if (piece == pieces[2]) {
            return new KnightPromotionCaptureMove(src, dst, board);
        }
        return new BishopPromotionCaptureMove(src, dst, board);
    }
}
