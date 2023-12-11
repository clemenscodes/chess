package lecture;

/**
 * Example of starting two threads with one shared mutex.
 * Note that the mutex is static, hence both threads access the same object!
 * An improved version would hand over the mutex to the MyMutex constructor to avoid the static variable.
 */
class MyMutex extends Thread {

	private String name;

	public static void main(String[] args) {
		new MyMutex("T1").start();
		new MyMutex("T2").start();
		System.out.println("End of Main Thread");
	}

	public MyMutex(String name) {
		this.name = name;
	}

	private static Object mutexObj = new Object();

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (mutexObj) {
					System.out.print(String.format("[%s] Mutex locked", name));
					Thread.sleep(1000);
					System.out.println(" + released");
				}
				Thread.sleep((int) (Math.random() * 100));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
