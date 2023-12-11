package evolisadventure.view;

import evolisadventure.controller.IEvolisAdventureController;
import evolisadventure.model.Pokemon;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Abstract view class that contains all methods and variables that are shared by {@link EvolisAdventureDark} and
 * {@link EvolisAdventureLight}. Use one of these view-classes to start the game.
 */
public abstract class ProcessingView
	extends PApplet
	implements IEvolisAdventureView {

	protected int backgroundColor;
	protected int textColor;
	private IEvolisAdventureController controller;
	private HashMap<Pokemon, PImage> pokemonImages = new HashMap<>();
	private PImage titleScreen;

	/**
	 * Creates a new ProcessingView with the given game dimensions.
	 * @param width width of the game
	 * @param height height of the game
	 */
	public ProcessingView(int width, int height) {
		setSize(width, height);
	}

	/**
	 * Sets the controller to used for the MVC concept.
	 * @param controller the controller to call from the view
	 */
	public void setController(IEvolisAdventureController controller) {
		this.controller = controller;
	}

	/**
	 * Initial loading of all images (automatically called by processing).
	 */
	public void setup() {
		titleScreen = loadImage("images/TitleScreen.png");
	}

	/**
	 * Draw operation that needs to be forwarded to the controller.
	 * This method is automatically called by Processing (~60FPS).
	 */
	public void draw() {
		controller.nextFrame();
	}

	/**
	 * Mouse input of the user that is forwarded to the controller.
	 */
	public void mouseDragged() {
		controller.userInput(mouseX, mouseY);
	}

	/**
	 * Draws the player and the enemy to allow the user to see the game state and play.
	 * @param player the pokemon of the player to display (read-only)
	 * @param enemies the enemy pokemons to display (read-only)
	 */
	public void drawGame(Pokemon player, Pokemon[] enemies) {
		background(backgroundColor);

		for (int i = 0; i < enemies.length; i++) {
			drawPokemon(enemies[i]);
		}

		noStroke();
		fill(color(255, 215, 0));
		circle(
			player.x + player.size / 2,
			player.y + player.size / 2,
			player.size
		);
		drawPokemon(player);
	}

	private void drawPokemon(Pokemon p) {
		if (p.isAlive()) {
			// If the pokemon image was not used before, load it from the file to the hashmap
			if (!pokemonImages.containsKey(p)) {
				pokemonImages.put(p, loadImage(p.getPicture()));
			}
			image(pokemonImages.get(p), p.x, p.y, p.size, p.size);
		} else {
			strokeWeight(3);
			stroke(color(255, 0, 0));
			line(
				p.x + p.size / 2,
				p.y + p.size / 2 - 10,
				p.x + p.size / 2,
				p.y + p.size / 2 + 10
			);
			line(
				p.x + p.size / 2 - 5,
				p.y + p.size / 2 - 5,
				p.x + p.size / 2 + 5,
				p.y + p.size / 2 - 5
			);
		}
	}

	/**
	 * Draws "GG YOU WON!" text.
	 */
	public void drawGG() {
		background(backgroundColor);
		fill(textColor);
		textSize(64);
		text("GG YOU WON!", 350, 500);
	}

	/**
	 * Draws the title image for the game.
	 */
	@Override
	public void drawTitleScreen() {
		background(backgroundColor);
		image(titleScreen, 0, 0, 1000, 1000);
	}
}
