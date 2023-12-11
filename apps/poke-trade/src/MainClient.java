import processing.core.PApplet;

/**
 * Starts a PokeTrade client with six pokemons.
 * This needs to be started after the server.
 */
abstract class MainClient {

	public static void main(String[] args) {
		var pokemons = new Pokemon[] {
			new Pokemon("Hornliu", 13, 8),
			new Pokemon("Paras", 46, 11),
			new Pokemon("Machollo", 66, 27),
			new Pokemon("Porenta", 83, 44),
			new Pokemon("Voltoball", 100, 62),
			new Pokemon("Mew", 151, 99),
		};
		var pt = PokeTrade.newClient("localhost", 8080, pokemons);
		// Starts the processing application
		PApplet.runSketch(new String[] { "PokeTrade" }, pt);
	}
}
