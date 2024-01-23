package chess;

import api.IChessController;
import api.IChessModel;
import api.IChessView;
import controller.ChessController;
import model.ChessModel;
import processing.core.PApplet;
import view.ChessView;

/**
 * The {@link Chess} class serves as the main entry point for the chess game application,
 * utilizing the API, model, view, and controller libraries to bootstrap a fully functioning chess game.
 * It initializes the model, view, and controller components, establishing communication between them,
 * and launches the game using the Processing library.
 */
public final class Chess {

	/**
	 * The main method that initializes and starts the chess game.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		final int width = 1600;
		final int height = 900;
		final String title = "Chess";
		IChessModel model = new ChessModel();
		IChessView view = new ChessView(width, height, title);
		IChessController controller = new ChessController();
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);
		PApplet.runSketch(new String[] { "ChessView" }, (PApplet) view);
	}
}
