import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

/**
 * Creates a PokeTrade GUI which is either a client or a server.
 * Please note this is not an MVC-application. Use VW05 and VW06 for MVC examples.
 * <p>
 * Start it with: <code>PokeTrade.newClient()</code> or <code>PokeTrade.newServer()</code>.
 */
public class PokeTrade extends PApplet {

	private ClientServerThread thread;

	private PImage[] pokemonImages;
	private Pokemon[] pokemons;
	/**
	 * The selected pokemon for trading.
	 * Or -1 if no pokemon is selected.
	 */
	private int selection = -1;

	/**
	 * Lock object for tradeImage and tradePokemon.
	 * Should be used when these variables are changed or drawn.
	 */
	private Object lock = new Object();
	/**
	 * The suggested trade from the network.
	 */
	private Pokemon tradePokemon = null;
	private PImage tradeImage = null;

	/**
	 * Constructor is private. Use the static methods:
	 * newClient() to create a client or
	 * newServer() to create a server.
	 *
	 * @param pokemons pokemons that can be traded
	 */
	private PokeTrade(Pokemon[] pokemons) {
		assert (pokemons.length > 0);
		assert (pokemons.length <= 6);
		this.pokemons = pokemons;
	}

	/**
	 * Creates a PokeTrade server.
	 *
	 * @param port     the port to bind the socket to
	 * @param pokemons pokemons to trade
	 * @return a new poketrade object
	 */
	public static PokeTrade newServer(int port, Pokemon[] pokemons) {
		var pt = new PokeTrade(pokemons);
		pt.thread = ClientServerThread.newServer(port, pt);
		pt.thread.start();
		return pt;
	}

	/**
	 * Creates a PokeTrade client.
	 *
	 * @param ip       the IP of the server to connect to
	 * @param port     the port of the server to connect to
	 * @param pokemons pokemons to trade
	 * @return a new poketrade object
	 */
	public static PokeTrade newClient(String ip, int port, Pokemon[] pokemons) {
		var pt = new PokeTrade(pokemons);
		pt.thread = ClientServerThread.newClient(ip, port, pt);
		pt.thread.start();
		return pt;
	}

	@Override
	public void settings() {
		// Sets window parameters
		setSize(600, 275);
		pixelDensity(1);

		// Loads pokemon images at start
		pokemonImages = new PImage[pokemons.length];
		for (int i = 0; i < pokemonImages.length; i++) {
			pokemonImages[i] = loadImage(pokemons[i].getPictureURL());
			pokemonImages[i].resize(200, 200);
		}
	}

	@Override
	public void setup() {}

	@Override
	public void draw() {
		background(255);
		// Draw selection rectangle
		if (selection >= 0 && selection < pokemonImages.length) {
			noStroke();
			fill(color(255, 150, 150));
			rect(100 * selection, 0, 100, 100);
		}
		// Draw team
		for (int i = 0; i < pokemonImages.length; i++) {
			image(pokemonImages[i], 100 * i, 0, 100, 100);
		}

		if (thread.isConnected()) {
			fill(0);
			textSize(14);
			text("Suggested Trade", 250, 140);
			// Draw trade pokemon
			synchronized (lock) {
				if (tradeImage != null) {
					image(tradeImage, 250, 150, 100, 100);
				} else {
					stroke(200);
					noFill();
					rect(250, 150, 100, 100);
				}
			}
		} else {
			// Draw waiting text
			fill(0);
			textSize(14);
			text("Waiting for client to connect", 250, 140);
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (key) {
			case '1' -> {
				setNewSelection(0);
			}
			case '2' -> {
				setNewSelection(1);
			}
			case '3' -> {
				setNewSelection(2);
			}
			case '4' -> {
				setNewSelection(3);
			}
			case '5' -> {
				setNewSelection(4);
			}
			case '6' -> {
				setNewSelection(5);
			}
			case '0' -> {
				selection = -1;
				thread.send("CLEAR");
				System.out.println("Selection cleared!");
			}
			case ' ' -> {
				// A pokemon is offered and a selection exists
				if (tradePokemon != null && selection >= 0) {
					thread.send("TRADE");
					trade();
				}
			}
		}
	}

	private void setNewSelection(int index) {
		if (pokemons.length > index) {
			selection = index;
			thread.send(pokemons[index]);
			System.out.println("Selected " + pokemons[index].name());
		}
	}

	public void setNewTradeOffer(Pokemon p) {
		synchronized (lock) {
			this.tradePokemon = p;
			if (p == null) {
				System.out.println("Offer got removed");
				this.tradeImage = null;
			} else {
				System.out.println("Got offered a " + p.name());
				this.tradeImage = loadImage(p.getPictureURL());
			}
		}
	}

	/**
	 * Swaps the selected pokemon for the traded one.
	 */
	public void trade() {
		synchronized (lock) {
			System.out.println(
				String.format(
					"Traded %s for %s",
					pokemons[selection].name(),
					tradePokemon.name()
				)
			);

			pokemons[selection] = this.tradePokemon;
			pokemonImages[selection] = this.tradeImage;
			this.tradePokemon = null;
			this.tradeImage = null;
			selection = -1;
		}
	}
}
