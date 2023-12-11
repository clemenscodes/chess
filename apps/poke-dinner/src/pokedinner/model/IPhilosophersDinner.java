package pokedinner.model;

public interface IPhilosophersDinner {
	void reset();
	boolean[] getAvailableForks();
	PhilosopherStatus[] getStatus();
	int getSpeed();
	void setSpeed(int speed);
}
