package view;

import api.controller.IChessController;
import api.model.Pieces;
import api.model.Square;
import api.view.IChessView;
import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.KeyEvent;

public class ChessView extends PApplet implements IChessView {

	private IChessController controller;

	public void setController(IChessController controller) {
		this.controller = controller;
	}

	private IChessController getController() {
		return controller;
	}

	private final int whiteColor = color(237, 214, 176);

	private int getWhiteColor() {
		return whiteColor;
	}

	private final int blackColor = color(181, 136, 99);

	private int getBlackColor() {
		return blackColor;
	}

	private ControlP5 cp5;

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

	private Button resignButton;

	private Button getResignButton() {
		return resignButton;
	}

	private void setResignButton(Button resignButton) {
		this.resignButton = resignButton;
	}

	private void initResignButton() {
		int background = color(48, 46, 43);
		int hoverColor = color(28, 26, 24);
		int textColor = color(193, 193, 192);
		int buttonWidth = width / 10;
		int buttonHeight = height / 20;
		float leftOffset = width - (getLeftBoardOffset() / 2.0f) - buttonWidth / 2.0f;
		float topOffset = height - 2 * getTopBoardOffset() + buttonHeight / 2.0f;
		setResignButton(getCp5().addButton("Resign"));
		getResignButton()
			.setSwitch(true)
			.setPosition(leftOffset, topOffset)
			.setWidth(buttonWidth)
			.setHeight(buttonHeight)
			.setColorBackground(background)
			.setColorActive(background)
			.setColorForeground(hoverColor)
			.onRelease(event -> getController().resign());
		getResignButton()
			.getCaptionLabel()
			.toUpperCase(false)
			.setColor(textColor)
			.setFont(getFont())
			.setText("Resign")
			.setSize(20);
	}

