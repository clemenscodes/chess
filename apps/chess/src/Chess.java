public class Chess extends AbstractChess {

	public Chess(String host, int port) {
		super(host, port);
	}

	public static void main(String[] args) {
		Chess game = new Chess("127.0.0.1", 8000);
		game.setup();
	}
}
