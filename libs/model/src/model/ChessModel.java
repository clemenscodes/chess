package model;

import api.IChessModel;
import api.Square;
import api.State;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * The {@link ChessModel} class implements the {@link api.IChessModel}
 * interface,
 * serving as the underlying model for a chess game.
 * It manages the game state, moves, and interactions between various
 * components.
 *
 * <p>
 * This class incorporates the rules and logic of chess, allowing for the
 * progression of the game, validation of moves, and handling of game states.
 * It operates within a multithreaded environment to manage concurrent game
 * interactions.
 * </p>
 *
 * <p>
 * The class includes functionality for controlling access to critical sections
 * through the use of a semaphore, as well as handling errors, managing threads
 * for
 * moves and draw offers, and utilizing reader-writer interfaces for data
 * exchange.
 * </p>
 *
 * <p>
 * Instances of this class maintain the state of the chessboard, track the list
 * of moves,
 * and provide methods to initiate and conclude the game based on the rules of
 * chess.
 * </p>
 *
 * @see IChessModel
 * @see IReader
 * @see IWriter
 * @see State
 * @see Square
 * @see IBoard
 * @see IMoveList
 */
public class ChessModel implements IChessModel {

    /**
     * A semaphore that controls access to critical sections of the model.
     * It ensures thread safety in a multithreaded environment.
     */
    private final Semaphore semaphore = new Semaphore(1);

    /**
     * Holds error messages generated during the chess game.
     */
    private String errorMessage;

    /**
     * Thread responsible for handling chess moves during the game.
     */
    private Thread moveThread;

    /**
     * Thread responsible for handling draw offer interactions during the game.
     */
    private Thread drawOfferThread;

    /**
     * Reader for retrieving the current state of the chess game.
     */
    private IReader<State> stateReader;

    /**
     * Writer for updating and setting the state of the chess game.
     */
    private IWriter<State> stateWriter;

    /**
     * Queue for storing and exchanging states within the chess game.
     */
    private BlockingQueue<State> stateQueue;

    /**
     * Reader for retrieving textual data within the chess game.
     */
    private IReader<String> reader;

    /**
     * Writer for updating and setting textual data within the chess game.
     */
    private IWriter<String> writer;

    /**
     * Queue for storing and exchanging textual data within the chess game.
     */
    private BlockingQueue<String> dataQueue;

    /**
     * Reader for retrieving moves represented by an array of squares.
     */
    private IReader<Square[]> moveReader;

    /**
     * Writer for updating and setting moves represented by an array of squares.
     */
    private IWriter<Square[]> moveWriter;

    /**
     * Queue for storing and exchanging moves represented by an array of squares.
     */
    private BlockingQueue<Square[]> moveQueue;

    /**
     * Represents the current state of the chessboard during the game.
     */
    private IBoard board;

    /**
     * Represents the list of moves made during the chess game.
     */
    private IMoveList moveList;

    /**
     * Constructs a new ChessModel.
     */
    public ChessModel() {
        setStateQueue(new LinkedBlockingQueue<>());
        setStateReader(getStateQueue());
        setStateWriter(getStateQueue());
        setDataQueue(new LinkedBlockingQueue<>());
        setReader(getDataQueue());
        setWriter(getDataQueue());
        setMoveQueue(new LinkedBlockingQueue<>());
        setMoveReader(getMoveQueue());
        setMoveWriter(getMoveQueue());
        initMoveThread();
        initDrawOfferThread();
    }

    public State getGameState() {
        return getStateReader().peek();
    }

    public String getMoves() {
        return getMoveList().toString();
    }

    public String[] getPiecePlacementData() {
        return getBoard().getFen().getPiecePlacementData();
    }

    public boolean isWhite() {
        return getBoard().getFen().isWhite();
    }

    public String getCastling() {
        return getBoard().getFen().getCastling();
    }

    public boolean getWhiteKingCastle() {
        return getBoard().getFen().getWhiteKingCastle();
    }

    public boolean getWhiteQueenCastle() {
        return getBoard().getFen().getWhiteQueenCastle();
    }

    public boolean getBlackKingCastle() {
        return getBoard().getFen().getWhiteKingCastle();
    }

    public boolean getBlackQueenCastle() {
        return getBoard().getFen().getBlackKingCastle();
    }

    public String getEnPassant() {
        return getBoard().getFen().getEnPassant();
    }

    public int getHalfMoveClock() {
        return getBoard().getFen().getHalfMoveClock();
    }

