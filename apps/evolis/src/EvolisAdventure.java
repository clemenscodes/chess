import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class EvolisAdventure extends PApplet {

	private Pokemon player;
	private Pokemon[] enemies;
	private final HashMap<Pokemon, PImage> pokemonImages = new HashMap<>();

	public static void main(String[] args) {
		PApplet.main(EvolisAdventure.class);
	}

	public void settings() {
		size(1000, 1000);
		displayDensity(2);
	}

	public void setup() {
		// Needs to be inside setup, because of loadImage-call
		player = new Pokemon("Evoli", "images/133.png", 999, 999);
		enemies =
			new Pokemon[] {
				new Pokemon("Bisasam", "images/001.png", 10, 5),
				new Pokemon("Glumanda", "images/004.png", 10, 5),
				new Pokemon("Schiggy", "images/007.png", 10, 5),
				new Pokemon("Pikachu", "images/025.png", 10, 5),
				new Pokemon("Paras", "images/046.png", 5, 5),
				new Pokemon("Enton", "images/054.png", 20, 3),
				new Pokemon("Flegmon", "images/079.png", 15, 6),
				new Pokemon("Gengar", "images/094.png", 25, 15),
				new Pokemon("Karpador", "images/129.png", 1, 0),
			};

		// Load images with Processings loadImage method
		// This cannot be done in Pokemon, because cannot access loadImage
		pokemonImages.put(player, loadImage(player.picture));
		for (var e : enemies) {
			pokemonImages.put(e, loadImage(e.picture));
		}

		// Set start positions
		player.move(0, 0);
		for (var e : enemies) {
			e.move(
				(int) (Math.random() * (width - e.size)),
				(int) (Math.random() * (height - e.size))
			);
		}
	}

	public void draw() {
		if (enemiesRemaining() > 0) {
			background(255);

			// Move enemies
			for (var e : enemies) {
				gameMove(
					e,
					e.x + (int) (Math.random() * 10 - 5),
					e.y + (int) (Math.random() * 10 - 5)
				);
			}

			// Fight and draw
			for (var e : enemies) {
				if (player.isFightingWith(e)) {
					player.fight(e);
				}
				drawPokemon(e);
			}
			float halfSizeOfPlayer = (float) player.size / 2;

			noStroke();
			fill(color(255, 215, 0));
			circle(
				player.x + halfSizeOfPlayer,
				player.y + halfSizeOfPlayer,
				player.size
			);
			drawPokemon(player);
		} else {
			background(0);
			textSize(64);
			text("GG YOU WON!", 350, 500);
		}
	}

	private void drawPokemon(Pokemon p) {
		float halfPokemonSize = (float) p.size / 2;
		if (p.hp > 0) {
			image(pokemonImages.get(p), p.x, p.y, p.size, p.size);
		} else {
			strokeWeight(3);
			stroke(color(255, 0, 0));
			line(
				p.x + halfPokemonSize / 2,
				p.y + halfPokemonSize / 2 - 10,
				p.x + halfPokemonSize / 2,
				p.y + halfPokemonSize / 2 + 10
			);
			line(
				p.x + halfPokemonSize / 2 - 5,
				p.y + halfPokemonSize / 2 - 5,
				p.x + halfPokemonSize / 2 + 5,
				p.y + halfPokemonSize / 2 - 5
			);
		}
	}

	public void mouseDragged() {
		gameMove(player, mouseX - player.size / 2, mouseY - player.size / 2);
	}

	private int enemiesRemaining() {
		int count = 0;
		for (var e : enemies) {
			if (e.hp > 0) count++;
		}
		return count;
	}

	private void gameMove(Pokemon p, int x, int y) {
		int xMove = Math.max(0, Math.min(width - p.size, x));
		int yMove = Math.max(0, Math.min(height - p.size, y));
		p.move(xMove, yMove);

		for (Pokemon e : enemies) {
			if (player.isFightingWith(e)) player.fight(e);
		}
	}
}
