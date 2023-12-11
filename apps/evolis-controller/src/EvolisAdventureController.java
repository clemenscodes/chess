class EvolisAdventureController implements IEvolisAdventureController {

	private EvolisAdventureModel model;
	private IEvolisAdventureView view;

	public EvolisAdventureController() {}

	public void setModel(EvolisAdventureModel model) {
		this.model = model;
	}

	public void setView(IEvolisAdventureView view) {
		this.view = view;
	}

	public void nextFrame() {
		if (model.enemiesRemaining() > 0) {
			model.moveEnemies();
			view.drawGame(model.player, model.enemies);
		} else {
			view.drawGG();
		}
	}

	public void userInput(int x, int y) {
		model.movePlayer(x - model.player.size / 2, y - model.player.size / 2);
	}
}
