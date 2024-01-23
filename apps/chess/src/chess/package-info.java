/**
 * The {@link chess} package contains the main entry point for a chess game application,
 * utilizing the {@link api}, {@link model}, {@link view} and {@link controller} libraries to create a fully functioning chess game.
 * The application initializes the {@link model}, {@link view} and {@link controller} components, establishing communication
 * between them, and launches the game using the Processing and controlP5 libraries.
 *
 * <p><strong>Classes:</strong></p>
 * The package includes the following classes:
 * <p>- {@link chess.Chess}: The main class serving as the entry point for the chess game application.</p>
 *
 * <p><strong>Usage:</strong></p>
 * Developers can utilize the classes in this package to bootstrap a chess game with a graphical user interface.
 * The {@link chess.Chess} class provides a convenient starting point to launch the game window and set up the necessary components.
 *
 * <p><strong>Dependencies:</strong></p>
 * This package relies on the following components:
 * <p>- {@link api}: Provides interfaces for chess game logic and actions.</p>
 * <p>- {@link model}: Implements the chess game model based on the {@link api}.</p>
 * <p>- {@link view}: Implements the graphical user interface (GUI) for the chess game.</p>
 * <p>- {@link controller}: Implements the game controller to manage interactions between the model and view.</p>
 *
 * <p><strong>Example:</strong></p>
 * To launch a chess game, developers can create an instance of the {@link chess.Chess} class and call the main method.
 * The dimensions and title of the game window, as well as the instances of the {@link model}, {@link view} and {@link controller}, can be customized
 * based on the specific requirements of the chess game implementation.
 *
 * <pre>{@code
 * public static void main(String[] args) {
 *     final int width = 1600;
 *     final int height = 900;
 *     final String title = "Chess";
 *     IChessModel model = new ChessModel();
 *     IChessView view = new ChessView(width, height, title);
 *     IChessController controller = new ChessController();
 *     controller.setModel(model);
 *     controller.setView(view);
 *     view.setController(controller);
 *     PApplet.runSketch(new String[] { "ChessView" }, (PApplet) view);
 * }
 * }</pre>
 */
package chess;
