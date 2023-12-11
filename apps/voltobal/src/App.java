import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

	private Pokemon[] pokemon;
	private final HashMap<Pokemon, PImage> pokemonImages = new HashMap<>();
	int degrees;

	public void setDegrees() {
		this.degrees = 0;
	}

	public static void main(String[] args) {
		PApplet.main(App.class);
	}

	public void settings() {
		this.setDegrees();
		size(1280, 720);
	}

	public void setup() {
		frameRate(60);
		Pokemon voltobal = new Pokemon("Voltobal", "images/100.png", 10, 5);
		this.pokemon = new Pokemon[] { voltobal };
		for (Pokemon p : this.pokemon) {
			int halfSizeOfPokemon = p.size / 2;
			int xDirection = (int) random(-10, 10);
			int yDirection = (int) random(-10, 10);
			int x = (int) random(halfSizeOfPokemon, width - halfSizeOfPokemon);
			int y = (int) random(halfSizeOfPokemon, height - halfSizeOfPokemon);
			p.setDirection(xDirection, yDirection);
			p.setPosition(x, y);
			this.pokemonImages.put(p, loadImage(p.picture));
		}
	}

	public void draw() {
		background(255);
		for (Pokemon p : this.pokemon) {
			this.rollPokemon(p);
			this.movePokemon(p);
		}
	}

	void rollPokemon(Pokemon p) {
		translate(p.x, p.y);
		rotate(radians(this.degrees));
		this.degrees += 6;
	}

	void movePokemon(Pokemon p) {
		PImage img = this.pokemonImages.get(p);
		float halfSizeOfPokemon = p.size / 2.0f;
		image(img, -halfSizeOfPokemon, -halfSizeOfPokemon, p.size, p.size);
		p.move(p.x + p.xDirection, p.y + p.yDirection);
		this.checkBoundary(p);
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
