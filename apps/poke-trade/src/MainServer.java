import processing.core.PApplet;

/**
 * Starts a PokeTrade server with four pokemons.
 * This should be started before the client.
 */
abstract class MainServer {

	public static void main(String[] args) {
		var pokemons = new Pokemon[] {
			new Pokemon("Pika", 25, 17),
			new Pokemon("Rettan", 23, 23),
			new Pokemon("Pixi", 36, 17),
			new Pokemon("Enton", 54, 36),
		};
		var pt = PokeTrade.newServer(8080, pokemons);
		// Starts the processing application
		PApplet.runSketch(new String[] { "PokeTrade" }, pt);
	}
}
