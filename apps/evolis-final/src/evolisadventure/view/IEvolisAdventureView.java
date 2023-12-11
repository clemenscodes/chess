package evolisadventure.view;

import evolisadventure.model.Pokemon;

/**
 * This interface contains all necessary classes of a view to be usable by the controller.
 * Please note that your view class can contain additional methods, which will not accessible by the controller.
 */
public interface IEvolisAdventureView {
	/**
	 * Show the current game state of Evolis Adventure. This display should display the given player and all enemies.
	 * @param player  The pokemon of the player. In Evolis Adventure this is always an Evoli.
	 * @param enemies An array with all enemy pokemon to draw. This array contains dead and alive pokemon.
	 */
	void drawGame(Pokemon player, Pokemon[] enemies);

	/**
	 * Show the game over screen of Evolis Adventure.
	 */
	void drawGG();

	/**
	 * Show the title screen of Evolis Adventure.
	 */
	void drawTitleScreen();
}
