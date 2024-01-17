public class Chess {

	public static void main(String[] args) {
		ChessModel model = new ChessModel();
		ChessView view = new ChessView();
		ChessController controller = new ChessController();
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);
		processing.core.PApplet.runSketch(new String[] { "ChessView" }, view);
	}
}
