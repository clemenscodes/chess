package pokedinner.controller;

import pokedinner.model.IPhilosophersDinner;
import pokedinner.model.PhilosophersDinner;
import pokedinner.view.IView;

public class PhilosophersController implements IController {

	private IView view;
	private IPhilosophersDinner model;

	/**
	 * Set the used view. Should only be set at application start.
	 *
	 * @param view view to use
	 */
	public void setView(IView view) {
		this.view = view;
	}

	/**
	 * Set the used model. Should only be set at application start.
	 *
	 * @param model model to use
	 */
	public void setModel(IPhilosophersDinner model) {
		this.model = model;
	}

	@Override
	public void nextFrame() {
		if (this.view != null) view.drawGameState(
			model.getAvailableForks(),
			model.getStatus()
		);
	}

	@Override
	public void userInput(int key) {
		final int SPEED_CHANGE = 100;

		switch (key) {
			case 'r' -> {
				model.reset();
				System.out.println("Dinner restarted!");
			}
			case 'w' -> {
				int newSpeed = model.getSpeed() + SPEED_CHANGE;
				model.setSpeed(newSpeed);
				System.out.println("Speed increased to " + newSpeed);
			}
			case 's' -> {
				int newSpeed = Math.max(1, model.getSpeed() - SPEED_CHANGE);
				model.setSpeed(newSpeed);
				System.out.println("Speed decreased to " + newSpeed);
			}
			default -> System.out.println("Unknown key: " + key);
		}
	}
}
