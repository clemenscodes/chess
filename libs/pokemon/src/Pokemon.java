/**
 * Pokemon class with the data and fighting functions that belong to pokemon
 * objects.
 */
public class Pokemon {

	String name;
	String picture;
	int hp;
	int attack;
	int x, y, size;
	int id;
	private static int numPokemons = 0;

	int xDirection, yDirection;

	Pokemon(String name, String picture, int hp, int attack) {
		this.name = name;
		this.picture = picture;
		this.hp = hp;
		this.attack = attack;
		this.size = 100;
		this.id = Pokemon.numPokemons++;
	}

	void setDirection(int x, int y) {
		this.xDirection = x;
		this.yDirection = y;
	}

	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
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
		System.out.printf(
			"[ID: %d] %s attacked %s: -%d (remaining HP: %d)%n",
			id,
			name,
			other.name,
			attack,
			other.hp
		);

		if (other.hp > 0) {
			return !other.fight(this);
		} else {
			System.out.println(other + " died");
			return true;
		}
	}

	@Override
	public String toString() {
		return String.format(
			"[ID: %d] %s at (%d, %d) has %d HP",
			id,
			name,
			x,
			y,
			hp
		);
	}
}
