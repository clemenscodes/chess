package chess;

import api.controller.IChessController;
import api.model.IChessModel;
import api.view.IChessView;
import controller.ChessController;
import model.ChessModel;
import processing.core.PApplet;
import view.ChessView;

public class Chess {

	public static void main(String[] args) {
		IChessModel model = new ChessModel();
		IChessController controller = new ChessController();
		IChessView view = new ChessView();
		controller.setModel(model);
		controller.setView(view);
		view.setController(controller);
		PApplet.runSketch(new String[] { "view.ChessView" }, (PApplet) view);
	}
}
