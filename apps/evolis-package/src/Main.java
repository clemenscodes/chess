import evolisadventure.controller.EvolisAdventureController;
import evolisadventure.model.EvolisAdventureModel;
import evolisadventure.view.EvolisAdventureDark;
import evolisadventure.view.EvolisAdventureLight;
import processing.core.PApplet;

public final class Main {

	public static void main(String[] args) {
		final int GAME_SIZE = 1000;
		var model = new EvolisAdventureModel(GAME_SIZE, GAME_SIZE);
		var controller = new EvolisAdventureController();
		var view = new EvolisAdventureLight(GAME_SIZE, GAME_SIZE);
		// Alternative View:
		// var view = new EvolisAdventureDark(GAME_SIZE, GAME_SIZE);

		// Connect M, V and C
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);

		// Starts the processing application
		PApplet.runSketch(new String[] { "EvolisAdventureView" }, view);
	}
}
