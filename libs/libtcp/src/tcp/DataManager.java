package tcp;

import java.io.*;
import java.net.Socket;

public class DataManager implements IDataManager {

	private InputStream in;
	private OutputStream out;

	public void setStreams(ConnectionManager connection) {
		try {
			Socket socket = connection.getIsClient()
				? connection.getClientSocket()
				: connection.getServerSocket();
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Failed to set streams: " + e.getMessage());
			System.exit(1);
		}
	}

	public <T extends Serializable> void sendData(T data) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(getOutputStream());
		oos.writeObject(data);
		oos.flush();
	}

	public <T extends Serializable> T receiveData(Class<T> type)
		throws IOException {
		try {
			ObjectInputStream ois = new ObjectInputStream(getInputStream());
			Object data = ois.readObject();
			if (type.isInstance(data)) {
				return type.cast(data);
			}
			System.err.println("[client] Received unexpected data type");
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println(
				"[client] Failed to receive data: " + e.getMessage()
			);
			return null;
		}
	}

	private InputStream getInputStream() {
		return in;
	}

	private OutputStream getOutputStream() {
		return out;
	}
}
