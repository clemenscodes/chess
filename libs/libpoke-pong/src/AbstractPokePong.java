import controller.PokePongController;
import model.PokePongModel;
import processing.core.PApplet;
import view.PokePongView;

public class AbstractPokePong extends PokePongView {

	private static final int DEFAULT_WIDTH = 900;
	private static final int DEFAULT_HEIGHT = 600;

	public AbstractPokePong() {
		super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public AbstractPokePong(int width, int height) {
		super(width, height);
	}

	public void setup() {
		var model = new PokePongModel();
		var controller = new PokePongController();
		var view = new PokePongView(this.width, this.height);

		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);

		PApplet.runSketch(new String[] { "PokePongView" }, view);
	}
}
