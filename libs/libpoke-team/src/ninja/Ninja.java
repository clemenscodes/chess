package ninja;

import teams.Fightable;

public class Ninja implements Fightable<Ninja> {

	private int hp, attack;

	public Ninja(int hp, int attack) {
		this.hp = hp;
		this.attack = attack;
	}

	@Override
	public boolean fight(Ninja other) {
		while (isAlive() && other.isAlive()) {
			other.hp = Math.max(0, other.hp - attack);

			if (other.isAlive()) hp = Math.max(0, hp - other.attack);
		}
		return isAlive();
	}

	@Override
	public boolean isAlive() {
		return hp > 0;
	}

	@Override
	public String toString() {
		return String.format("A ninja (%d HP)", hp);
	}
}
