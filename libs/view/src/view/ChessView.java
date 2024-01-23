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

	@Override
	public void settings() {
		size(getWidth(), getHeight());
		pixelDensity(displayDensity());
	}

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

	@Override
	public void mousePressed() {
		getController().handleMousePressed(mouseX, mouseY);
	}

	@Override
	public void mouseDragged() {
		getController().handleMouseDragged(mouseX, mouseY);
	}

	@Override
	public void mouseReleased() {
		getController().handleMouseReleased(mouseX, mouseY);
	}

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

	private void textSettings() {
		textAlign(CENTER, CENTER);
		textSize(28);
		fill(0);
	}

	private void drawGameOver() {
		drawPlaying();
		getResignButton().hide();
		getOfferDrawButton().hide();
		getStartButton().show();
		textAlign(CENTER, CENTER);
		textSize(32);
		text("Game over", getWidth() / 2.0f, getHeight() / 20.0f);
	}

	public void drawError() {
		fill(255, 0, 0);
		textAlign(CENTER, CENTER);
		textSize(20);
		text(getController().getErrorMessage(), (float) getWidth() / 2, (float) getHeight() / 20);
	}

	private void drawFen() {
		textSettings();
		text(getController().getFen(), getWidth() / 2.0f, getHeight() - getHeight() / 20.0f);
	}

	private void drawMoves() {
		fill(0);
		textAlign(LEFT);
		textSize(16);
		text(getController().getMoves(), getSquareSize() / 2.0f, getSquareSize() / 2.0f);
	}

	private void drawLegalMoves() {
		var moves = getController().getLegalMoves();
		if (moves == null || moves.isEmpty()) {
			return;
		}
		for (var move : moves) {
			highlightLegalDestination(move[1]);
		}
	}

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

	private void highlight() {
		fill(247, 247, 105);
	}

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

	private int getFileFromSquare(Square square) {
		return square.name().charAt(0) - 'a' + 1;
	}

	private int getRankFromSquare(Square square) {
		return Character.getNumericValue(square.name().charAt(1));
	}

	private int getLeftSquareOffset(int file) {
		return getLeftBoardOffset() + (file - 1) * getSquareSize();
	}

	private int getTopSquareOffset(int rank) {
		return getHeight() - getTopBoardOffset() - rank * getSquareSize();
	}

	private void drawBoard() {
		for (int rank = 8; rank > 0; rank--) {
			drawRank(rank);
		}
	}

	private void drawRank(int rank) {
		for (int file = 1; file <= 8; file++) {
			drawSquare(rank, file);
		}
	}

	private void drawSquare(int rank, int file) {
		fill(getFillColor(rank, file));
		noStroke();
		square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
	}

	private int getFillColor(int rank, int file) {
		boolean bothEven = rank % 2 == 0 && file % 2 == 0;
		boolean bothOdd = rank % 2 != 0 && file % 2 != 0;
		return bothEven || bothOdd ? getBlackColor() : getWhiteColor();
	}

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

	private void drawPiece(int rank, int file, PImage image) {
		if (image == null) {
			return;
		}
		int size = getSquareSize();
		image(image, getLeftSquareOffset(file), getTopSquareOffset(rank), size, size);
	}

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

	private IChessController getController() {
		return controller;
	}

	private void setCp5(ControlP5 cp5) {
		this.cp5 = cp5;
	}

	private ControlP5 getCp5() {
		return cp5;
	}

	private void initCp5() {
		setCp5(new ControlP5(this));
		getCp5().setFont(getFont());
	}

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

	private Button getStartButton() {
		return startButton;
	}

	private void setStartButton(Button startButton) {
		this.startButton = startButton;
	}

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

	private Button getResignButton() {
		return resignButton;
	}

	private void setResignButton(Button resignButton) {
		this.resignButton = resignButton;
	}

	private void initResignButton() {
		setResignButton(initButton("Resign game", "Resign"));
		getResignButton().hide().onRelease(event -> getController().resign());
	}

	private Button getClearErrorButton() {
		return clearErrorButton;
	}

	private void setClearErrorButton(Button clearErrorButton) {
		this.clearErrorButton = clearErrorButton;
	}

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

	private Button getOfferDrawButton() {
		return offerDrawButton;
	}

	private void setOfferDrawButton(Button offerDrawButton) {
		this.offerDrawButton = offerDrawButton;
	}

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

	private Button getAcceptDrawButton() {
		return acceptDrawButton;
	}

	private void setAcceptDrawButton(Button acceptDrawButton) {
		this.acceptDrawButton = acceptDrawButton;
	}

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

	private Button getDeclineDrawButton() {
		return declineDrawButton;
	}

	private void setDeclineDrawButton(Button declineDrawButton) {
		this.declineDrawButton = declineDrawButton;
	}

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

	private Button getPromoteQueenButton() {
		return promoteQueenButton;
	}

	private void setPromoteQueenButton(Button promoteQueenButton) {
		this.promoteQueenButton = promoteQueenButton;
	}

	private void initPromoteQueenButton() {
		setPromoteQueenButton(initButton("Promote queen button", "Promote queen"));
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		getPromoteQueenButton()
			.setColorBackground(background)
			.setPosition(
				(getWidth() / 2f) - getPromoteQueenButton().getWidth(),
				(getHeight() / 20f) - (getPromoteQueenButton().getHeight() / 2f)
			)
			.setWidth(getPromoteQueenButton().getWidth() / 2)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getPromoteQueenButton().getCaptionLabel().setColor(textColor).setText("Queen");
		getPromoteQueenButton()
			.hide()
			.onRelease(event -> {
				getController().promoteQueen();
				hidePromotionButtons();
			});
	}

	private Button getPromoteRookButton() {
		return promoteRookButton;
	}

	private void setPromoteRookButton(Button promoteRookButton) {
		this.promoteRookButton = promoteRookButton;
	}

	private void initPromoteRookButton() {
		setPromoteRookButton(initButton("Promote rook button", "Promote rook"));
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		getPromoteRookButton()
			.setColorBackground(background)
			.setPosition(
				(getWidth() / 2f) - (getPromoteRookButton().getWidth() / 2f),
				(getHeight() / 20f) - (getPromoteRookButton().getHeight() / 2f)
			)
			.setWidth(getPromoteRookButton().getWidth() / 2)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getPromoteRookButton().getCaptionLabel().setColor(textColor).setText("Rook");
		getPromoteRookButton()
			.hide()
			.onRelease(event -> {
				getController().promoteRook();
				hidePromotionButtons();
			});
	}

	private Button getPromoteKnightButton() {
		return promoteKnightButton;
	}

	private void setPromoteKnightButton(Button promoteKnightButton) {
		this.promoteKnightButton = promoteKnightButton;
	}

	private void initPromoteKnightButton() {
		setPromoteKnightButton(initButton("Promote knight button", "Promote knight"));
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		getPromoteKnightButton()
			.setColorBackground(background)
			.setPosition(
				getWidth() / 2f,
				(getHeight() / 20f) - (getPromoteKnightButton().getHeight() / 2f)
			)
			.setWidth(getPromoteKnightButton().getWidth() / 2)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getPromoteKnightButton().getCaptionLabel().setColor(textColor).setText("Knight");
		getPromoteKnightButton()
			.hide()
			.onRelease(event -> {
				getController().promoteKnight();
				hidePromotionButtons();
			});
	}

	private Button getPromoteBishopButton() {
		return promoteBishopButton;
	}

	private void setPromoteBishopButton(Button promoteBishopButton) {
		this.promoteBishopButton = promoteBishopButton;
	}

	private void initPromoteBishopButton() {
		setPromoteBishopButton(initButton("Promote bishop button", "Promote bishop"));
		int background = color(245, 60, 60);
		int hoverColor = color(205, 50, 50);
		int textColor = color(255);
		getPromoteBishopButton()
			.setColorBackground(background)
			.setPosition(
				(getWidth() / 2f) + getPromoteBishopButton().getWidth() / 2f,
				(getHeight() / 20f) - (getPromoteBishopButton().getHeight() / 2f)
			)
			.setWidth(getPromoteBishopButton().getWidth() / 2)
			.setColorActive(background)
			.setColorForeground(hoverColor);
		getPromoteBishopButton().getCaptionLabel().setColor(textColor).setText("Bishop");
		getPromoteBishopButton()
			.hide()
			.onRelease(event -> {
				getController().promoteBishop();
				hidePromotionButtons();
			});
	}

	private void hidePromotionButtons() {
		getPromoteQueenButton().hide();
		getPromoteRookButton().hide();
		getPromoteBishopButton().hide();
		getPromoteKnightButton().hide();
	}

	private void showPromotionButtons() {
		getPromoteQueenButton().show();
		getPromoteRookButton().show();
		getPromoteBishopButton().show();
		getPromoteKnightButton().show();
	}

	private PImage[] getPieceImages() {
		return pieceImages;
	}

	private void setPieceImages(PImage[] pieceImages) {
		this.pieceImages = pieceImages;
	}

	private void loadPieceImages() {
		setPieceImages(new PImage[12]);
		Thread imageLoaderThread = new Thread(this::loadImages);
		imageLoaderThread.start();
	}

	private void loadImages() {
		for (int i = 0; i < 12; i++) {
			getPieceImages()[i] = loadImage(getImagePath(i));
		}
	}

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

	private PFont getFont() {
		return font;
	}

	private void setFont(PFont font) {
		this.font = font;
	}

	private void loadFont() {
		String fontPath = "fonts/IosevkaTerm-ExtraLight.ttf";
		setFont(createFont(fontPath, 16));
	}

	private void setWidth(int width) {
		this.width = width;
	}

	private void setHeight(int height) {
		this.height = height;
	}

	private String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setLeftBoardOffset(int leftBoardOffset) {
		this.leftBoardOffset = leftBoardOffset;
	}

	private void setTopBoardOffset(int topBoardOffset) {
		this.topBoardOffset = topBoardOffset;
	}

	private void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	private int getWhiteColor() {
		return whiteColor;
	}

	private int getBlackColor() {
		return blackColor;
	}
}
