package lecture;

/**
 * Example of starting five threads with one shared <b>static</b> counter variable.
 * Due to the time difference between reading and writing the variable, the result is not 500.
 * Example: Thread A reads the value 14; Thread B counts it up to 15; Thread A sets it to 14+1.
 * Therefore, one counting up step went missing due to a race condition.
 * <p>
 * These types of errors often happen when multiple threads use the same resources.
 * To avoid it, use mutex- or semaphore-objects to block simultaneous access.
 */
class CounterThread extends Thread {

	static int counter;

	public static void main(String[] args) throws InterruptedException {
		var t = new Thread[5];
		for (int i = 0; i < t.length; i++) {
			t[i] = new CounterThread();
			t[i].start();
		}
		System.out.println("All threads started");

		for (int i = 0; i < t.length; i++) {
			t[i].join();
		}
		System.out.println("End result is " + counter);
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			var t = (int) (Math.random() * 10);
			try {
				int value = counter;
				Thread.sleep(t);
				counter = value + 1;
				Thread.sleep(t);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
