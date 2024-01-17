import controller.ChessController;
import model.ChessModel;
import processing.core.PApplet;
import view.ChessView;

public abstract class AbstractChess extends ChessView {

	public void setup() {
		ChessModel model = new ChessModel();
		ChessView view = new ChessView();
		ChessController controller = new ChessController();
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);
		PApplet.runSketch(new String[] { "ChessView" }, view);
	}
}
