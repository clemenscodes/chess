package evolisadventure.controller;

import evolisadventure.model.EvolisAdventureModel;
import evolisadventure.view.IEvolisAdventureView;

public class EvolisAdventureController implements IEvolisAdventureController {

	// Due to time reasons, we directly include the game here. An interface for the model would be better.
	private EvolisAdventureModel model;
	private IEvolisAdventureView view;
	private GameState state;

	public EvolisAdventureController() {
		this.state = GameState.TITLE_SCREEN;
	}

	public void setModel(EvolisAdventureModel model) {
		this.model = model;
	}

	public void setView(IEvolisAdventureView view) {
		this.view = view;
	}

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
