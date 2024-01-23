package view;

import api.IChessController;
import api.IChessView;
import api.Square;
import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * The {@link ChessView} class implements the {@link IChessView} interface and extends the Processing {@link PApplet}.
 * It serves as the graphical user interface (GUI) for the chess game, providing visual representation and user interaction.
 */
public class ChessView extends PApplet implements IChessView {

	/**
	 * The associated chess controller implementing the {@link IChessController} interface.
	 */
	private IChessController controller;

	/**
	 * The {@code ControlP5} instance for button management.
	 */
	private ControlP5 cp5;

	/**
	 * Button for resigning the game.
	 */
	private Button resignButton;

	/**
	 * Button for starting a new game.
	 */
	private Button startButton;

	/**
	 * Button for clearing error messages.
	 */
	private Button clearErrorButton;

	/**
	 * Button for offering a draw.
	 */
	private Button offerDrawButton;

	/**
	 * Button for accepting a draw offer.
	 */
	private Button acceptDrawButton;

	/**
	 * Button for declining a draw offer.
	 */
	private Button declineDrawButton;

	/**
	 * Button for promoting a pawn to a queen.
	 */
	private Button promoteQueenButton;

	/**
	 * Button for promoting a pawn to a rook.
	 */
	private Button promoteRookButton;

	/**
	 * Button for promoting a pawn to a knight.
	 */
	private Button promoteKnightButton;

	/**
	 * Button for promoting a pawn to a bishop.
	 */
	private Button promoteBishopButton;

	/**
	 * Array of images representing chess pieces.
	 */
	private PImage[] pieceImages;

	/**
	 * The font used for text rendering.
	 */
	private PFont font;

	/**
	 * The title of the game window.
	 */
	private String title;

	/**
	 * The dimensions of the game window.
	 */
	private int width;

	/**
	 * The dimensions of the game window.
	 */
	private int height;

	/**
	 * Offsets for the chessboard within the window.
	 */
	private int leftBoardOffset;

	/**
	 * Offsets for the chessboard within the window.
	 */
	private int topBoardOffset;

	/**
	 * The size of a chessboard square.
	 */
	private int squareSize;

	/**
	 * Colors representing white squares.
	 */
	private final int whiteColor = color(237, 214, 176);

	/**
	 * Colors representing black squares.
	 */
	private final int blackColor = color(184, 135, 98);

	/**
	 * The constructor initializes the dimensions and title of the game window.
	 *
	 * @param width  The width of the game window.
	 * @param height The height of the game window.
	 * @param title  The title of the game window.
	 */
	public ChessView(int width, int height, String title) {
		setWidth(width);
		setHeight(height);
		setTitle(title);
	}

	/**
	 * Configures the size and pixel density of the game window based on the specified width and height.
	 */
	@Override
	public void settings() {
		size(getWidth(), getHeight());
		pixelDensity(displayDensity());
	}

	/**
	 * Sets up the initial configuration for the chess game, including window title, square size, board offsets,
	 * font, GUI components, piece images, and initiates the game start.
	 */
	@Override
	public void setup() {
		windowTitle(getTitle());
		setSquareSize(getHeight() / 10);
		setLeftBoardOffset((getWidth() / 2) - getSquareSize() * 4);
		setTopBoardOffset(getHeight() / 10);
		loadFont();
		textFont(getFont());
		initCp5();
		initStartButton();
		initResignButton();
		initErrorButton();
		initOfferDrawButton();
		initAcceptDrawButton();
		initDeclineDrawButton();
		initPromoteQueenButton();
		initPromoteRookButton();
		initPromoteKnightButton();
		initPromoteBishopButton();
		loadPieceImages();
		getController().startGame();
	}

	/**
	 * Handles the rendering of the game window, background, error message, and updates the frame.
	 */
	@Override
	public void draw() {
		background(221, 221, 221);
		if (getController().getErrorMessage() != null) {
			getClearErrorButton().show();
			drawError();
		} else {
			getClearErrorButton().hide();
		}
		getController().nextFrame();
	}

