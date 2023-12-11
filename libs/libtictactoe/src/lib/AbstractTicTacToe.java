package lib;

import lib.controller.TicTacToeController;
import lib.model.TicTacToeModel;
import lib.view.TicTacToeView;
import processing.core.PApplet;

/**
 * The AbstractTicTacToe class serves as a base class for implementing the Tic-Tac-Toe game in a Processing environment.
 * It extends the TicTacToeView class and provides default settings for the game size and mode.
 *
 * @author Clemens Horn
 * @version 1.0
 */
public abstract class AbstractTicTacToe extends TicTacToeView {

	/** Default size of the game window */
	private static final int DEFAULT_SIZE = 900;

	/** Default mode of the game window (light mode if false, dark mode if true) */
	private static final boolean DEFAULT_MODE = false;

	/**
	 * Default constructor for AbstractTicTacToe.
	 * Initializes the game with default size and mode.
	 */
	public AbstractTicTacToe() {
		super(DEFAULT_SIZE, DEFAULT_MODE);
	}

	/**
	 * Parameterized constructor for AbstractTicTacToe.
	 * Initializes the game with the specified size and mode.
	 *
	 * @param size The size of the game window.
	 * @param darkMode The mode of the game window (light mode if false, dark mode if true).
	 */
	public AbstractTicTacToe(int size, boolean darkMode) {
		super(size, darkMode);
	}

	/**
	 * Setup method for the game.
	 * Initializes the model, controller, and view components of the Tic-Tac-Toe game.
	 */
	public void setup() {
		// Create instances of the model, controller, and view
		var model = new TicTacToeModel();
		var controller = new TicTacToeController();
		var view = new TicTacToeView(this.size, this.darkMode);

		// Set the model for the controller, view for the controller, and controller for the view
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);

		// Run the Processing sketch with the TicTacToeView
		PApplet.runSketch(new String[] { "TicTacToeView" }, view);
	}
}
