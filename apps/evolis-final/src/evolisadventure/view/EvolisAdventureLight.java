package evolisadventure.view;

/**
 * A processing view with a light color theme.
 */
public class EvolisAdventureLight extends ProcessingView {

	/**
	 * Creates a new light view. Background is white and text is black.
	 * @param width width of the game
	 * @param height height of the game
	 */
	public EvolisAdventureLight(int width, int height) {
		super(width, height);
		textColor = 0;
		backgroundColor = 255;
	}
}
