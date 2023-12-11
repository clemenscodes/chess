public class TCPong extends AbstractTCPong {

	public TCPong() {
		super(900, 600, "127.0.0.1", 8000);
	}

	public static void main(String[] args) {
		TCPong game = new TCPong();
		game.setup();
	}
}
