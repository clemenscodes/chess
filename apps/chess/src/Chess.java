public class Chess extends AbstractChess {

	public Chess(int width, int height, String host, int port) {
		super(width, height, host, port);
	}

	public static void main(String[] args) {
		Chess game = new Chess(900, 600, "127.0.0.1", 8000);
		game.setup();
	}
}
