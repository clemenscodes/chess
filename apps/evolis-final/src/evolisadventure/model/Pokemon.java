package evolisadventure.model;

/**
 * This package is part of the model and contains data and logic. It is allowed to pass this as a data container to the
 * controller and view. However, it should be read-only outside the model to avoid changes from the outside. Please note
 * that most functions are only visible inside the package and only getters were made public.
 */
public class Pokemon {

	private String name;
	private String picture;
	private Stats stats;
	private int hp;

	// Due to limited time in the course, we use these variables directly. This however, is a bad coding style.
	// Please make sure to always set your variables private and use getter and setter to use them from the outside.
	public int x, y, size;

	Pokemon(String name, String picture, int hp, int attack) {
		this.name = name;
		this.stats = new Stats(hp, attack);
		this.picture = picture;
		this.hp = hp;
		this.size = 100;
	}

	void move(int x, int y) {
		if (hp > 0) {
			this.x = x;
			this.y = y;
		}
	}

	void attack(Pokemon other) {
		other.hp = other.hp - stats.attack();
	}

	boolean isFightingWith(Pokemon other) {
		return (
			this != other &&
			hp > 0 &&
			other.hp > 0 &&
			(Math.abs(x - other.x) <= size / 2 &&
				Math.abs(y - other.y) <= size / 2)
		);
	}

	boolean fight(Pokemon other) {
		attack(other);
		System.out.println(
			String.format(
				"%s attacked %s: -%d (remaining HP: %d)",
				name,
				other.name,
				stats.attack(),
				other.hp
			)
		);

		if (other.isAlive()) {
			return !other.fight(this);
		} else {
			System.out.println(other.toString() + " died");
			return true;
		}
	}

	public boolean isAlive() {
		return hp > 0;
	}

	public String getPicture() {
		return picture;
	}

	@Override
	public String toString() {
		return String.format("%s at (%d, %d) has %d HP", name, x, y, hp);
	}

	public int getHP() {
		return hp;
	}
}
