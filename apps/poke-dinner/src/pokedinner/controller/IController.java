package pokedinner.controller;

public interface IController {
	/**
	 * Handles a redraw request of the view.
	 * Decides what the view should draw next.
	 */
	void nextFrame();

	/**
	 * Handles keyboard input of the user.
	 *
	 * @param key ASCII keycode of the pressed key
	 */
	void userInput(int key);
}
