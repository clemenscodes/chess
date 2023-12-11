package pokedinner.model;

import java.util.concurrent.Semaphore;

public class PhilosophersDinner implements IPhilosophersDinner {

	/**
	 * Semaphore for the forks (only one permit).
	 */
	private Semaphore[] forks;
	private Philosopher[] philosophers;
	private int speed;

	/**
	 * Creates a new philosophers dinner with five philosophers.
	 *
	 * @param speed the maximum speed of the philosophers in milliseconds (> 0).
	 */
	public PhilosophersDinner(int speed) {
		this.setSpeed(speed);
		reset();
	}

	public void reset() {
		// Create mutex objects for the forks
		forks = new Semaphore[5];
		for (int i = 0; i < forks.length; i++) {
			forks[i] = new Semaphore(1);
		}

		if (philosophers == null) {
			philosophers = new Philosopher[5];
		} else {
			// Stop/kill old philosophers
			for (int i = 0; i < philosophers.length; i++) {
				if (philosophers[i] != null) philosophers[i].interrupt();
			}
		}

		// Create philosophers with access to their left+right fork
		philosophers[0] = new Philosopher(forks[0], forks[1]);
		philosophers[1] = new Philosopher(forks[1], forks[2]);
		philosophers[2] = new Philosopher(forks[2], forks[3]);
		philosophers[3] = new Philosopher(forks[3], forks[4]);
		philosophers[4] = new Philosopher(forks[4], forks[0]);

		for (int i = 0; i < philosophers.length; i++) {
			philosophers[i].start();
		}
	}

	/**
	 * Returns the availability of the five forks. The first fork is the left fork of the first philosopher.
	 *
	 * @return array with each of the five fields representing if fork is on the table (=available).
	 */
	public boolean[] getAvailableForks() {
		var state = new boolean[5];
		state[0] = forks[0].availablePermits() > 0;
		state[1] = forks[1].availablePermits() > 0;
		state[2] = forks[2].availablePermits() > 0;
		state[3] = forks[3].availablePermits() > 0;
		state[4] = forks[4].availablePermits() > 0;
		return state;
	}

	/**
	 * Returns the status of the five philosophers.
	 *
	 * @return array with the status of the five philosophers
	 */
	public PhilosopherStatus[] getStatus() {
		var state = new PhilosopherStatus[5];
		state[0] = philosophers[0].getStatus();
		state[1] = philosophers[1].getStatus();
		state[2] = philosophers[2].getStatus();
		state[3] = philosophers[3].getStatus();
		state[4] = philosophers[4].getStatus();
		return state;
	}

	/**
	 * Philosopher speed in milliseconds.
	 *
	 * @return the speed in ms
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Sets the time a philosopher needs to grasp the forks and eat.
	 * Smaller speed means faster eating, but philosopher gets hungry faster.
	 *
	 * @param speed new speed in ms
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * A philosopher as a thread.
	 */
	class Philosopher extends Thread {

		private PhilosopherStatus status;
		private Semaphore leftFork;
		private Semaphore rightFork;

		public Philosopher(Semaphore leftFork, Semaphore rightFork) {
			this.leftFork = leftFork;
			this.rightFork = rightFork;
			this.status = PhilosopherStatus.THINKING;
		}

		@Override
		public void run() {
			try {
				while (true) {
					// Start with deep thinking
					status = PhilosopherStatus.THINKING;
					Thread.sleep((int) (Math.random() * getSpeed() * 10));

					// Philosopher gets hungry
					status = PhilosopherStatus.HUNGRY;
					Thread.sleep(getSpeed());

					// Wait for a left fork
					leftFork.acquire();
					status = PhilosopherStatus.HOLDS_FORK;
					Thread.sleep(getSpeed());

					// Wait for a right fork
					rightFork.acquire();
					status = PhilosopherStatus.EATING;
					Thread.sleep(getSpeed());

					// Return both forks
					leftFork.release();
					rightFork.release();
				}
			} catch (InterruptedException e) {
				System.out.println("Oh no, I got replaced.");
			}
		}

		public PhilosopherStatus getStatus() {
			return status;
		}
	}
}
