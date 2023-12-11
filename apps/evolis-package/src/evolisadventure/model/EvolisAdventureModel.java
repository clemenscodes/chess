package evolisadventure.model;

public class EvolisAdventureModel {

	// Due to limited time in the course, we use these variables directly. This however, is a bad coding style.
	// Please make sure to always set your variables private and use getter and setter to use them from the outside.
	public Pokemon player;
	public Pokemon[] enemies;
	public int width, height;

	public EvolisAdventureModel(int width, int height) {
		this.width = width;
		this.height = height;

		// Create pokemons
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

		// Move to random position
		player.move(0, 0);
		for (var e : enemies) {
			e.move(
				(int) (Math.random() * (width - e.size)),
				(int) (Math.random() * (height - e.size))
			);
		}
	}

	public int enemiesRemaining() {
		int count = 0;
		for (Pokemon e : enemies) {
			if (e.isAlive()) count++;
		}
		return count;
	}

	public void movePlayer(int x, int y) {
		gameMove(player, x, y);
	}

	public void moveEnemies() {
		for (var e : enemies) {
			gameMove(
				e,
				e.x + (int) (Math.random() * 10 - 5),
				e.y + (int) (Math.random() * 10 - 5)
			);
		}
	}

	private void gameMove(Pokemon p, int x, int y) {
		int xMove = Math.max(0, Math.min(width - p.size, x));
		int yMove = Math.max(0, Math.min(height - p.size, y));
		p.move(xMove, yMove);

		for (Pokemon e : enemies) {
			if (player.isFightingWith(e)) player.fight(e);
		}
	}

	@Override
	public String toString() {
		return (
			"Evolis Adventure has " + enemiesRemaining() + " enemies remaining."
		);
	}
}
