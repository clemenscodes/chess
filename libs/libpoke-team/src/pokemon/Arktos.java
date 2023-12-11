package pokemon;

public class Arktos extends Pokemon implements FlyingType, IceType {

	public Arktos(int hp, int attack) {
		super("Arktos", hp, attack);
	}

	@Override
	public boolean isWeakAgainst(Pokemon other) {
		return IceType.super.isWeakAgainst(other);
	}

	@Override
	public boolean isStrongAgainst(Pokemon other) {
		return (
			IceType.super.isStrongAgainst(other) ||
			FlyingType.super.isStrongAgainst(other)
		);
	}
}
