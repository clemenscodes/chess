package pokemon;

public class Zapdos extends Pokemon implements FlyingType, ElectricType {

	public Zapdos(int hp, int attack) {
		super("Zapdos", hp, attack);
	}

	@Override
	public boolean isWeakAgainst(Pokemon other) {
		return ElectricType.super.isWeakAgainst(other);
	}

	@Override
	public boolean isStrongAgainst(Pokemon other) {
		return (
			ElectricType.super.isStrongAgainst(other) ||
			FlyingType.super.isStrongAgainst(other)
		);
	}
}
