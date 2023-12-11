import pokedinner.controller.PhilosophersController;
import pokedinner.model.PhilosophersDinner;
import pokedinner.view.PhilosophersView;
import processing.core.PApplet;

public abstract class Main {

	/**
	 * Main method creates a model, view and controller classes.
	 * It connects them and start the processing application.
	 * <p>
	 * Note: In the last examples the view always included a concrete controller object, since it was created inside the
	 * view class. This is not necessary with the method used here. Controller and view are completely separate.
	 */
	public static void main(String[] args) {
		var model = new PhilosophersDinner(1000);
		var controller = new PhilosophersController();
		var view = new PhilosophersView();

		// Connect controller and view
		controller.setView(view);
		controller.setModel(model);
		view.setController(controller);

		// Starts the processing application
		PApplet.runSketch(new String[] { "PhilosophersView" }, view);
	}
}
