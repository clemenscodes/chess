package evolisadventure.view;

import evolisadventure.controller.IEvolisAdventureController;
import evolisadventure.model.Pokemon;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class EvolisAdventureLight
	extends PApplet
	implements IEvolisAdventureView {

	private IEvolisAdventureController controller;
	private HashMap<Pokemon, PImage> pokemonImages = new HashMap<>();
	private PImage titleScreen;

	public EvolisAdventureLight(int width, int height) {
		setSize(width, height);
	}

	public void setController(IEvolisAdventureController controller) {
		this.controller = controller;
	}

	public void setup() {
		titleScreen = loadImage("images/TitleScreen.png");
	}

	public void draw() {
		controller.nextFrame();
	}

	public void mouseDragged() {
		controller.userInput(mouseX, mouseY);
	}

	public void drawGame(Pokemon player, Pokemon[] enemies) {
		background(255);

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

	public void drawGG() {
		background(0);
		textSize(64);
		text("GG YOU WON!", 350, 500);
	}

	@Override
	public void drawTitleScreen() {
		background(255);
		image(titleScreen, 0, 0, 1000, 1000);
	}
}
