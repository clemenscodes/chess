package pokedinner.view;

import pokedinner.controller.IController;
import pokedinner.model.PhilosopherStatus;
import processing.core.PApplet;
import processing.core.PImage;

record Coordinate(int x, int y) {}

public class PhilosophersView extends PApplet implements IView {

	private IController controller;
	private PImage[] pokemons;
	/**
	 * Center coordinates of the five pokemon philosophers.
	 * Counter-clockwise to ensure it is the left fork first.
	 */
	private Coordinate[] coordinates = {
		new Coordinate(0, -375),
		new Coordinate(-350, -100),
		new Coordinate(-250, 300),
		new Coordinate(250, 300),
		new Coordinate(350, -100),
	};
	private PImage fork;

	public PhilosophersView() {}

	/**
	 * Set the controller. Should only be set at application start.
	 *
	 * @param controller controller to use
	 */
	public void setController(IController controller) {
		this.controller = controller;
	}

	@Override
	public void setup() {
		pokemons = new PImage[5];
		pokemons[0] = loadImage("images/062.png");
		pokemons[1] = loadImage("images/143.png");
		pokemons[2] = loadImage("images/126.png");
		pokemons[3] = loadImage("images/063.png");
		pokemons[4] = loadImage("images/149.png");
		fork = loadImage("images/fork.png");
	}

	@Override
	public void settings() {
		setSize(1000, 1000);
	}

	@Override
	public void draw() {
		if (controller != null) controller.nextFrame();
	}

	@Override
	public void keyPressed() {
		if (controller != null) controller.userInput(key);
	}

	@Override
	public void drawGameState(
		boolean[] forks,
		PhilosopherStatus[] philosophers
	) {
		background(255);
		translate(width / 2, height / 2);
		imageMode(CENTER);

		// Draw table
		noStroke();
		fill(color(222, 184, 135));
		circle(0, 0, 500);

		// Draw philosophers
		for (int i = 0; i < philosophers.length; i++) {
			drawPhilosopher(pokemons[i], philosophers[i], coordinates[i]);
		}

		// Draw forks
		if (forks[0]) {
			image(fork, 110, -180, 50, 50);
		}
		if (forks[1]) {
			image(fork, -110, -180, 50, 50);
		}
		if (forks[2]) {
			image(fork, -190, 80, 50, 50);
		}
		if (forks[3]) {
			image(fork, 0, 220, 50, 50);
		}
		if (forks[4]) {
			image(fork, 190, 80, 50, 50);
		}
	}

	private void drawPhilosopher(
		PImage img,
		PhilosopherStatus state,
		Coordinate c
	) {
		final int size = 250;
		switch (state) {
			case HUNGRY -> {
				noStroke();
				fill(color(255, 0, 0));
				circle(c.x(), c.y(), 220);
			}
			case HOLDS_FORK -> {
				noStroke();
				fill(color(255, 255, 0));
				circle(c.x(), c.y(), 220);
			}
			case EATING -> {
				noStroke();
				fill(color(0, 255, 0));
				circle(c.x(), c.y(), 220);
			}
		}
		image(img, c.x(), c.y(), size, size);
	}
}
