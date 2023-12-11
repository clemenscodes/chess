import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a thread that can be either a client or a server.
 * It automatically listens to new messages and either calls:
 * poketrade.setNewTradeOffer  if it receives a pokemon or
 * poketrade.trade()           if it receives a TRADE String
 * Please note that this application does not handle exceptions well.
 * For example, the client crashes as soon as the server is closed.
 * In your project, you should handle these errors with more care.
 */
public class ClientServerThread extends Thread {

	private ServerSocket serversocket;
	private Socket socket;
	private final PokeTrade poketrade;
	private ObjectOutputStream oos;

	private ClientServerThread(PokeTrade pt) {
		this.poketrade = pt;
	}

	/**
	 * Constructor for a server thread.
	 *
	 * @param port      Port to use for the socket
	 * @param poketrade Object for callbacks
	 */
	public static ClientServerThread newServer(int port, PokeTrade poketrade) {
		var cst = new ClientServerThread(poketrade);
		try {
			cst.serversocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.printf(e.toString());
		}
		return cst;
	}

	/**
	 * Constructor for a client thread.
	 *
	 * @param ip        IP to connect to
	 * @param port      Port to connect to
	 * @param poketrade Object for callbacks
	 */
	public static ClientServerThread newClient(
		String ip,
		int port,
		PokeTrade poketrade
	) {
		var cst = new ClientServerThread(poketrade);
		try {
			cst.socket = new Socket(ip, port);
			cst.oos = new ObjectOutputStream(cst.socket.getOutputStream());
		} catch (IOException e) {
			System.err.printf(e.toString());
		}
		return cst;
	}

	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	public void send(Object obj) {
		try {
			if (oos != null) {
				oos.reset();
				oos.writeObject(obj);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			// If this is a server accept one client
			if (socket == null) {
				socket = serversocket.accept();
				oos = new ObjectOutputStream(socket.getOutputStream());
			}

			// Read objects
			var ois = new ObjectInputStream(socket.getInputStream());
			//noinspection InfiniteLoopStatement
			while (true) {
				Object obj = ois.readObject();
				if (obj instanceof Pokemon) {
					poketrade.setNewTradeOffer((Pokemon) obj);
				} else if (obj instanceof String) {
					switch ((String) obj) {
						case "CLEAR" -> {
							poketrade.setNewTradeOffer(null);
						}
						case "TRADE" -> {
							poketrade.trade();
						}
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
