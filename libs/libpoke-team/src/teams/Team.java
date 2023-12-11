package teams;

import java.util.ArrayList;

/*
 * A team of fighters. Two teams of the same type (T) can fight against each other.
 */
public class Team<T extends Fightable<T>> {

	private ArrayList<T> members;

	public Team() {
		members = new ArrayList<>();
	}

	public void addMember(T member) {
		members.add(member);
	}

	public T getMember(int index) {
		assert (index >= 0);
		assert (index < members.size());

		return members.get(index);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("TEAM MEMBERS:");
		result.append(System.lineSeparator());
		for (var p : members) {
			result.append("- ");
			result.append(p.toString());
			result.append(System.lineSeparator());
		}
		return result.toString();
	}

	public T nextFighter() {
		for (int i = 0; i < members.size(); i++) {
			var member = members.get(i);
			if (member.isAlive()) return member;
		}
		return null;
	}

	enum FightResult {
		WIN,
		LOOSE,
	}

	public FightResult fight(Team<T> other) {
		T fighter = nextFighter();
		while (fighter != null) {
			T enemy = other.nextFighter();
			if (enemy == null) {
				return FightResult.WIN;
			}
			fighter.fight(enemy);
			fighter = nextFighter();
		}
		return FightResult.LOOSE;
	}
}
