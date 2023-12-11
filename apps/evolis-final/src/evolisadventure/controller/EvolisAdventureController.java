package evolisadventure.controller;

import evolisadventure.model.EvolisAdventureModel;
import evolisadventure.view.IEvolisAdventureView;

/**
 * A controller implementation for a GUI view. This controller creates and uses a {@link EvolisAdventureModel}.
 * It implements a state machine to keep track of the state the view should draw next.
 */
public class EvolisAdventureController implements IEvolisAdventureController {

	// Due to time reasons, we directly include the game here. An interface for the model would be better.
	private EvolisAdventureModel model;
	private IEvolisAdventureView view;
	private GameState state;

	/**
	 * Creates a new controller object with the given view and size.
	 */
	public EvolisAdventureController() {
		this.state = GameState.TITLE_SCREEN;
	}

	/**
	 * Sets the model to use within this controller.
	 * @param model the model to use
	 */
	public void setModel(EvolisAdventureModel model) {
		this.model = model;
	}

	/**
	 * Sets the view to use within this controller.
	 * @param view the view to use
	 */
	public void setView(IEvolisAdventureView view) {
		this.view = view;
	}

	/**
	 * Calls the draw methods of the view, depending on the current game state.
	 * During the game, this method also updates the position of all enemies.
	 */
	public void nextFrame() {
		switch (state) {
			case TITLE_SCREEN -> {
				view.drawTitleScreen();
			}
			case GAME -> {
				model.moveEnemies();
				view.drawGame(this.model.player, this.model.enemies);

				if (model.enemiesRemaining() <= 0) {
					state = GameState.GAME_OVER;
				}
			}
			case GAME_OVER -> {
				view.drawGG();
			}
		}
	}

	/**
	 * When called while the user is in the start-screen, the game will be started.
	 * While inside the game, the function updates the user's position.
	 * @param x  The new x-coordinate of Evoli
	 * @param y  The new y-coordinate of Evoli
	 */
	public void userInput(int x, int y) {
		switch (state) {
			case TITLE_SCREEN -> {
				state = GameState.GAME;
			}
			case GAME -> {
				model.movePlayer(
					x - model.player.size / 2,
					y - model.player.size / 2
				);
			}
		}
	}
}
