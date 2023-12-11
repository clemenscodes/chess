import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	static int PORT = 8080;

	public static void main(String[] args) throws IOException {
		var server = new ServerSocket(PORT);
		System.out.println("WAITING FOR CLIENT");

		// Blocks until a client connects
		var socket = server.accept();
		System.out.println("CLIENT CONNECTED");

		String line = "";
		var reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);
		while (!line.equals("GAMEOVER")) {
			// BufferedReader reads a whole line
			line = reader.readLine();
			System.out.println("RECEIVED: " + line);
		}
		System.out.println("END OF PROGRAM");
	}
}
