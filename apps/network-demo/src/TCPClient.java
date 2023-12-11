import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TCPClient {

	static int PORT = 8080;

	public static void main(String[] args)
		throws IOException, InterruptedException {
		var socket = new Socket("127.0.0.1", PORT);
		var writer = new PrintWriter(socket.getOutputStream(), true);
		for (int i = 0; i < 10; i++) {
			writer.println("Hello World " + i);
			TimeUnit.SECONDS.sleep(1);
		}
		writer.println("GAMEOVER");
		System.out.println("DONE");
	}
}
