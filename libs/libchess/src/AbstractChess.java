import processing.core.PApplet;
import controller.ChessController;
import model.ChessModel;
import tcp.ConnectionManager;
import tcp.DataManager;
import view.ChessView;

public abstract class AbstractChess extends ChessView {

	private final String host;
	private final int port;

	public AbstractChess(int width, int height, String host, int port) {
		super(width, height);
		this.host = host;
		this.port = port;
	}

	public void setup() {
		var model = new ChessModel();
		var view = new ChessView(width, height);
		var controller = new ChessController();
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
