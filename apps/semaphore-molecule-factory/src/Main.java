import java.util.Random;
import java.util.concurrent.Semaphore;
import processing.core.PApplet;

public class Main extends PApplet {

	Semaphore hSemaphore = new Semaphore(1);
	Semaphore oSemaphore = new Semaphore(1);

	WasserstoffGenerator hGenerator = new WasserstoffGenerator(
		hSemaphore,
		1000
	);
	SauerstoffGenerator oGenerator = new SauerstoffGenerator(oSemaphore, 1000);
	WasserGenerator h2oGenerator = new WasserGenerator(
		hSemaphore,
		oSemaphore,
		1500
	);
	OzonGenerator o3Generator = new OzonGenerator(oSemaphore, 2000);

	public static void main(String[] args) {
		PApplet.main(Main.class);
	}

	@Override
	public void settings() {
		size(750, 550);
		pixelDensity(1);
	}

	@Override
	public void setup() {
		hGenerator.start();
		oGenerator.start();
		h2oGenerator.start();
		o3Generator.start();
	}

	@Override
	public void draw() {
		long h2oUnits = h2oGenerator.getUnits();
		long o3Units = o3Generator.getUnits();
		background(255);
		fill(0);
		textSize(16);
		text(
			"Wasserstoff-Atome verfügbar: " + hSemaphore.availablePermits(),
			20,
			30
		);
		text(
			"Sauerstoff-Atome verfügbar: " + oSemaphore.availablePermits(),
			20,
			50
		);
		text("Wasser-Moleküle: " + h2oUnits, 20, 70);
		text("Ozon-Moleküle: " + o3Units, 20, 90);
	}
}

class WasserstoffGenerator extends Thread {

	private final Semaphore semaphore;
	private final int maxDistance;

	public WasserstoffGenerator(Semaphore semaphore, int maxDistance) {
		this.semaphore = semaphore;
		this.maxDistance = maxDistance;
	}

	@Override
	public void run() {
		Random random = new Random();

		//noinspection InfiniteLoopStatement
		while (true) {
			try {
				synchronized (this) {
					wait(random.nextInt(maxDistance));
				}
				System.out.println("Wasserstoff-Atom erzeugt");
				System.out.print("Wasserstoff-Atome verfügbar: ");
				System.out.println(semaphore.availablePermits());
				semaphore.release();
			} catch (InterruptedException e) {
				System.err.printf(e.toString());
			}
		}
	}
}

class SauerstoffGenerator extends Thread {

	private final Semaphore semaphore;
	private final int maxDistance;

	public SauerstoffGenerator(Semaphore semaphore, int maxDistance) {
		this.semaphore = semaphore;
		this.maxDistance = maxDistance;
	}

	@Override
	public void run() {
		Random random = new Random();

		//noinspection InfiniteLoopStatement
		while (true) {
			try {
				synchronized (this) {
					wait(random.nextInt(maxDistance));
				}
				System.out.println("Sauerstoff-Atom erzeugt");
				System.out.print("Sauerstoff-Atome verfügbar: ");
				System.out.println(semaphore.availablePermits());
				semaphore.release();
			} catch (InterruptedException e) {
				System.err.printf(e.toString());
			}
		}
	}
}

class WasserGenerator extends Thread {

	private final Semaphore hSemaphore;
	private final Semaphore oSemaphore;
	private final int distance;
	private long createdUnits;

	public long getUnits() {
		return createdUnits;
	}

	public WasserGenerator(
		Semaphore hSemaphore,
		Semaphore oSemaphore,
		int distance
	) {
		this.hSemaphore = hSemaphore;
		this.oSemaphore = oSemaphore;
		this.distance = distance;
	}

	@Override
	public void run() {
		//noinspection InfiniteLoopStatement
		while (true) {
			try {
				hSemaphore.acquire(2);
				oSemaphore.acquire();
				synchronized (this) {
					wait(distance);
				}
				createdUnits++;
				System.out.println("Wasser-Molekül erzeugt");
				System.out.print("Wasser-Moleküle: ");
				System.out.println(createdUnits);
			} catch (InterruptedException e) {
				System.err.printf(e.toString());
			}
		}
	}
}

class OzonGenerator extends Thread {

	private final Semaphore oSemaphore;
	private final int distance;
	private long createdUnits;

	public long getUnits() {
		return createdUnits;
	}

	public OzonGenerator(Semaphore oSemaphore, int distance) {
		this.oSemaphore = oSemaphore;
		this.distance = distance;
	}

	@Override
	public void run() {
		//noinspection InfiniteLoopStatement
		while (true) {
			try {
				oSemaphore.acquire(3);
				synchronized (this) {
					wait(distance);
				}
				createdUnits++;
				System.out.println("Ozon-Molekül erzeugt");
				System.out.print("Ozon-Moleküle: ");
				System.out.println(createdUnits);
			} catch (InterruptedException e) {
				System.err.printf(e.toString());
			}
		}
	}
}
