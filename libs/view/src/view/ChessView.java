package view;

import api.controller.IChessController;
import api.view.IChessView;
import controlP5.Button;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class ChessView extends PApplet implements IChessView {

    private IChessController controller;
    private ControlP5 cp5;
    private Button resignButton;
    private PImage[] pieceImages;
    private PFont font;
    private String title;
    private int leftBoardOffset;
    private int topBoardOffset;
    private int squareSize;
    private final int whiteColor = color(237, 214, 176);
    private final int blackColor = color(181, 136, 99);

    public ChessView(String title) {
        setTitle(title);
    }

    @Override
    public void settings() {
        fullScreen();
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
        initResignButton();
        loadPieceImages();
        getController().startGame();
    }

    @Override
    public void draw() {
        getController().nextFrame();
    }

    @Override
    public void mouseDragged() {
        getController().handleUserInput(mouseX, mouseY);
    }

    @Override
    public void mousePressed() {
        getController().handleUserInput(mouseX, mouseY);
    }

    @Override
    public void mouseReleased() {
        getController().handleUserInput(mouseX, mouseY);
    }

    public void setController(IChessController controller) {
        this.controller = controller;
    }

    public void setBackground() {
        background(255);
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
        drawBoard(getController().getPiecePlacementData());
    }

    public void drawPlaying() {
    }

    public void drawCheckmate() {
    }

    public void drawStalemate() {
    }

    public void drawGameOver() {
    }

    private int getLeftSquareOffset(int file) {
        return getLeftBoardOffset() + (file - 1) * getSquareSize();
    }

    private int getTopSquareOffset(int rank) {
        return getHeight() - getTopBoardOffset() - rank * getSquareSize();
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
                drawPiece(rank, fileToRenderPieceOn, pieceImageFromChar(c));
            }
        }
    }

    private void drawSquare(int rank, int file) {
        boolean bothEven = rank % 2 == 0 && file % 2 == 0;
        boolean bothOdd = rank % 2 != 0 && file % 2 != 0;
        boolean printBlack = bothEven || bothOdd;
        if (printBlack) {
            fill(getBlackColor());
        } else {
            fill(getWhiteColor());
        }
        square(getLeftSquareOffset(file), getTopSquareOffset(rank), getSquareSize());
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
            default ->
                throw new IllegalStateException("Unexpected value: " + c);
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
        int buttonWidth = getWidth() / 10;
        int buttonHeight = getHeight() / 20;
        float leftOffset = getWidth() - (getLeftBoardOffset() / 2.0f) - buttonWidth / 2.0f;
        float topOffset = getHeight() - 2 * getTopBoardOffset() + buttonHeight / 2.0f;
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
            case 0 -> "images/black/pawn.png";
            case 1 -> "images/black/rook.png";
            case 2 -> "images/black/knight.png";
            case 3 -> "images/black/bishop.png";
            case 4 -> "images/black/queen.png";
            case 5 -> "images/black/king.png";
            case 6 -> "images/white/pawn.png";
            case 7 -> "images/white/rook.png";
            case 8 -> "images/white/knight.png";
            case 9 -> "images/white/bishop.png";
            case 10 -> "images/white/queen.png";
            case 11 -> "images/white/king.png";
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