	/**
	 * Handles the processing of mouse press events by forwarding them to the chess controller.
	 */
	@Override
	public void mousePressed() {
		getController().handleMousePressed(mouseX, mouseY);
	}

	/**
	 * Handles the processing of mouse drag events by forwarding them to the chess controller.
	 */
	@Override
	public void mouseDragged() {
		getController().handleMouseDragged(mouseX, mouseY);
	}

	/**
	 * Handles the processing of mouse release events by forwarding them to the chess controller.
	 */
	@Override
	public void mouseReleased() {
		getController().handleMouseReleased(mouseX, mouseY);
	}

	/**
	 * Handles the processing of mouse move events by forwarding them to the chess controller.
	 */
	@Override
	public void mouseMoved() {
		if (mousePressed) {
			return;
		}
		getController().handleMouseMoved(mouseX, mouseY);
	}

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	public int getLeftBoardOffset() {
		return leftBoardOffset;
	}

	public int getTopBoardOffset() {
		return topBoardOffset;
	}

	public int getSquareSize() {
		return squareSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void drawStart() {
		drawBoard();
		drawPieces();
	}

	public void drawPlaying() {
		drawBoard();
		highlightSource();
		highlightDraggedSquare();
		drawPieces();
		drawFen();
		drawMoves();
		drawLegalMoves();
	}

	public void drawCheckmate() {
		drawGameOver();
		String winner = getController().isWhite() ? "Black" : "White";
		textSettings();
		text(
			"Checkmate! " + winner + " won.",
			getWidth() - (getLeftBoardOffset() / 2.0f),
			getHeight() / 2.0f
		);
	}

	public void drawStalemate() {
		drawGameOver();
		textSettings();
		text("Stalemate!", getWidth() - (getLeftBoardOffset() / 2.0f), getHeight() / 2.0f);
	}

	public void drawResignation() {
		drawGameOver();
		String winner = getController().isWhite() ? "Black" : "White";
		textSettings();
		text(
			"Resignation! " + winner + " won.",
			getWidth() - (getLeftBoardOffset() / 2.0f),
			getHeight() / 2.0f
		);
	}

	public void drawDraw() {
		drawGameOver();
		textSettings();
		text("Draw!", getWidth() - (getLeftBoardOffset() / 2.0f), getHeight() / 2.0f);
	}

	public void drawPromotion() {
		drawPlaying();
		showPromotionButtons();
		textSettings();
		text("Promotion!", getWidth() - (getLeftBoardOffset() / 2.0f), getHeight() / 2.0f);
	}

	public void drawDrawOffer() {
		drawPlaying();
		textSettings();
		text("Draw offer!", getWidth() - (getLeftBoardOffset() / 2.0f), getHeight() / 2.0f);
	}

	/**
	 * Configures the text alignment and size for displaying various elements in the chess game.
	 */
	private void textSettings() {
		textAlign(CENTER, CENTER);
		textSize(28);
		fill(0);
	}

	/**
	 * Draws the "Game Over" message on the screen, hiding unnecessary buttons and showing the start button.
	 */
	private void drawGameOver() {
		drawPlaying();
		getResignButton().hide();
		getOfferDrawButton().hide();
		getStartButton().show();
		textAlign(CENTER, CENTER);
		textSize(32);
		text("Game over", getWidth() / 2.0f, getHeight() / 20.0f);
	}

	/**
	 * Draws the error message on the screen, if present.
	 */
	private void drawError() {
		fill(255, 0, 0);
		textAlign(CENTER, CENTER);
		textSize(20);
		text(getController().getErrorMessage(), (float) getWidth() / 2, (float) getHeight() / 20);
	}

	/**
	 * Draws the FEN (Forsyth-Edwards Notation) string on the screen.
	 */
	private void drawFen() {
		textSettings();
		text(getController().getFen(), getWidth() / 2.0f, getHeight() - getHeight() / 20.0f);
	}

	/**
	 * Draws the list of moves on the screen.
	 */
	private void drawMoves() {
		fill(0);
		textAlign(LEFT);
		textSize(16);
		text(getController().getMoves(), getSquareSize() / 2.0f, getSquareSize() / 2.0f);
	}

	/**
	 * Highlights legal move destinations on the board.
	 */
	private void drawLegalMoves() {
		var moves = getController().getLegalMoves();
		if (moves == null || moves.isEmpty()) {
			return;
		}
		for (var move : moves) {
			highlightLegalDestination(move[1]);
		}
	}

	/**
	 * Highlights the destination square of a legal move.
	 *
	 * @param square The square to be highlighted.
	 */
	private void highlightLegalDestination(Square square) {
		int rank = getRankFromSquare(square);
		int file = getFileFromSquare(square);
		int halfSquareSize = (int) (getSquareSize() / 2.0f);
		int fileOffset = getLeftSquareOffset(file) + halfSquareSize;
		int rankOffset = getTopSquareOffset(rank) + halfSquareSize;
		fill(color(213, 192, 158, 125));
		noStroke();
		circle(fileOffset, rankOffset, getSquareSize() / 3.0f);
	}

	/**
	 * Highlights the square being dragged by the player.
	 */
	private void highlightDraggedSquare() {
		Square square = getController().getDraggedSquare();
		if (square == null) {
			return;
		}
		int rank = getRankFromSquare(square);
		int file = getFileFromSquare(square);
		fill(getFillColor(rank, file));
		stroke(241, 233, 190);
		strokeWeight(2);
		if (square.equals(getController().getSource())) {
			highlight();
		}
		square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
	}

	/**
	 * Sets the fill color for highlighting a square to a yellow signal value.
	 */
	private void highlight() {
		fill(247, 247, 105);
	}

	/**
	 * Highlights the source square of a move.
	 */
	private void highlightSource() {
		Square source = getController().getSource();
		if (source == null) {
			return;
		}
		int rank = getRankFromSquare(source);
		int file = getFileFromSquare(source);
		highlight();
		square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
	}

	/**
	 * Gets the file index of a square.
	 *
	 * @param square The square for which to get the file index.
	 * @return The file index.
	 */
	private int getFileFromSquare(Square square) {
		return square.name().charAt(0) - 'a' + 1;
	}

	/**
	 * Gets the rank index of a square.
	 *
	 * @param square The square for which to get the rank index.
	 * @return The rank index.
	 */
	private int getRankFromSquare(Square square) {
		return Character.getNumericValue(square.name().charAt(1));
	}

	/**
	 * Gets the left offset of a square on the board.
	 *
	 * @param file The file index of the square.
	 * @return The left offset of the square.
	 */
	private int getLeftSquareOffset(int file) {
		return getLeftBoardOffset() + (file - 1) * getSquareSize();
	}

	/**
	 * Gets the top offset of a square on the board.
	 *
	 * @param rank The rank index of the square.
	 * @return The top offset of the square.
	 */
	private int getTopSquareOffset(int rank) {
		return getHeight() - getTopBoardOffset() - rank * getSquareSize();
	}

	/**
	 * Draws the chess board with alternating colors for squares.
	 */
	private void drawBoard() {
		for (int rank = 8; rank > 0; rank--) {
			drawRank(rank);
		}
	}

	/**
	 * Draws a rank on the chess board.
	 *
	 * @param rank The rank index.
	 */
	private void drawRank(int rank) {
		for (int file = 1; file <= 8; file++) {
			drawSquare(rank, file);
		}
	}

	/**
	 * Draws a square on the chess board.
	 *
	 * @param rank The rank index of the square.
	 * @param file The file index of the square.
	 */
	private void drawSquare(int rank, int file) {
		fill(getFillColor(rank, file));
		noStroke();
		square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
	}

	/**
	 * Determines the fill color for a square based on its rank and file indices.
	 *
	 * @param rank The rank index of the square.
	 * @param file The file index of the square.
	 * @return The fill color for the square.
	 */
	private int getFillColor(int rank, int file) {
		boolean bothEven = rank % 2 == 0 && file % 2 == 0;
		boolean bothOdd = rank % 2 != 0 && file % 2 != 0;
		return bothEven || bothOdd ? getBlackColor() : getWhiteColor();
	}

	/**
	 * Draws all the chess pieces on the board.
	 */
	private void drawPieces() {
		String[] ppd = getController().getPiecePlacementData();
		int i = 0;
		for (int rank = 8; rank > 0; rank--) {
			int fileToRenderPieceOn = 0;
			for (var c : ppd[i].toCharArray()) {
				if (Character.isDigit(c)) {
					int emptySquares = Character.getNumericValue(c);
					fileToRenderPieceOn += emptySquares;
				} else {
					fileToRenderPieceOn++;
					drawPiece(rank, fileToRenderPieceOn, pieceImageFromChar(c));
				}
			}
			i++;
		}
	}

	/**
	 * Draws a specific chess piece on the board.
	 *
	 * @param rank  The rank index of the piece.
	 * @param file  The file index of the piece.
	 * @param image The image representing the chess piece.
	 */
	private void drawPiece(int rank, int file, PImage image) {
		if (image == null) {
			return;
		}
		int size = getSquareSize();
		image(image, getLeftSquareOffset(file), getTopSquareOffset(rank), size, size);
	}

	/**
	 * Retrieves the image of a chess piece based on its character representation.
	 *
	 * @param c The character representation of the chess piece.
	 * @return The image of the chess piece.
	 */
	private PImage pieceImageFromChar(char c) {
		return switch (c) {
			case 'p' -> getPieceImages()[0];
			case 'r' -> getPieceImages()[1];
			case 'n' -> getPieceImages()[2];
			case 'b' -> getPieceImages()[3];
			case 'q' -> getPieceImages()[4];
			case 'k' -> getPieceImages()[5];
			case 'P' -> getPieceImages()[6];
			case 'R' -> getPieceImages()[7];
			case 'N' -> getPieceImages()[8];
			case 'B' -> getPieceImages()[9];
			case 'Q' -> getPieceImages()[10];
			case 'K' -> getPieceImages()[11];
			default -> throw new IllegalStateException("Unexpected value: " + c);
		};
	}

	/**
	 * Retrieves the chess controller associated with this view.
	 *
	 * @return The chess controller.
	 */
	private IChessController getController() {
		return controller;
	}

	/**
	 * Retrieves the ControlP5 instance used for GUI components.
	 *
	 * @return The ControlP5 instance.
	 */
	private ControlP5 getCp5() {
		return cp5;
	}

	/**
	 * Sets the ControlP5 instance for GUI components.
	 *
	 * @param cp5 The ControlP5 instance to set.
	 */
	private void setCp5(ControlP5 cp5) {
		this.cp5 = cp5;
	}

	/**
	 * Initiates the ControlP5 library for GUI components.
	 */
	private void initCp5() {
		setCp5(new ControlP5(this));
		getCp5().setFont(getFont());
	}

	/**
	 * Initializes a button with the specified label and text.
	 *
	 * @param label The label for the button.
	 * @param text  The text displayed on the button.
	 * @return The initialized button.
	 */
	private Button initButton(String label, String text) {
		int background = color(48, 46, 43);
		int hoverColor = color(28, 26, 24);
		int textColor = color(193, 193, 192);
		int buttonWidth = getWidth() / 10;
		int buttonHeight = getHeight() / 20;
		float leftOffset = getWidth() - (getLeftBoardOffset() / 2.0f) - buttonWidth / 2.0f;
		float topOffset = getHeight() - 2 * getTopBoardOffset() + buttonHeight / 2.0f;
		Button button = getCp5()
			.addButton(label)
			.setSwitch(true)
			.setPosition(leftOffset, topOffset)
			.setWidth(buttonWidth)
			.setHeight(buttonHeight)
			.setColorBackground(background)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		button
			.getCaptionLabel()
			.toUpperCase(false)
			.setColor(textColor)
			.setFont(getFont())
			.setText(text)
			.setSize(20);
		return button;
	}

	/**
	 * Retrieves the "Start Game" button.
	 *
	 * @return The "Start Game" button.
	 */
	private Button getStartButton() {
		return startButton;
	}

	/**
	 * Sets the "Start Game" button.
	 *
	 * @param startButton The "Start Game" button to set.
	 */
	private void setStartButton(Button startButton) {
		this.startButton = startButton;
	}

	/**
	 * Initiates the "Start Game" button with appropriate settings and actions.
	 */
	private void initStartButton() {
		setStartButton(initButton("Start game button", "Start new game"));
		getStartButton()
			.onRelease(event -> {
				getStartButton().hide();
				getResignButton().show();
				getOfferDrawButton().show();
				getController().startNewGame();
			});
	}

	/**
	 * Retrieves the "Resign" button.
	 *
	 * @return The "Resign" button.
	 */
	private Button getResignButton() {
		return resignButton;
	}

	/**
	 * Sets the "Resign" button.
	 *
	 * @param resignButton The "Resign" button to set.
	 */
	private void setResignButton(Button resignButton) {
		this.resignButton = resignButton;
	}

	/**
	 * Initiates the "Resign" button with appropriate settings and actions.
	 */
	private void initResignButton() {
		setResignButton(initButton("Resign game", "Resign"));
		getResignButton().hide().onRelease(event -> getController().resign());
	}

	/**
	 * Retrieves the "Clear Error" button.
	 *
	 * @return The "Clear Error" button.
	 */
	private Button getClearErrorButton() {
		return clearErrorButton;
	}

	/**
	 * Sets the "Clear Error" button.
	 *
	 * @param clearErrorButton The "Clear Error" button to set.
	 */
	private void setClearErrorButton(Button clearErrorButton) {
		this.clearErrorButton = clearErrorButton;
	}

	/**
	 * Initiates the "Clear Error" button with appropriate settings and actions.
	 */
	private void initErrorButton() {
		setClearErrorButton(initButton("Clear error button", "Clear error"));
		int background = color(200, 46, 43);
		int hoverColor = color(190, 26, 24);
		int textColor = color(255);
		var pos = getClearErrorButton().getPosition();
		getClearErrorButton()
			.setColorBackground(background)
			.setColorActive(background)
			.setPosition(pos[0], pos[1] - ((4 * getHeight()) / 20.0f))
			.setColorForeground(hoverColor);
		getClearErrorButton().getCaptionLabel().setColor(textColor).setText("Clear error");
		getClearErrorButton()
			.hide()
			.onRelease(event -> {
				getController().clearErrorMessage();
				getClearErrorButton().hide();
			});
	}

	/**
	 * Retrieves the "Offer Draw" button.
	 *
	 * @return The "Offer Draw" button.
	 */
	private Button getOfferDrawButton() {
		return offerDrawButton;
	}

	/**
	 * Sets the "Offer Draw" button.
	 *
	 * @param offerDrawButton The "Offer Draw" button to set.
	 */
	private void setOfferDrawButton(Button offerDrawButton) {
		this.offerDrawButton = offerDrawButton;
	}

	/**
	 * Initiates the "Offer Draw" button with appropriate settings and actions.
	 */
	private void initOfferDrawButton() {
		setOfferDrawButton(initButton("Offer draw button", "Offer draw"));
		int background = color(176, 196, 222);
		int hoverColor = color(166, 181, 202);
		int textColor = color(0);
		var pos = getOfferDrawButton().getPosition();
		getOfferDrawButton()
			.setColorBackground(background)
			.setPosition(pos[0], pos[1] - ((2 * getHeight()) / 20.0f))
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getOfferDrawButton().getCaptionLabel().setColor(textColor).setText("Offer draw");
		getOfferDrawButton()
			.hide()
			.onRelease(event -> {
				getController().offerDraw();
				getOfferDrawButton().hide();
				getAcceptDrawButton().show();
				getDeclineDrawButton().show();
			});
	}

	/**
	 * Retrieves the "Accept Draw" button.
	 *
	 * @return The "Accept Draw" button.
	 */
	private Button getAcceptDrawButton() {
		return acceptDrawButton;
	}

	/**
	 * Sets the "Accept Draw" button.
	 *
	 * @param acceptDrawButton The "Accept Draw" button to set.
	 */
	private void setAcceptDrawButton(Button acceptDrawButton) {
		this.acceptDrawButton = acceptDrawButton;
	}

	/**
	 * Initiates the "Accept Draw" button with appropriate settings and actions.
	 */
	private void initAcceptDrawButton() {
		setAcceptDrawButton(initButton("Accept draw button", "Accept"));
		int background = color(106, 213, 65);
		int hoverColor = color(96, 200, 55);
		int textColor = color(255);
		var pos = getAcceptDrawButton().getPosition();
		getAcceptDrawButton()
			.setColorBackground(background)
			.setPosition(
				pos[0] - getAcceptDrawButton().getWidth() / 2.0f,
				pos[1] - ((4 * getHeight()) / 20.0f)
			)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getAcceptDrawButton().getCaptionLabel().setColor(textColor).setText("Accept");
		getAcceptDrawButton()
			.hide()
			.onRelease(event -> {
				getController().acceptDraw();
				getAcceptDrawButton().hide();
				getDeclineDrawButton().hide();
			});
	}

	/**
	 * Retrieves the "Decline Draw" button.
	 *
	 * @return The "Decline Draw" button.
	 */
	private Button getDeclineDrawButton() {
		return declineDrawButton;
	}

	/**
	 * Sets the "Decline Draw" button.
	 *
	 * @param declineDrawButton The "Decline Draw" button to set.
	 */
	private void setDeclineDrawButton(Button declineDrawButton) {
		this.declineDrawButton = declineDrawButton;
	}

	/**
	 * Initiates the "Decline Draw" button with appropriate settings and actions.
	 */
	private void initDeclineDrawButton() {
		setDeclineDrawButton(initButton("Decline draw button", "Decline"));
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		var pos = getDeclineDrawButton().getPosition();
		getDeclineDrawButton()
			.setColorBackground(background)
			.setPosition(
				pos[0] + getDeclineDrawButton().getWidth() / 2.0f,
				pos[1] - ((4 * getHeight()) / 20.0f)
			)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getDeclineDrawButton().getCaptionLabel().setColor(textColor).setText("Decline");
		getDeclineDrawButton()
			.hide()
			.onRelease(event -> {
				getController().declineDraw();
				getOfferDrawButton().show();
				getAcceptDrawButton().hide();
				getDeclineDrawButton().hide();
			});
	}

	/**
	 * Initiates the promotion button with the specified name, label, and text at the given position.
	 *
	 * @param name  The name of the button.
	 * @param label The label for the button.
	 * @param text  The text displayed on the button.
	 * @param x     The x-coordinate of the button position.
	 * @param y     The y-coordinate of the button position.
	 * @return The initialized promotion button.
	 */
	private Button initPromotionButton(String name, String label, String text, float x, float y) {
		Button promotionButton = initButton(name, label);
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		promotionButton
			.hide()
			.setColorBackground(background)
			.setPosition(x, y)
			.setWidth(getPromoteBishopButton().getWidth() / 2)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getPromoteBishopButton().getCaptionLabel().setColor(textColor).setText(text);
		return promotionButton;
	}

	/**
	 * Retrieves the "Promote Queen" button.
	 *
	 * @return The "Promote Queen" button.
	 */
	private Button getPromoteQueenButton() {
		return promoteQueenButton;
	}

	/**
	 * Sets the "Promote Queen" button.
	 *
	 * @param promoteQueenButton The "Promote Queen" button to set.
	 */
	private void setPromoteQueenButton(Button promoteQueenButton) {
		this.promoteQueenButton = promoteQueenButton;
	}

	/**
	 * Initiates the "Promote Queen" button with appropriate settings and actions.
	 */
	private void initPromoteQueenButton() {
		float x = (getWidth() / 2f) - getPromoteQueenButton().getWidth();
		float y = (getHeight() / 20f) - (getPromoteQueenButton().getHeight() / 2f);
		setPromoteQueenButton(
			initPromotionButton("Promote queen button", "Promote queen", "Queen", x, y)
		);
		getPromoteQueenButton()
			.onRelease(event -> {
				getController().promoteQueen();
				hidePromotionButtons();
			});
	}

	/**
	 * Retrieves the "Promote Rook" button.
	 *
	 * @return The "Promote Rook" button.
	 */
	private Button getPromoteRookButton() {
		return promoteRookButton;
	}

	/**
	 * Sets the "Promote Rook" button.
	 *
	 * @param promoteRookButton The "Promote Rook" button to set.
	 */
	private void setPromoteRookButton(Button promoteRookButton) {
		this.promoteRookButton = promoteRookButton;
	}

	/**
	 * Initiates the "Promote Rook" button with appropriate settings and actions.
	 */
	private void initPromoteRookButton() {
		float x = (getWidth() / 2f) - (getPromoteRookButton().getWidth() / 2f);
		float y = (getHeight() / 20f) - (getPromoteRookButton().getHeight() / 2f);
		setPromoteRookButton(
			initPromotionButton("Promote rook button", "Promote rook", "Rook", x, y)
		);
		getPromoteRookButton()
			.onRelease(event -> {
				getController().promoteRook();
				hidePromotionButtons();
			});
	}

	/**
	 * Retrieves the "Promote Knight" button.
	 *
	 * @return The "Promote Knight" button.
	 */
	private Button getPromoteKnightButton() {
		return promoteKnightButton;
	}

	/**
	 * Sets the "Promote Knight" button.
	 *
	 * @param promoteKnightButton The "Promote Knight" button to set.
	 */
	private void setPromoteKnightButton(Button promoteKnightButton) {
		this.promoteKnightButton = promoteKnightButton;
	}

	/**
	 * Initiates the "Promote Knight" button with appropriate settings and actions.
	 */
	private void initPromoteKnightButton() {
		float x = getWidth() / 2f;
		float y = (getHeight() / 20f) - (getPromoteKnightButton().getHeight() / 2f);
		setPromoteKnightButton(
			initPromotionButton("Promote knight button", "Promote knight", "Knight", x, y)
		);
		getPromoteKnightButton()
			.onRelease(event -> {
				getController().promoteKnight();
				hidePromotionButtons();
			});
	}

	/**
	 * Retrieves the "Promote Bishop" button.
	 *
	 * @return The "Promote Bishop" button.
	 */
	private Button getPromoteBishopButton() {
		return promoteBishopButton;
	}

	/**
	 * Sets the "Promote Bishop" button.
	 *
	 * @param promoteBishopButton The "Promote Bishop" button to set.
	 */
	private void setPromoteBishopButton(Button promoteBishopButton) {
		this.promoteBishopButton = promoteBishopButton;
	}

	/**
	 * Initiates the "Promote Bishop" button with appropriate settings and actions.
	 */
	private void initPromoteBishopButton() {
		float x = (getWidth() / 2f) + getPromoteBishopButton().getWidth() / 2f;
		float y = (getHeight() / 20f) - (getPromoteBishopButton().getHeight() / 2f);
		setPromoteBishopButton(
			initPromotionButton("Promote bishop button", "Promote bishop", "Bishop", x, y)
		);
		getPromoteBishopButton()
			.onRelease(event -> {
				getController().promoteBishop();
				hidePromotionButtons();
			});
	}

	/**
	 * Hides all promotion buttons.
	 */
	private void hidePromotionButtons() {
		getPromoteQueenButton().hide();
		getPromoteRookButton().hide();
		getPromoteBishopButton().hide();
		getPromoteKnightButton().hide();
	}

	/**
	 * Shows all promotion buttons.
	 */
	private void showPromotionButtons() {
		getPromoteQueenButton().show();
		getPromoteRookButton().show();
		getPromoteBishopButton().show();
		getPromoteKnightButton().show();
	}

	/**
	 * Retrieves the array of piece images.
	 *
	 * @return The array of piece images.
	 */
	private PImage[] getPieceImages() {
		return pieceImages;
	}

	/**
	 * Sets the array of piece images.
	 *
	 * @param pieceImages The array of piece images.
	 */
	private void setPieceImages(PImage[] pieceImages) {
		this.pieceImages = pieceImages;
	}

	/**
	 * Loads chess piece images asynchronously.
	 */
	private void loadPieceImages() {
		setPieceImages(new PImage[12]);
		Thread imageLoaderThread = new Thread(this::loadImages);
		imageLoaderThread.start();
	}

	/**
	 * Loads the images for each chess piece type.
	 */
	private void loadImages() {
		for (int i = 0; i < 12; i++) {
			getPieceImages()[i] = loadImage(getImagePath(i));
		}
	}

	/**
	 * Retrieves the file path for the image associated with the given piece index.
	 *
	 * @param index The index of the piece.
	 * @return The file path for the image of the corresponding piece.
	 * @throws Error if the index is invalid.
	 */
	private String getImagePath(int index) {
		return switch (index) {
			case 0 -> "images/bp.png";
			case 1 -> "images/br.png";
			case 2 -> "images/bn.png";
			case 3 -> "images/bb.png";
			case 4 -> "images/bq.png";
			case 5 -> "images/bk.png";
			case 6 -> "images/wp.png";
			case 7 -> "images/wr.png";
			case 8 -> "images/wn.png";
			case 9 -> "images/wb.png";
			case 10 -> "images/wq.png";
			case 11 -> "images/wk.png";
			default -> throw new Error("Invalid image index");
		};
	}

	/**
	 * Retrieves the font used for text rendering.
	 *
	 * @return The font used for text rendering.
	 */
	private PFont getFont() {
		return font;
	}

	/**
	 * Sets the font used for text rendering.
	 *
	 * @param font The font used for text rendering.
	 */
	private void setFont(PFont font) {
		this.font = font;
	}

	/**
	 * Loads the custom font for text rendering.
	 */
	private void loadFont() {
		String fontPath = "fonts/IosevkaTerm-ExtraLight.ttf";
		setFont(createFont(fontPath, 16));
	}

	/**
	 * Sets the width of the game window.
	 *
	 * @param width The width of the game window.
	 */
	private void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height of the game window.
	 *
	 * @param height The height of the game window.
	 */
	private void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Retrieves the title of the game window.
	 *
	 * @return The title of the game window.
	 */
	private String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the game window.
	 *
	 * @param title The title of the game window.
	 */
	private void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the left offset of the chessboard.
	 *
	 * @param leftBoardOffset The left offset of the chessboard.
	 */
	private void setLeftBoardOffset(int leftBoardOffset) {
		this.leftBoardOffset = leftBoardOffset;
	}

	/**
	 * Sets the top offset of the chessboard.
	 *
	 * @param topBoardOffset The top offset of the chessboard.
	 */
	private void setTopBoardOffset(int topBoardOffset) {
		this.topBoardOffset = topBoardOffset;
	}

	/**
	 * Sets the size of each square on the chessboard.
	 *
	 * @param squareSize The size of each square on the chessboard.
	 */
	private void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	/**
	 * Retrieves the color representing a white square on the chessboard.
	 *
	 * @return The color representing a white square.
	 */
	private int getWhiteColor() {
		return whiteColor;
	}

	/**
	 * Retrieves the color representing a black square on the chessboard.
	 *
	 * @return The color representing a black square.
	 */
	private int getBlackColor() {
		return blackColor;
	}
}
