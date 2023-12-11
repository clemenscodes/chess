package evolisadventure.controller;

/**
 * All possible game states. This class is used to implement a state machine in the controller.
 */
public enum GameState {
	/**
	 * The state in which the user can play Evolis Adventure. Transitions to the <code>GAME_OVER</code> state.
	 */
	GAME,
	/**
	 * The end state of the game, when Evoli won the game.
	 */
	GAME_OVER,
	/**
	 * The starting state of the game. Transitions to the <code>GAME</code> state.
	 */
	TITLE_SCREEN,
}
