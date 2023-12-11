package evolisadventure.model;

record Stats(int maxHP, int attack, int defense, int agility) {
	Stats(int maxHP, int attack) {
		this(maxHP, attack, 0, 0);
	}
}
