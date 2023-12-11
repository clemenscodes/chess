package pokemon;

/**
 * This is a Pokemon that implements two interfaces FireType and FlyingType.
 * Both of these interfaces have the same methods with default implementation.
 * Hence, this class would not compile without overriding the two methods.
 */
public class Glurak extends Pokemon implements FireType, FlyingType {

	public Glurak(int hp, int attack) {
		super("Glurak", hp, attack);
	}

	@Override
	public boolean isWeakAgainst(Pokemon other) {
		// Only weakness of FireType, because the FlyingType weakness (grass) is cancelled out by Fire-Type.
		return FireType.super.isWeakAgainst(other);
	}

	@Override
	public boolean isStrongAgainst(Pokemon other) {
		return (
			FireType.super.isStrongAgainst(other) ||
			FlyingType.super.isStrongAgainst(other)
		);
	}
}
