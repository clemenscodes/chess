package lecture;

import java.util.concurrent.Semaphore;

/**
 * Example of starting five threads with one shared semaphore, which provides two permits.
 * Note that the semaphore is static, hence all threads access the same semaphore object!
 * An improved version would hand over the semaphore to the MySemaphore constructor to avoid the static variable.
 */
class MySemaphore extends Thread {

	private String name;

	public static void main(String[] args) {
		var mutexObj = new Object();
		new MySemaphore("T1").start();
		new MySemaphore("T2").start();
		new MySemaphore("T3").start();
		new MySemaphore("T4").start();
		new MySemaphore("T5").start();
		System.out.println("End of Main Thread");
	}

	public MySemaphore(String name) {
		this.name = name;
	}

	// Erlaubt zwei zeitgleiche Zugriffe
	private static Semaphore semaphore = new Semaphore(2);

	@Override
	public void run() {
		while (true) {
			try {
				semaphore.acquire();
				System.out.println(
					String.format(
						"%s acquired semaphore [%d]",
						name,
						semaphore.availablePermits()
					)
				);
				Thread.sleep(1000);
				semaphore.release();
				System.out.println(
					String.format(
						"%s released semaphore [%d]",
						name,
						semaphore.availablePermits()
					)
				);

				Thread.sleep((int) (Math.random() * 100));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
