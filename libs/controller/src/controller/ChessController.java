package controller;

import api.IChessController;
import api.IChessModel;
import api.IChessView;
import api.Square;
import api.State;
import java.util.ArrayList;

/**
 * The {@link ChessController} class implements the {@link IChessController}
 * interface, serving as the
 * controller component in the Chess MVC (Model-View-Controller) architecture.
 * It manages the game logic,
 * user interactions, and communication between the model and view components.
 */
public class ChessController implements IChessController {

    /**
     * The model representing the chess game state. It implements the
     * {@link IChessModel} interface.
     */
    private IChessModel model;

    /**
     * The view responsible for rendering the graphical user interface (GUI) of the
     * chess game.
     * It implements the {@link IChessView} interface.
     */
    private IChessView view;

    /**
     * The source square of the last chess move. It represents the starting position
     * of a piece.
     */
    private Square source;

    /**
     * The destination square of the last chess move. It represents the target
     * position of a piece.
     */
    private Square destination;

    /**
     * The square being dragged by the user during user interactions.
     */
    private Square draggedSquare;

    /**
     * The list of legal moves available for the current player.
     * Each element is an array of squares representing a legal move (source and
     * destination).
     */
    private ArrayList<Square[]> legalMoves;

    public void setModel(IChessModel model) {
        this.model = model;
    }

    public void setView(IChessView view) {
        this.view = view;
    }

    public void startGame() {
        getModel().startGame();
    }

    public void startNewGame() {
        getModel().startNewGame();
    }

