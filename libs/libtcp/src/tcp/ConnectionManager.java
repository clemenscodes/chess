package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager implements IConnectionManager {

	private ServerSocket server;
	private Socket serverSocket;
	private Socket clientSocket;
	private final String host;

	private final int port;
	private boolean isClient;
	private boolean connectionAlive;

	public ConnectionManager(String host, int port) {
		this.host = host;
		this.port = port;
		startClient();
	}

	public boolean getConnectionAlive() {
		return connectionAlive;
	}

	public boolean getIsClient() {
		return isClient;
	}

	public Socket getServerSocket() {
		return serverSocket;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	private ServerSocket getServer() {
		return server;
	}

	private void setServer(ServerSocket server) {
		this.server = server;
	}

	private void setIsClient(boolean isClient) {
		this.isClient = isClient;
	}

	private void setServerSocket(Socket serverSocket) {
		this.serverSocket = serverSocket;
	}

	private void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	private void setConnectionAlive(boolean connectionAlive) {
		this.connectionAlive = connectionAlive;
	}

	public void handleClientConnectionLoss() {
		System.err.println("[server] Client disconnected");
		setConnectionAlive(false);
		setClientSocket(null);
		waitForClient();
	}

	public void handleServerConnectionLoss() {
		System.err.println("[client] Server offline");
		setConnectionAlive(false);
		setServer(null);
		setServerSocket(null);
	}

	public void startClient() {
		System.out.printf(
			"[client] Connecting with server socket [host: %s, port: %d]\n",
			host,
			port
		);
		try {
			setClientSocket(new Socket(host, port));
			setIsClient(true);
			setConnectionAlive(true);
			System.out.println("[client] Connected with server socket");
		} catch (IOException ignored) {
			System.out.println("[client] Failed to connect to server socket");
			startServer();
		}
	}

	public void startServer() {
		System.out.printf("[server] Starting server [port: %d]\n", port);
		try {
			setIsClient(false);
			setServer(new ServerSocket(port));
			System.out.println("[server] Started server");
			waitForClient();
		} catch (IOException e) {
			System.err.println("[server] Failed to start server");
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public void waitForClient() {
		try {
			System.out.println("[server] waiting for client...");
			setServerSocket(getServer().accept());
			setConnectionAlive(true);
			System.out.println("[server] Client connected");
		} catch (IOException e) {
			System.err.println("[server] Error while connecting with client");
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
