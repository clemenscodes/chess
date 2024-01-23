/**
 * The {@link view} package contains classes related to the graphical user interface (GUI) of the chess game.
 * It includes implementations for visual representation, user interaction, and rendering of the chessboard and game components.
 *
 * <p><strong>Classes:</strong></p>
 * <p>- {@link view.ChessView}: Implements the {@link api.IChessView} interface and extends the Processing {@link processing.core.PApplet}.
 * Serves as the primary GUI class responsible for displaying the chess game and handling user interactions.</p>
 *
 * <p><strong>Package Dependencies:</strong></p>
 * The package relies on classes from other packages, including:
 * <p>- {@link api}: Provides interfaces for chess game logic and actions.</p>
 * <p>- {@link controlP5}: A library for creating GUI components in Processing.</p>
 * <p>- {@link processing.core}: Core classes for the Processing library.</p>
 *
 * <p><strong>Usage:</strong></p>
 * Developers can use the classes within this package to create and customize the graphical representation
 * of the chess game. The {@link view.ChessView} class, in particular, provides an interactive and visually appealing
 * interface for players to interact with the game.
 *
 * <p><strong>Example:</strong></p>
 * An instance of the {@link view.ChessView} class is typically created in the main method of the chess game application.
 * It is associated with instances of the model and controller, and it serves as the main GUI component for the game.
 *
 * <pre>{@code
 * public static void main(String[] args) {
 *     IChessModel model = new ChessModel();
 *     IChessController controller = new ChessController();
 *     IChessView view = new ChessView(800, 600, "Chess Game");
 *     controller.setModel(model);
 *     controller.setView(view);
 *     view.setController(controller);
 *     // Additional setup and launching the game...
 * }
 * }</pre>
 */
package view;