    public void nextFrame() {
        State state = getModel().getGameState();
        switch (state) {
            case Start -> getView().drawStart();
            case Playing -> getView().drawPlaying();
            case Checkmate -> getView().drawCheckmate();
            case Stalemate -> getView().drawStalemate();
            case Resignation -> getView().drawResignation();
            case DrawOffer -> getView().drawDrawOffer();
            case Promotion -> getView().drawPromotion();
            case Draw -> getView().drawDraw();
            default -> throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    public State getGameState() {
        return getModel().getGameState();
    }

    public String getMoves() {
        return getModel().getMoves();
    }

    public void resign() {
        getModel().resign();
    }

    public void offerDraw() {
        getModel().offerDraw();
    }

    public void acceptDraw() {
        getModel().acceptDraw();
    }

    public void declineDraw() {
        getModel().declineDraw();
    }

    public void claimDraw() {
        getModel().claimDraw();
    }

    public void makeMove(Square source, Square destination) {
        getModel().makeMove(source, destination);
    }

    public void promoteQueen() {
        getModel().promoteQueen();
    }

    public void promoteRook() {
        getModel().promoteRook();
    }

    public void promoteKnight() {
        getModel().promoteKnight();
    }

    public void promoteBishop() {
        getModel().promoteBishop();
    }

    public boolean isOwnPieceOnSquare(Square square) {
        return getModel().isOwnPieceOnSquare(square);
    }

    public String[] getPiecePlacementData() {
        return getModel().getPiecePlacementData();
    }

    public String getFen() {
        return getModel().getFen();
    }

    public boolean isWhite() {
        return getModel().isWhite();
    }

    public String getCastling() {
        return getModel().getCastling();
    }

    public boolean getWhiteKingCastle() {
        return getModel().getWhiteKingCastle();
    }

    public boolean getWhiteQueenCastle() {
        return getModel().getWhiteQueenCastle();
    }

    public boolean getBlackKingCastle() {
        return getModel().getBlackKingCastle();
    }

    public boolean getBlackQueenCastle() {
        return getModel().getBlackQueenCastle();
    }

    public String getEnPassant() {
        return getModel().getEnPassant();
    }

    public int getHalfMoveClock() {
        return getModel().getHalfMoveClock();
    }

    public int getFullMoveNumber() {
        return getModel().getFullMoveNumber();
    }

    public void handleMousePressed(int x, int y) {
        if (getGameState() != State.Playing) {
            return;
        }
        Square square = getSquareFromCoordinates(x, y);
        if (square == null || !getModel().isOwnPieceOnSquare(square)) {
            return;
        }
        setSource(square);
        setDraggedSquare(square);
        setLegalMoves(getLegalMoves(square));
    }

    public void handleMouseDragged(int x, int y) {
        if (getGameState() != State.Playing) {
            return;
        }
        Square square = getSquareFromCoordinates(x, y);
        if (square == null || getSource() == null) {
            setDraggedSquare(null);
        }
        setDraggedSquare(square);
    }

    public void handleMouseMoved(int x, int y) {
        if (getGameState() != State.Playing) {
            return;
        }
        Square square = getSquareFromCoordinates(x, y);
        if (square == null || getSource() == null) {
            setDraggedSquare(null);
        }
        setDraggedSquare(square);
    }

    public void handleMouseReleased(int x, int y) {
        setDraggedSquare(null);
        if (getGameState() != State.Playing) {
            return;
        }
        Square square = getSquareFromCoordinates(x, y);
        if (getSource() == null || square == null || square.equals(getSource())) {
            return;
        }
        setDestination(square);
        getModel().makeMove(getSource(), getDestination());
        clearErrorMessage();
        getLegalMoves().clear();
    }

    public Square getSource() {
        return source;
    }

    public Square getDestination() {
        return destination;
    }

    public Square getDraggedSquare() {
        return draggedSquare;
    }

    public ArrayList<Square[]> getLegalMoves() {
        return legalMoves;
    }

    public String getErrorMessage() {
        return getModel().getErrorMessage();
    }

    public void clearErrorMessage() {
        setSource(null);
        setDestination(null);
        getModel().clearError();
        getLegalMoves().clear();
    }

    /**
     * The {@code getSquareFromCoordinates} method converts screen coordinates (x,
     * y) to a corresponding chessboard square.
     * It checks if the coordinates are outside the chessboard boundaries and
     * returns null if so.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The corresponding chessboard square or null if coordinates are
     *         outside the boundaries.
     */
    private Square getSquareFromCoordinates(int x, int y) {
        boolean isOutsideHorizontally = isOutsideHorizontally(x);
        boolean isOutsideVertically = isOutsideVertically(y);
        boolean isOutside = isOutsideHorizontally || isOutsideVertically;
        if (isOutside) {
            return null;
        }
        int fileOffset = x - getView().getLeftBoardOffset();
        int rankOffset = (getView().getHeight() - getView().getTopBoardOffset()) - y;
        int squareSize = getView().getSquareSize();
        int file = getIndex(fileOffset, squareSize);
        int rank = getIndex(rankOffset, squareSize);
        return getSquareFromRankFile(rank, file);
    }

    /**
     * The {@code isOutsideHorizontally} method checks if a given coordinate is
     * outside the horizontal boundaries of the chessboard.
     *
     * @param point The coordinate to check.
     * @return True if the coordinate is outside horizontally, false otherwise.
     */
    private boolean isOutsideHorizontally(int point) {
        boolean isOutsideLeft = point < getView().getLeftBoardOffset();
        boolean isOutsideRight = point > getView().getWidth() - getView().getLeftBoardOffset();
        return isOutsideLeft || isOutsideRight;
    }

    /**
     * The {@code isOutsideVertically} method checks if a given coordinate is
     * outside the vertical boundaries of the chessboard.
     *
     * @param point The coordinate to check.
     * @return True if the coordinate is outside vertically, false otherwise.
     */
    private boolean isOutsideVertically(int point) {
        boolean isOutsideTop = point < getView().getTopBoardOffset();
        boolean isOutsideBottom = point > getView().getHeight() - getView().getTopBoardOffset();
        return isOutsideTop || isOutsideBottom;
    }

    /**
     * The {@code getIndex} method calculates the index of a square based on the
     * offset and square size.
     *
     * @param offset     The offset from the reference point.
     * @param squareSize The size of a chess square
     * @return The calculated index.
     */
    private int getIndex(int offset, int squareSize) {
        return (int) Math.floor((double) offset / squareSize) + 1;
    }

    /**
     * The {@code getSquareFromRankFile} method converts rank and file indices to a
     * corresponding chessboard square.
     * It checks if the indices are valid and returns null if not.
     *
     * @param rank The rank index.
     * @param file The file index.
     * @return The corresponding chessboard square or null if indices are invalid.
     */
    private Square getSquareFromRankFile(int rank, int file) {
        if (isInvalidIndex(rank) || isInvalidIndex(file)) {
            return null;
        }
        char rankChar = (char) (rank + '0');
        char fileChar = (char) (file + 'a' - 1);
        String square = new String(new char[] { fileChar, rankChar });
        return Square.valueOf(square);
    }

    /**
     * The {@code isInvalidIndex} method checks if a given index is outside the
     * valid range [1, 8].
     *
     * @param index The index to check.
     * @return True if the index is invalid, false otherwise.
     */
    private boolean isInvalidIndex(int index) {
        return index < 1 || index > 8;
    }

    /**
     * The {@code getModel} method retrieves the associated chess model.
     *
     * @return The chess model.
     */
    private IChessModel getModel() {
        return model;
    }

    /**
     * The {@code getView} method retrieves the associated chess view.
     *
     * @return The chess view.
     */
    private IChessView getView() {
        return view;
    }

    /**
     * The {@code setSource} method sets the source square for a chess move.
     *
     * @param source The source square.
     */
    private void setSource(Square source) {
        this.source = source;
    }

    /**
     * The {@code setDestination} method sets the destination square for a chess
     * move.
     *
     * @param destination The destination square.
     */
    private void setDestination(Square destination) {
        this.destination = destination;
    }

    /**
     * The {@code setDraggedSquare} method sets the square being dragged to by the
     * user during interactions.
     *
     * @param draggedSquare The square being dragged to.
     */
    private void setDraggedSquare(Square draggedSquare) {
        this.draggedSquare = draggedSquare;
    }

    /**
     * The {@code setLegalMoves} method sets the list of legal moves available for
     * the current player.
     *
     * @param legalMoves The list of legal moves.
     */
    private void setLegalMoves(ArrayList<Square[]> legalMoves) {
        this.legalMoves = legalMoves;
    }

    /**
     * The {@code getLegalMoves} method retrieves the list of legal moves for a
     * specified square from the model.
     *
     * @param square The square to get legal moves for.
     * @return The list of legal moves.
     */
    private ArrayList<Square[]> getLegalMoves(Square square) {
        return getModel().getLegalMoves(square);
    }
}
