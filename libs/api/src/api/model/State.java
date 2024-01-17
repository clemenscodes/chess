package api.model;

import java.io.Serializable;

public enum State implements Serializable {
	Start,
	Playing,
	Checkmate,
	Stalemate,
	GameOver,
}
