import controller.ChessController;
import model.ChessModel;
import processing.core.PApplet;
import tcp.ConnectionManager;
import tcp.DataManager;
import view.ChessView;

public abstract class AbstractChess extends ChessView {

	private final String host;
	private final int port;

	public AbstractChess(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void setup() {
		var model = new ChessModel();
		var view = new ChessView();
		var controller = new ChessController();
		var connectionManager = new ConnectionManager(host, port);
		var dataManager = new DataManager();

		controller.setModel(model);
		controller.setView(view);
		controller.setConnectionManager(connectionManager);
		controller.setDataManager(dataManager);

		view.setController(controller);
		PApplet.runSketch(new String[] { "ChessView" }, view);
	}
}
