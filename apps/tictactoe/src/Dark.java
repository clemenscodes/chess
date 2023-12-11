import lib.AbstractTicTacToe;

public class Dark extends AbstractTicTacToe {

	public Dark(int size, boolean darkMode) {
		super(size, darkMode);
	}

	public static void main(String[] args) {
		Dark dark = new Dark(900, true);
		dark.setup();
	}
}