    public int getFullMoveNumber() {
        return getBoard().getFen().getFullMoveNumber();
    }

    public String getFen() {
        return getBoard().getFen().toString();
    }

    public void startGame() {
        setBoard(new Board());
        setMoveList(new MoveList());
        setGameState(State.Start);
        printGame();
    }

    public void startNewGame() {
        acquire();
        startGame();
        setGameState(State.Playing);
        initMoveThread();
        getMoveThread().start();
    }

    public void makeMove(Square source, Square destination) {
        getMoveWriter().write(new Square[] { source, destination });
    }

    public boolean isOwnPieceOnSquare(Square square) {
        IBitboard piece = Bitboard.getSingleBit(Board.getIndex(square));
        IBitboard ownPieces = getBoard().getPieces(getBoard().getFen().isWhite());
        return Bitboard.overlap(piece, ownPieces);
    }

    public void resign() {
        System.out.println("Resigned");
        acquire();
        setGameState(State.Resignation);
        release();
    }

    public void offerDraw() {
        System.out.println("Offering draw");
        if (getDrawOfferThread().getState() == Thread.State.NEW) {
            getDrawOfferThread().start();
        } else {
            getDrawOfferThread().interrupt();
            initDrawOfferThread();
            getDrawOfferThread().start();
        }
    }

    public void acceptDraw() {
        getWriter().write("Y");
    }

    public void declineDraw() {
        getWriter().write("N");
    }

    public void claimDraw() {
        System.out.println("Draw claimed");
        acquire();
        setGameState(State.Draw);
        release();
    }

    public ArrayList<Square[]> getLegalMoves(Square square) {
        IPiece piece = getBoard().getPiece(square);
        ArrayList<Square[]> allMoves = piece.getPieceMoves(
                getBoard(),
                Bitboard.getSingleBit(Board.getIndex(square)));
        return getLegalMoves(allMoves);
    }

    public void promoteQueen() {
        getWriter().write("Q");
    }

    public void promoteRook() {
        getWriter().write("R");
    }

    public void promoteKnight() {
        getWriter().write("N");
    }

