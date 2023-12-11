package teams;

public interface Fightable<T> {
	boolean fight(T other);
	boolean isAlive();
}