	private PImage[] pieceImages;

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
			case 0 -> "images/black/rook.png";
			case 1 -> "images/black/knight.png";
			case 2 -> "images/black/bishop.png";
			case 3 -> "images/black/queen.png";
			case 4 -> "images/black/king.png";
			case 5 -> "images/black/pawn.png";
			case 6 -> "images/white/rook.png";
			case 7 -> "images/white/knight.png";
			case 8 -> "images/white/bishop.png";
			case 9 -> "images/white/queen.png";
			case 10 -> "images/white/king.png";
			case 11 -> "images/white/pawn.png";
			default -> throw new Error("Invalid image index");
		};
	}

	private PFont font;

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

	private int leftBoardOffset;

	private int getLeftBoardOffset() {
		return leftBoardOffset;
	}

	private void setLeftBoardOffset(int leftBoardOffset) {
		this.leftBoardOffset = leftBoardOffset;
	}

	private int topBoardOffset;

	private int getTopBoardOffset() {
		return topBoardOffset;
	}

	private void setTopBoardOffset(int topBoardOffset) {
		this.topBoardOffset = topBoardOffset;
	}

	private int squareSize;

	private int getSquareSize() {
		return squareSize;
	}

	private void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	@Override
	public void settings() {
		fullScreen();
		pixelDensity(displayDensity());
	}

	@Override
	public void setup() {
		windowTitle("Chess");
		setSquareSize(height / 10);
		setLeftBoardOffset((width / 2) - getSquareSize() * 4);
		setTopBoardOffset(height / 10);
		loadFont();
		textFont(getFont());
		initCp5();
		initResignButton();
		loadPieceImages();
		getController().startGame();
	}

	@Override
	public void draw() {
		getController().nextFrame();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		getController().handleUserInput(key, keyCode);
	}

	@Override
	public void mouseDragged() {
		Square square = getSquareFromCoordinates(mouseX, mouseY);
		if (square != null) {
			System.out.println("Dragged on square " + square);
		} else {
			System.out.println("Dragged outside board");
		}
	}

	@Override
	public void mousePressed() {
		Square square = getSquareFromCoordinates(mouseX, mouseY);
		if (square != null) {
			System.out.println("Pressed on square " + square);
		} else {
			System.out.println("Outside board");
		}
	}

	@Override
	public void mouseReleased() {
		Square square = getSquareFromCoordinates(mouseX, mouseY);
		if (square != null) {
			System.out.println("Released on square " + square);
		} else {
			System.out.println("Released outside board");
		}
	}

	public void setBackground() {
		background(255);
	}

	public void drawStart() {
		drawBoard(getController().getPiecePlacementData());
	}

	private void drawBoard(String[] piecePlacementData) {
		int i = 0;
		for (int rank = 8; rank > 0; rank--) {
			drawRank(rank, piecePlacementData[i]);
			i++;
		}
	}

	private void drawRank(int rank, String piecesOnRank) {
		for (int file = 1; file <= 8; file++) {
			drawSquare(rank, file);
		}
		int fileToRenderPieceOn = 0;
		for (var c : piecesOnRank.toCharArray()) {
			if (Character.isDigit(c)) {
				int emptySquares = Character.getNumericValue(c);
				fileToRenderPieceOn += emptySquares;
			} else {
				fileToRenderPieceOn++;
				drawPiece(rank, fileToRenderPieceOn, pieceFromChar(c));
			}
		}
	}

	private void drawPiece(int rank, int file, Pieces piece) {
		PImage pieceImage = getPieceImage(piece);
		if (pieceImage == null) {
			return;
		}
		int leftPieceOffset = getLeftSquareOffset(file);
		int topPieceOffset = getTopSquareOffset(rank);
		int size = getSquareSize();
		image(pieceImage, leftPieceOffset, topPieceOffset, size, size);
	}

	private PImage getPieceImage(Pieces piece) {
		return switch (piece) {
			case BlackRook -> getPieceImages()[0];
			case BlackKnight -> getPieceImages()[1];
			case BlackBishop -> getPieceImages()[2];
			case BlackQueen -> getPieceImages()[3];
			case BlackKing -> getPieceImages()[4];
			case BlackPawn -> getPieceImages()[5];
			case WhiteRook -> getPieceImages()[6];
			case WhiteKnight -> getPieceImages()[7];
			case WhiteBishop -> getPieceImages()[8];
			case WhiteQueen -> getPieceImages()[9];
			case WhiteKing -> getPieceImages()[10];
			case WhitePawn -> getPieceImages()[11];
		};
	}

	private Pieces pieceFromChar(char c) {
		return switch (c) {
			case 'p' -> Pieces.BlackPawn;
			case 'r' -> Pieces.BlackRook;
			case 'n' -> Pieces.BlackKnight;
			case 'b' -> Pieces.BlackBishop;
			case 'q' -> Pieces.BlackQueen;
			case 'k' -> Pieces.BlackKing;
			case 'P' -> Pieces.WhitePawn;
			case 'R' -> Pieces.WhiteRook;
			case 'N' -> Pieces.WhiteKnight;
			case 'B' -> Pieces.WhiteBishop;
			case 'Q' -> Pieces.WhiteQueen;
			case 'K' -> Pieces.WhiteKing;
			default -> throw new IllegalStateException("Unexpected value: " + c);
		};
	}

	private int getLeftSquareOffset(int file) {
		return getLeftBoardOffset() + (file - 1) * getSquareSize();
	}

	private int getTopSquareOffset(int rank) {
		return height - getTopBoardOffset() - rank * getSquareSize();
	}

	private void drawSquare(int rank, int file) {
		boolean bothEven = rank % 2 == 0 && file % 2 == 0;
		boolean bothOdd = rank % 2 != 0 && file % 2 != 0;
		boolean printBlack = bothEven || bothOdd;
		if (printBlack) {
			fillBlack();
		} else {
			fillWhite();
		}
		square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
	}

	private Square getSquareFromCoordinates(int x, int y) {
		int leftBoardOffset = getLeftBoardOffset();
		int topBoardOffset = getTopBoardOffset();
		boolean outsideHorizontally = x < leftBoardOffset || x > width - leftBoardOffset;
		boolean outsideVertically = y < topBoardOffset || y > height - topBoardOffset;
		if (outsideHorizontally || outsideVertically) {
			return null;
		}
		int fileOffset = x - leftBoardOffset;
		int rankOffset = (height - (topBoardOffset)) - y;
		int file = (int) Math.floor((double) fileOffset / getSquareSize()) + 1;
		int rank = (int) Math.floor((double) rankOffset / getSquareSize()) + 1;
		return getSquareFromRankFile(rank, file);
	}

	private Square getSquareFromRankFile(int rank, int file) {
		char rankChar = (char) (rank + '0');
		char fileChar = (char) (file + 'a' - 1);
		String square = new String(new char[] { fileChar, rankChar });
		return Square.valueOf(square);
	}

	public void drawPlaying() {}

	public void drawCheckmate() {}

	public void drawStalemate() {}

	public void drawGameOver() {}

	private void fillWhite() {
		fill(getWhiteColor());
	}

	private void fillBlack() {
		fill(getBlackColor());
	}
}
