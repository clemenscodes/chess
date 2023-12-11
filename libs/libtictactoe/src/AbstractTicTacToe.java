import controller.TicTacToeController;
import model.TicTacToeModel;
import processing.core.PApplet;
import view.TicTacToeView;

public abstract class AbstractTicTacToe extends TicTacToeView {

	private static final int DEFAULT_SIZE = 900;
	private static final boolean DEFAULT_MODE = false;

	public AbstractTicTacToe() {
		super(DEFAULT_SIZE, DEFAULT_MODE);
	}

	public AbstractTicTacToe(int size, boolean darkMode) {
		super(size, darkMode);
	}

	public void setup() {
		var model = new TicTacToeModel();
		var controller = new TicTacToeController();
		var view = new TicTacToeView(this.size, this.darkMode);

		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);

		PApplet.runSketch(new String[] { "TicTacToeView" }, view);
	}
}
