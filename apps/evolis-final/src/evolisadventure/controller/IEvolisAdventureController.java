package evolisadventure.controller;

/**
 * The interface of a controller from the perspective of a view object.
 * All elements that are not part of this interface are invisible for the view.
 */
public interface IEvolisAdventureController {
	/**
	 * This method should be called whenever the controller should decide what the view should display.
	 * The controller will call one of the drawX()-Methods from the view.
	 */
	void nextFrame();

	/**
	 * Method that should be called whenever the user clicks on the game window.
	 * @param x  The selected  x-coordinate
	 * @param y  The selected y-coordinate
	 */
	void userInput(int x, int y);
}
