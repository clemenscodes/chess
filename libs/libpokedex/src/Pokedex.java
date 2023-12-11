import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import pokemon.Nameable;

public class Pokedex<T extends Nameable> {

	private final Map<String, T> pokemon;

	public Map<String, T> getPokemon() {
		return pokemon;
	}

	public Pokedex() {
		pokemon = new HashMap<>();
	}

	void add(T nameable) {
		String name = nameable.getName();
		getPokemon().putIfAbsent(name, nameable);
	}

	void swap(String name, Pokedex<T> other, String otherName) {
		Map<String, T> owned = getPokemon();
		Map<String, T> foreign = other.getPokemon();
		T ownedPokemon = owned.get(name);
		T foreignPokemon = foreign.get(otherName);

		Set<T> foreignUniquePokemon = getUniqueObjectsOf(other);
		Set<T> ownedUniquePokemon = other.getUniqueObjectsOf(this);

		if (!foreignUniquePokemon.contains(foreignPokemon)) {
			System.out.printf(
				"Swap failed, initiator already owns %s\n",
				otherName
			);
			return;
		}

		if (!ownedUniquePokemon.contains(ownedPokemon)) {
			System.out.printf("Swap failed, receiver already owns %s\n", name);
			return;
		}

		add(foreignPokemon);
		foreign.remove(otherName);
		other.add(ownedPokemon);
		owned.remove(name);

		System.out.println("Swap succeeded");
	}

	Set<T> getUniqueObjectsOf(Pokedex<T> other) {
		Map<String, T> owned = getPokemon();
		Set<T> uniqueObjects = new HashSet<>();
		other
			.getPokemon()
			.forEach((key, value) -> {
				if (!owned.containsKey(key)) {
					uniqueObjects.add(value);
				}
			});
		return uniqueObjects;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("Pokemon: {");
		getPokemon()
			.forEach((key, value) -> {
				output
					.append("\n\tName: ")
					.append(key)
					.append(", Details: ")
					.append(value);
			});
		output.append("\n}");
		return output.toString();
	}
}
