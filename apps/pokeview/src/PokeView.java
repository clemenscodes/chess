import static controlP5.ControlP5Constants.ACTION_RELEASE;

import controlP5.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This is a basic pokemon viewer made with ControlP5 GUI widgets.
 * <p>
 * In MVC applications the CallbackListeners should forward the request to a controller method.
 */
public class PokeView extends PApplet {

	private ControlP5 cp5;
	private Textfield pokedexField;
	private Button loadButton;
	private Button randomButton;
	private Slider sizeSlider;
	private Slider rTintSlider;
	private Slider gTintSlider;
	private Slider bTintSlider;

	private PImage pokemon;
	private int size = 300;
	private int rTint = 255, gTint = 255, bTint = 255;

	public static void main(String[] args) {
		PApplet.main(PokeView.class);
	}

	@Override
	public void settings() {
		setSize(600, 400);
		pixelDensity(1);
	}

	@Override
	public void setup() {
		// All ControlP5 elements are initialized inside the setup method
		// They can be accessed later through the class variables

		// Creates a ControlP5 object for the current PApplet
		cp5 = new ControlP5(this);

		// Creates a textfield for the pokemon index
		pokedexField = cp5.addTextfield("ID");
		pokedexField.setLabel("");
		pokedexField.setPosition(10, 30);
		pokedexField.setSize(100, 25);

		// Button to load the pokemon with the index in pokedexField
		loadButton = cp5.addButton("Load");
		loadButton.setPosition(120, 30);
		loadButton.setSize(70, 25);
		// The button calls the given method on button-click release. Release has the advantage that after the user
		// clicked the button, he/she can decide not to activate the methode by moving the mouse out of the button
		// before releasing.
		loadButton.addListenerFor(
			ACTION_RELEASE,
			new CallbackListener() {
				@Override
				public void controlEvent(CallbackEvent callbackEvent) {
					// For MVC-Applications inside this method should only be a call of a controller-method.
					try {
						int index = Integer.parseUnsignedInt(
							pokedexField.getText()
						);
						loadPokemon(index);
					} catch (Exception e) {
						// The following opens a message box using swing. Swing is another GUI framework.
						// It would be better to implement this in Processing. But this is shorter work-around.
						javax.swing.JOptionPane.showMessageDialog(
							null,
							String.format(
								"'%s' is an invalid number. Please enter a valid pokemon index [1-1010].",
								pokedexField.getText()
							)
						);
					}
				}
			}
		);

		// Button to load a random pokemon from generation 1
		randomButton = cp5.addButton("Load Random Pokemon");
		randomButton.setPosition(10, 60);
		randomButton.setSize(180, 25);
		randomButton.addListenerFor(
			ACTION_RELEASE,
			new CallbackListener() {
				@Override
				public void controlEvent(CallbackEvent callbackEvent) {
					try {
						int index = (int) (Math.random() * 150) + 1;
						loadPokemon(index);
					} catch (Exception e) {
						// Cannot happen, because Gen1 Pokemon are always valid
					}
				}
			}
		);

		// Slider to set the size of the picture
		sizeSlider = cp5.addSlider("Size");
		sizeSlider.setPosition(10, 180);
		sizeSlider.setSize(180, 25);
		sizeSlider.setLabel("");
		sizeSlider.setRange(1, 400);
		sizeSlider.setValue(size);
		// The following listener is called whenever the Slider changes
		sizeSlider.addListener(
			new ControlListener() {
				@Override
				public void controlEvent(ControlEvent controlEvent) {
					size = (int) sizeSlider.getValue();
				}
			}
		);

		// Slider to set the tints (r, g, b)
		rTintSlider = cp5.addSlider("Red");
		rTintSlider.setPosition(10, 300);
		rTintSlider.setSize(140, 25);
		rTintSlider.setRange(0, 255);
		rTintSlider.setValue(rTint);
		rTintSlider.setColorCaptionLabel(0);
		rTintSlider.setColorForeground(color(200, 0, 0));
		rTintSlider.setColorActive(color(255, 0, 0));

		gTintSlider = cp5.addSlider("Green");
		gTintSlider.setPosition(10, 330);
		gTintSlider.setSize(140, 25);
		gTintSlider.setRange(0, 255);
		gTintSlider.setValue(gTint);
		gTintSlider.setColorCaptionLabel(0);
		gTintSlider.setColorForeground(color(0, 200, 0));
		gTintSlider.setColorActive(color(0, 255, 0));

		bTintSlider = cp5.addSlider("Blue");
		bTintSlider.setPosition(10, 360);
		bTintSlider.setSize(140, 25);
		bTintSlider.setRange(0, 255);
		bTintSlider.setValue(bTint);
		bTintSlider.setColorCaptionLabel(0);
		bTintSlider.setColorForeground(color(0, 0, 200));
		bTintSlider.setColorActive(color(0, 0, 255));

		// This example shows how the same listener can be used across multiple UI elements
		var tintCallback = new ControlListener() {
			@Override
			public void controlEvent(ControlEvent controlEvent) {
				rTint = (int) rTintSlider.getValue();
				gTint = (int) gTintSlider.getValue();
				bTint = (int) bTintSlider.getValue();
			}
		};
		rTintSlider.addListener(tintCallback);
		gTintSlider.addListener(tintCallback);
		bTintSlider.addListener(tintCallback);
		tintCallback.controlEvent(null);
	}

	@Override
	public void draw() {
		// Note that the draw method does not draw Control-P5 UI elements.
		// These elements automatically draw themselves, unless they are hidden.
		background(255);

		// Draw separator between GUI elements and image
		fill(color(200, 200, 200));
		noStroke();
		rect(0, 0, 200, height);

		// Text labels
		fill(0);
		textSize(18);
		text("Load a Pokemon:", 10, pokedexField.getPosition()[1] - 10);

		fill(0);
		textSize(18);
		text("Image Size:", 10, sizeSlider.getPosition()[1] - 10);

		fill(0);
		textSize(18);
		text("Image Tint:", 10, rTintSlider.getPosition()[1] - 10);

		// Draw image if one is available
		if (pokemon != null) {
			tint(rTint, gTint, bTint);
			image(pokemon, 200, 0, size, size);
			tint(255);
		}
	}

	/**
	 * Loads a pokemon image in the background and saves it to the pokemon PImage.
	 *
	 * @param pokeIndex index of a pokemon
	 * @throws Exception pokeIndex parameter is out of bounds
	 */
	private void loadPokemon(int pokeIndex) throws Exception {
		// If the pokemon does not exist, this method throws an exception that can be handled inside the caller
		if (pokeIndex < 1 || pokeIndex > 1010) throw new Exception(
			"Invalid pokemon index"
		);

		// Pokemons until 999 have padded zeros. After 999 they have four numbers.
		// Apparently the Pokemon company never anticipated that many pokemon.
		String urlTemplate =
			"https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png";
		if (pokeIndex > 999) urlTemplate =
			"https://assets.pokemon.com/assets/cms2/img/pokedex/full/%04d.png";

		// RequestImage does not block the method call, but rather loads the image in the background (=Thread)
		pokemon = requestImage(String.format(urlTemplate, pokeIndex));
		pokedexField.setText(String.valueOf(pokeIndex));
	}
}
