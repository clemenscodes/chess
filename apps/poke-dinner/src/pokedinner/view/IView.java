package pokedinner.view;

import pokedinner.model.PhilosopherStatus;

public interface IView {
	/**
	 * Draws the current game state with the given state.
	 *
	 * @param forks        availability of the five forks (true=fork on table; false=otherwise)
	 * @param philosophers state of the five philosophers
	 */
	void drawGameState(boolean[] forks, PhilosopherStatus[] philosophers);
}
