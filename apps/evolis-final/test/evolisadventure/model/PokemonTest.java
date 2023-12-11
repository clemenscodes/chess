package evolisadventure.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Just some examples. Not complete. :-)
 */
class PokemonTest {

	@Test
	void ShouldStartAtPosition00() {
		var p = new Pokemon("Bisasam", "", 1, 30);
		assertEquals(0, p.x);
		assertEquals(0, p.y);
	}

	@Test
	void isAlive_ShouldReturnTrue_WhenHPGreaterZero() {
		var p = new Pokemon("Bisasam", "", 123, 30);
		assertTrue(p.isAlive());
	}

	@Test
	void isAlive_ShouldReturnFalse_WhenHPEqualZero() {
		var p = new Pokemon("Bisasam", "", 0, 30);
		assertFalse(p.isAlive());
	}

	@Test
	void getHP_ShouldReturnInitialHP() {
		var p = new Pokemon("Glurak", "", 42, 1);
		assertEquals(42, p.getHP());
	}

	@Test
	void attack_ShouldNotChangeAttackersHP() {
		var p1 = new Pokemon("Bisasam", "", 123, 30);
		var p2 = new Pokemon("Glumanda", "", 50, 30);
		p1.attack(p2);
		assertEquals(123, p1.getHP());
	}

	@Test
	void attack_ShouldPermanentlyReduceHP() {
		var p1 = new Pokemon("Bisasam", "", 123, 30);
		var p2 = new Pokemon("Glumanda", "", 50, 30);
		p1.attack(p2);
		assertEquals(50 - 30, p2.getHP());
	}

	@Test
	void fight_ShouldKillOnePokemon() {
		var p1 = new Pokemon("Bisasam", "", 123, 30);
		var p2 = new Pokemon("Glumanda", "", 50, 30);
		p1.fight(p2);
		assertFalse(p1.isAlive() && p2.isAlive());
	}

	@Test
	void fight_ShouldReturnTrue_WhenEqualPokemonFight() {
		var p1 = new Pokemon("Bisasam 1", "", 123, 30);
		var p2 = new Pokemon("Bisasam 2", "", 123, 30);
		assertTrue(p1.fight(p2));
		assertTrue(p1.isAlive());
		assertFalse(p2.isAlive());
	}

	@Test
	void move_ShouldMove_WhenPokemonIsAlive() {
		var p = new Pokemon("Bisasam", "", 1, 30);
		p.move(10, 11);
		assertEquals(10, p.x);
		assertEquals(11, p.y);
	}

	@Test
	void move_ShouldNotMove_WhenPokemonIsDead() {
		var p = new Pokemon("Bisasam", "", 0, 30);
		p.move(10, 11);
		assertNotEquals(10, p.x);
		assertNotEquals(11, p.y);
	}
}
