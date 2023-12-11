import controller.TCPongController;
import model.PokePongModel;
import processing.core.PApplet;
import tcp.ConnectionManager;
import tcp.DataManager;
import view.PokePongView;

public class AbstractTCPong extends PokePongView {

	private final String host;
	private final int port;

	public AbstractTCPong(int width, int height, String host, int port) {
		super(width, height);
		this.host = host;
		this.port = port;
	}

	public void setup() {
		var model = new PokePongModel();
		var view = new PokePongView(width, height);
		var controller = new TCPongController();
		var connectionManager = new ConnectionManager(host, port);
		var dataManager = new DataManager();

		controller.setModel(model);
		controller.setView(view);
		controller.setConnectionManager(connectionManager);
		controller.setDataManager(dataManager);

		view.setController(controller);
		PApplet.runSketch(new String[] { "PokePongView" }, view);
	}
}
