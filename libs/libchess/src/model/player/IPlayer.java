package model.player;

import java.util.ArrayList;
import model.piece.extension.*;

public interface IPlayer {
	ArrayList<Pawn> getPawns();

	ArrayList<Knight> getKnights();

	ArrayList<Bishop> getBishops();

	ArrayList<Rook> getRooks();

	ArrayList<Queen> getQueens();

	King getKing();

	boolean isWhite();

	void setPawns(ArrayList<Pawn> pawns);

	void setKnights(ArrayList<Knight> knights);

	void setBishops(ArrayList<Bishop> bishops);

	void setRooks(ArrayList<Rook> rooks);

	void setQueens(ArrayList<Queen> queens);

	void setKing(King king);
}
