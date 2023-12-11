package evolisadventure.view;

import evolisadventure.model.Pokemon;

public interface IEvolisAdventureView {
	void drawGame(Pokemon player, Pokemon[] enemies);

	void drawGG();

	void drawTitleScreen();
}
