package lecture;

/**
 * Example of starting two independent threads.
 */
class SimpleThread extends Thread {

	private String name;

	public static void main(String[] args) {
		var t1 = new SimpleThread("T1");
		t1.start();
		var t2 = new SimpleThread("T2");
		t2.start();
		System.out.println("End of Main Thread");
	}

	public SimpleThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(name + " is running");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
