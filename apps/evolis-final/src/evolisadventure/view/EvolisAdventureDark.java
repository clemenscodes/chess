package evolisadventure.view;

/**
 * A processing view with a dark color theme.
 */
public class EvolisAdventureDark extends ProcessingView {

	/**
	 * Creates a new dark view. Background is black and text is white.
	 * @param width width of the game
	 * @param height height of the game
	 */
	public EvolisAdventureDark(int width, int height) {
		super(width, height);
		textColor = 255;
		backgroundColor = 0;
	}
}