    public void promoteBishop() {
        getWriter().write("B");
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void clearError() {
        setErrorMessage(null);
    }

    /**
     * Retrieves the thread responsible for handling moves.
     *
     * @return The move thread.
     */
    Thread getMoveThread() {
        return moveThread;
    }

    /**
     * Retrieves the current game board.
     *
     * @return The game board.
     */
    IBoard getBoard() {
        return board;
    }

    /**
     * Retrieves the move list containing the history of moves.
     *
     * @return The move list.
     */
    IMoveList getMoveList() {
        return moveList;
    }

    /**
     * Initiates the game with the given FEN string.
     *
     * @param fen The Forsyth-Edwards Notation (FEN) string representing the initial
     *            state of the chess game.
     */
    void startGame(String fen) {
        setBoard(new Board(new ForsythEdwardsNotation(fen)));
        setMoveList(new MoveList());
        setGameState(State.Start);
        printGame();
    }

    /**
     * Ends the current game, resetting the game state to the start.
     */
    void gameOver() {
        setGameState(State.Start);
    }

    /**
     * Checks if the current game state represents a checkmate.
     *
     * @return True if it's a checkmate, false otherwise.
     */
    boolean isCheckmate() {
        return isCheck() && hasNoLegalMoves();
    }

    /**
     * Checks if the current game state represents a stalemate.
     *
     * @return True if it's a stalemate, false otherwise.
     */
    boolean isStalemate() {
        return !isCheck() && hasNoLegalMoves();
    }

    /**
     * Acquires the semaphore, blocking if necessary until the permit is available.
     */
    void acquire() {
        try {
            getSemaphore().acquire();
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Releases the semaphore, returning the permit to the semaphore.
     */
    void release() {
        getSemaphore().release();
    }

    /**
     * Retrieves all legal moves from the current position on the board.
     *
     * @return A list of legal moves.
     */
    private ArrayList<Square[]> getAllLegalMoves() {
        ArrayList<Square[]> allMoves = getBoard().getAllMoves(getBoard().getFen().isWhite());
        return getLegalMoves(allMoves);
    }

    /**
     * Filters the given list of moves to get only the legal moves.
     *
     * @param allMoves The list of all moves.
     * @return A list of legal moves.
     */
    private ArrayList<Square[]> getLegalMoves(ArrayList<Square[]> allMoves) {
        ArrayList<Square[]> legalMoves = new ArrayList<>();
        for (Square[] move : allMoves) {
            try {
                int src = Board.getIndex(move[0]);
                int dst = Board.getIndex(move[1]);
                boolean isIllegal = getBoard()
                        .getPiece(move[0])
                        .isInvalidMove(src, dst, getBoard());
                if (!isIllegal) {
                    legalMoves.add(new Square[] { move[0], move[1] });
                }
            } catch (Error ignored) {
            }
        }
        return legalMoves;
    }

    /**
     * Checks if there are no legal moves available in the current position.
     *
     * @return True if there are no legal moves, false otherwise.
     */
    private boolean hasNoLegalMoves() {
        return getAllLegalMoves().isEmpty();
    }

    /**
     * Checks if the current position is in check.
     *
     * @return True if the current position is in check, false otherwise.
     */
    private boolean isCheck() {
        boolean isWhite = getBoard().getFen().isWhite();
        IBitboard king = getBoard().getKing(isWhite);
        IBitboard attacks = getBoard().getAllOpponentAttacks();
        return Bitboard.overlap(king, attacks);
    }

    /**
     * Checks if the game is currently in progress.
     *
     * @return True if the game is in progress, false otherwise.
     */
    private boolean isPlaying() {
        return (getGameState() == State.Playing ||
                getGameState() == State.Promotion ||
                getGameState() == State.DrawOffer);
    }

    /**
     * Prints the current state of the game board and FEN string to the console.
     */
    private void printGame() {
        System.out.println(getBoard());
        System.out.println(getBoard().getFen());
    }

    /**
     * Prints a message indicating checkmate if the current game state is checkmate.
     */
    private void printCheckmate() {
        if (isCheckmate()) {
            setGameState(State.Checkmate);
            System.out.println("Checkmate!");
            System.out.println(getBoard().getFen().isWhite() ? "Black won." : "White won.");
        }
    }

    /**
     * Prints a message indicating stalemate if the current game state is stalemate.
     */
    private void printStalemate() {
        if (isStalemate()) {
            setGameState(State.Stalemate);
            System.out.println("Stalemate!");
        }
    }

    /**
     * Prints a message indicating a draw if the game has reached a draw condition.
     */
    private void printDraw() {
        if (getHalfMoveClock() >= ForsythEdwardsNotation.MAX_HALF_MOVE_CLOCK) {
            setGameState(State.Draw);
            System.out.println("Draw!");
        }
    }

    /**
     * Sets the current game state to the specified state.
     *
     * @param state The state to set.
     */
    private void setGameState(State state) {
        getStateReader().flush();
        getStateWriter().write(state);
    }

    /**
     * Retrieves the reader for the game state.
     *
     * @return The game state reader.
     */
    private IReader<State> getStateReader() {
        return stateReader;
    }

    /**
     * Sets the reader for the game state.
     *
     * @param stateQueue The blocking queue for game state.
     */
    private void setStateReader(BlockingQueue<State> stateQueue) {
        this.stateReader = new Reader<>(stateQueue);
    }

    /**
     * Retrieves the writer for the game state.
     *
     * @return The game state writer.
     */
    private IWriter<State> getStateWriter() {
        return stateWriter;
    }

    /**
     * Sets the writer for the game state.
     *
     * @param stateQueue The blocking queue for game state.
     */
    private void setStateWriter(BlockingQueue<State> stateQueue) {
        this.stateWriter = new Writer<>(stateQueue);
    }

    /**
     * Retrieves the blocking queue for the game state.
     *
     * @return The blocking queue for game state.
     */
    private BlockingQueue<State> getStateQueue() {
        return stateQueue;
    }

    /**
     * Sets the blocking queue for the game state.
     *
     * @param stateQueue The blocking queue for game state.
     */
    private void setStateQueue(BlockingQueue<State> stateQueue) {
        this.stateQueue = stateQueue;
    }

    /**
     * Sets the move list for the game.
     *
     * @param moveList The move list to set.
     */
    private void setMoveList(IMoveList moveList) {
        this.moveList = moveList;
    }

    /**
     * Sets the game board.
     *
     * @param board The game board to set.
     */
    private void setBoard(IBoard board) {
        this.board = board;
    }

    /**
     * Retrieves the reader for data input.
     *
     * @return The data input reader.
     */
    private IReader<String> getReader() {
        return reader;
    }

    /**
     * Sets the reader for data input.
     *
     * @param queue The blocking queue for data input.
     */
    private void setReader(BlockingQueue<String> queue) {
        reader = new Reader<>(queue);
    }

    /**
     * Retrieves the writer for data output.
     *
     * @return The data output writer.
     */
    private IWriter<String> getWriter() {
        return writer;
    }

    /**
     * Sets the writer for data output.
     *
     * @param queue The blocking queue for data output.
     */
    private void setWriter(BlockingQueue<String> queue) {
        this.writer = new Writer<>(queue);
    }

    /**
     * Retrieves the blocking queue for data.
     *
     * @return The blocking queue for data.
     */
    private BlockingQueue<String> getDataQueue() {
        return dataQueue;
    }

    /**
     * Sets the blocking queue for data.
     *
     * @param dataQueue The blocking queue for data.
     */
    private void setDataQueue(BlockingQueue<String> dataQueue) {
        this.dataQueue = dataQueue;
    }

    /**
     * Retrieves the blocking queue for moves.
     *
     * @return The blocking queue for moves.
     */
    private BlockingQueue<Square[]> getMoveQueue() {
        return moveQueue;
    }

    /**
     * Sets the blocking queue for moves.
     *
     * @param moveQueue The blocking queue for moves.
     */
    private void setMoveQueue(BlockingQueue<Square[]> moveQueue) {
        this.moveQueue = moveQueue;
    }

    /**
     * Retrieves the reader for moves.
     *
     * @return The reader for moves.
     */
    private IReader<Square[]> getMoveReader() {
        return moveReader;
    }

    /**
     * Sets the reader for moves.
     *
     * @param moveQueue The blocking queue for moves.
     */
    private void setMoveReader(BlockingQueue<Square[]> moveQueue) {
        this.moveReader = new Reader<>(moveQueue);
    }

    /**
     * Retrieves the writer for moves.
     *
     * @return The writer for moves.
     */
    private IWriter<Square[]> getMoveWriter() {
        return moveWriter;
    }

    /**
     * Sets the writer for moves.
     *
     * @param moveQueue The blocking queue for moves.
     */
    private void setMoveWriter(BlockingQueue<Square[]> moveQueue) {
        this.moveWriter = new Writer<>(moveQueue);
    }

    /**
     * Sets the error message for the game.
     *
     * @param errorMessage The error message to set.
     */
    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the thread responsible for handling moves.
     *
     * @param moveThread The move thread to set.
     */
    private void setMoveThread(Thread moveThread) {
        this.moveThread = moveThread;
    }

    /**
     * Retrieves the thread responsible for handling draw offers.
     *
     * @return The draw offer thread.
     */
    private Thread getDrawOfferThread() {
        return drawOfferThread;
    }

    /**
     * Sets the thread responsible for handling draw offers.
     *
     * @param drawOfferThread The draw offer thread to set.
     */
    private void setDrawOfferThread(Thread drawOfferThread) {
        this.drawOfferThread = drawOfferThread;
    }

    /**
     * Retrieves the semaphore used for synchronization.
     *
     * @return The semaphore.
     */
    private Semaphore getSemaphore() {
        return semaphore;
    }

    /**
     * Initializes the thread responsible for handling moves.
     */
    private void initMoveThread() {
        Thread moveThread = new Thread(() -> {
            release();
            if (getGameState() == State.Promotion) {
                setGameState(State.Playing);
            }
            while (isPlaying()) {
                Square[] move = getMoveReader().read();
                if (move == null) {
                    continue;
                }
                try {
                    getMoveList()
                            .makeMove(move[0], move[1], getBoard(), getReader(), getStateWriter());
                } catch (Error e) {
                    setErrorMessage(e.getMessage());
                } finally {
                    release();
                    getMoveReader().flush();
                }
                printGame();
                printCheckmate();
                printStalemate();
                printDraw();
            }
            System.out.println(getMoveList());
        });
        setMoveThread(moveThread);
    }

    /**
     * Initializes the thread responsible for handling draw offers.
     */
    private void initDrawOfferThread() {
        Thread drawOfferThread = new Thread(() -> {
            getReader().flush();
            State oldState = getGameState();
            setGameState(State.DrawOffer);
            System.out.println("Draw offered! Accept ? (Y)");
            String answer = getReader().read();
            if (answer == null) {
                setGameState(oldState);
                return;
            }
            if (answer.equals("Y")) {
                System.out.println("Draw accepted");
                setGameState(State.Draw);
            } else {
                System.out.println("Draw declined");
                setGameState(oldState);
            }
        });
        setDrawOfferThread(drawOfferThread);
    }
}
