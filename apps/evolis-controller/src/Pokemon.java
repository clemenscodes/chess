class Pokemon {

	// Due to limited time in the course, we use these variables directly. This however, is a bad coding style.
	// Please make sure to always set your variables private and use getter and setter to use them from the outside.
	String name;
	String picture;
	int hp;
	int attack;
	int x, y, size;

	Pokemon(String name, String picture, int hp, int attack) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.picture = picture;
		this.size = 100;
	}

	void move(int x, int y) {
		if (hp > 0) {
			this.x = x;
			this.y = y;
		}
	}

	void attack(Pokemon other) {
		other.hp = other.hp - attack;
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
				attack,
				other.hp
			)
		);

		if (other.hp > 0) {
			return !other.fight(this);
		} else {
			System.out.println(other.toString() + " died");
			return true;
		}
	}

	@Override
	public String toString() {
		return String.format("%s at (%d, %d) has %d HP", name, x, y, hp);
	}
}
