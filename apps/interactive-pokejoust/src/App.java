import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

public class App extends PApplet {

	Pokemon player;
	Pokemon winner;
	private ArrayList<Pokemon> pokemon;
	private final HashMap<Pokemon, PImage> pokemonImages = new HashMap<>();

	boolean gameOver = false;

	public static void main(String[] args) {
		PApplet.main(App.class);
	}

	public void settings() {
		size(1280, 720);
	}

	public void setup() {
		frameRate(60);
		this.player = new Pokemon("Lugia", "images/249.png", 10, 5);
		this.pokemon = new ArrayList<>();
		this.pokemon.add(this.player);
		for (int i = 0; i < 10; i++) {
			Ibitak ibitak = new Ibitak();
			this.pokemon.add(ibitak);
		}
		for (Pokemon p : this.pokemon) {
			int halfSizeOfPokemon = p.size / 2;
			int xDirection = (int) random(-5, 5);
			if (xDirection == 0) {
				xDirection++;
			}
			int yDirection = (int) random(-5, 5);
			if (yDirection == 0) {
				yDirection++;
			}
			int x = (int) random(halfSizeOfPokemon, width - halfSizeOfPokemon);
			int y = (int) random(halfSizeOfPokemon, height - halfSizeOfPokemon);
			p.setDirection(xDirection, yDirection);
			p.setPosition(x, y);
			this.pokemonImages.put(p, loadImage(p.picture));
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (keyCode == UP) {
			if (this.player.y >= 10 + this.player.size / 2) {
				this.player.y -= 10;
			}
		}
		if (keyCode == DOWN) {
			if (this.player.y <= height - 10 - this.player.size / 2) {
				this.player.y += 10;
			}
		}
		if (keyCode == 82) {
			gameOver = false;
			this.setup();
		}
	}

	public void draw() {
		if (this.pokemon.size() == 1) {
			gameOver = true;
		}
		if (gameOver) {
			background(0);
			fill(255);
			textSize(32);
			text(winner.name + " is the winner!", 100, 200);
			fill(255);
			textSize(64);
			text("Game over", 350, 500);
		} else {
			background(255);
			for (Pokemon p : this.pokemon) {
				this.movePokemon(p);
			}
			checkCollisions();
		}
	}

	void checkCollisions() {
		for (int i = 0; i < this.pokemon.size(); i++) {
			Pokemon p1 = this.pokemon.get(i);
			for (int j = i + 1; j < this.pokemon.size(); j++) {
				Pokemon p2 = this.pokemon.get(j);
				if (checkOverlap(p1, p2)) {
					if (p1.y > p2.y + p1.size * 0.4) {
						System.out.printf(
							"[ID: %d] %s killed %s [ID: %d]\n",
							p2.id,
							p2.name,
							p1.name,
							p1.id
						);
						this.pokemon.remove(p1);
						if (this.pokemon.size() == 1) {
							this.winner = p2;
							gameOver = true;
						}
					} else if (p2.y > p1.y + p2.size * 0.4) {
						System.out.printf(
							"[ID: %d] %s killed %s [ID: %d]\n",
							p1.id,
							p1.name,
							p2.name,
							p2.id
						);
						this.pokemon.remove(p2);
						if (this.pokemon.size() == 1) {
							this.winner = p1;
							gameOver = true;
						}
					} else {
						p1.xDirection *= -1;
						p1.xDirection++;
						p1.yDirection *= -1;
						p2.xDirection *= -1;
						p2.xDirection++;
						p2.yDirection *= -1;
					}
				}
			}
		}
	}

	boolean checkOverlap(Pokemon p1, Pokemon p2) {
		float halfSizeP1 = p1.size / 2.0f;
		float halfSizeP2 = p2.size / 2.0f;
		float distance = dist(p1.x, p1.y, p2.x, p2.y);
		return distance < (halfSizeP1 + halfSizeP2 - 25);
	}

	void movePokemon(Pokemon p) {
		PImage img = this.pokemonImages.get(p);
		pushMatrix();
		translate(p.x, p.y);
		float halfSizeOfPokemon = p.size / 2.0f;
		if (p.xDirection >= 0) {
			scale(-1, 1);
		}
		image(img, -halfSizeOfPokemon, -halfSizeOfPokemon, p.size, p.size);
		p.move(p.x + p.xDirection, p.y + p.yDirection);
		this.checkBoundary(p);
		popMatrix();
	}

	void checkBoundary(Pokemon p) {
		int halfSizeOfPokemon = p.size / 2;
		if (p.x < halfSizeOfPokemon || p.x > (width - halfSizeOfPokemon)) {
			p.xDirection *= -1;
		}
		if (p.y < halfSizeOfPokemon || p.y > (height - halfSizeOfPokemon)) {
			p.yDirection *= -1;
		}
	}
}
