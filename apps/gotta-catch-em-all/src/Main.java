import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main(Main.class);
	}

	private PImage[] gen1;
	private static final int THREAD_COUNT = 5;
	Thread[] threads = new Thread[THREAD_COUNT];
	private final ArrayList<Boolean> threadFinished = new ArrayList<>();

	@Override
	public void settings() {
		size(750, 550);
		pixelDensity(1);
	}

	@Override
	public void setup() {
		gen1 = new PImage[151];
		int threadIndex = 0;

		for (int i = 0; i < THREAD_COUNT; i++) {
			int start = i * (gen1.length / THREAD_COUNT);
			int end = (i + 1) * (gen1.length / THREAD_COUNT);
			if (i == THREAD_COUNT - 1) {
				end++;
			}
			threads[i] = new ImageLoaderThread(start, end, threadIndex);
			threads[i].start();
			threadIndex++;
			threadFinished.add(false);
		}
	}

	private boolean checkAllThreadsLoaded() {
		for (boolean finished : threadFinished) {
			if (!finished) {
				return false;
			}
		}
		return true;
	}

	private void checkAllImagesLoaded() {
		int i = 0;
		for (var image : gen1) {
			if (image != null) {
				i++;
			}
		}
		System.out.print("Loaded: ");
		System.out.println(i);
	}

	@Override
	public void draw() {
		background(255);

		for (int i = 0; i < gen1.length; i++) {
			int size = 50;
			int xPos = (i % 15) * size;
			int yPos = (i / 15) * size;

			if (gen1[i] != null) {
				image(gen1[i], xPos, yPos, size, size);
			}
		}

		// If all images are loaded, stop the threads to prevent unnecessary processing
		if (checkAllThreadsLoaded()) {
			noLoop();
			System.out.println("All threads done");
			checkAllImagesLoaded();
		}
	}

	class ImageLoaderThread extends Thread {

		private final int startIndex;
		private final int endIndex;
		private final int threadIndex;

		ImageLoaderThread(int startIndex, int endIndex, int threadIndex) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadIndex = threadIndex;
		}

		@Override
		public void run() {
			for (int i = startIndex; i < endIndex; i++) {
				String imageUrl = String.format(
					"https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png",
					i + 1
				);
				PImage img = loadImage(imageUrl);
				if (img != null) {
					gen1[i] = img;
					System.out.println("Loaded: " + i);
				}
			}
			threadFinished.set(threadIndex, true);
		}
	}
}
