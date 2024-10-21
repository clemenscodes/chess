/**
 * The {@link controller} package contains the implementation of the controller
 * component in the Chess MVC (Model-View-Controller) architecture.
 * The {@link controller.ChessController} class manages the game logic, user
 * interactions, and communication between the model and view components.
 *
 * <p>
 * <strong>Classes:</strong>
 * </p>
 * The package includes the following class:
 *
 * <p>
 * - {@link controller.ChessController}: The main controller class responsible
 * for coordinating interactions between the model and view.
 * </p>
 *
 * <p>
 * <strong>Package Dependencies:</strong>
 * </p>
 * This package relies on classes from other packages, including:
 * <p>
 * - {@link api}: Provides interfaces for chess game logic and actions.
 * </p>
 * <p>
 * - {@link model}: Implements the chess game model based on the {@link api}.
 * </p>
 * <p>
 * - {@link view}: Implements the graphical user interface (GUI) for the chess
 * game.
 * </p>
 *
 * <p>
 * <strong>Usage:</strong>
 * </p>
 * Developers can use the {@link controller.ChessController} class to manage the
 * behavior of the chess game,
 * handle user input, and update the model and view accordingly. The controller
 * plays a central role
 * in coordinating the flow of the game based on user interactions.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 * An instance of the {@link controller.ChessController} class is typically
 * created in the main method of the chess game application,
 * and it is associated with instances of the model and view. The controller is
 * then used to set up the initial state,
 * handle user interactions, and update the model and view as the game
 * progresses.
 *
 * <pre>{@code
 * public static void main(String[] args) {
 *     IChessModel model = new ChessModel();
 *     IChessView view = new ChessView();
 *     IChessController controller = new ChessController();
 *     controller.setModel(model);
 *     controller.setView(view);
 *     // Additional setup and launching the game...
 * }
 * }</pre>
 */
package controller;
