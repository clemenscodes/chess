package pokemon;

interface PokemonType {
	boolean isWeakAgainst(Pokemon other);

	boolean isStrongAgainst(Pokemon other);
}

interface FlyingType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof GrassType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return false;
	}
}

interface FireType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof WaterType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return other instanceof GrassType;
	}
}

interface WaterType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof GrassType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return other instanceof FireType;
	}
}

interface GrassType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof FireType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return other instanceof WaterType;
	}
}

interface IceType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof WaterType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return other instanceof GrassType || other instanceof FlyingType;
	}
}

interface ElectricType extends PokemonType {
	default boolean isWeakAgainst(Pokemon other) {
		return other instanceof GrassType;
	}

	default boolean isStrongAgainst(Pokemon other) {
		return other instanceof FlyingType || other instanceof WaterType;
	}
}
