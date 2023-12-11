import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

public class UDPClient {

	public static void main(String[] args)
		throws IOException, InterruptedException {
		DatagramChannel channel = DatagramChannel.open();
		SocketAddress server = new InetSocketAddress("time.nist.gov", 37);

		// Get three timestamps from server
		for (int i = 0; i < 3; i++) {
			// Send a request with some dummy data
			ByteBuffer buffer = ByteBuffer.allocate(8);
			buffer.put("test".getBytes());
			channel.send(buffer, server);

			// Clear the buffer
			buffer.clear();
			// Prepend four empty bytes to make the response a long value
			// This is a small hack, since Java has no unsigned integers
			buffer.putInt(0);

			// Read the response (seconds since 1-Jan-1900 as unsigned 32bit-integer)
			channel.receive(buffer);
			buffer.flip(); // write to read flip

			// Read four zeros and 32bit integer as a long
			var seconds = buffer.getLong();
			System.out.println(
				String.format("Seconds since 1-Jan-1900:\t %d", seconds)
			);
			System.out.println(
				String.format(
					"Years since 1-Jan-1900:\t\t %d",
					seconds / 60 / 60 / 24 / 365
				)
			);
			System.out.println();
			TimeUnit.SECONDS.sleep(10);
		}
	}
}
