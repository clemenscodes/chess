import java.io.Serializable;

/**
 * A basic pokemon record. The pokemon objects cannot be changed after creation (records are immutable).
 *
 * @param name      Name of the pokemon
 * @param pokeindex Index in the pokedex
 * @param level     The level of the pokemon
 */
public record Pokemon(String name, int pokeindex, int level)
	implements Serializable {
	/**
	 * Returns the URL to the pokemon picture
	 *
	 * @return URL of the picture
	 */
	public String getPictureURL() {
		return String.format(
			"https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png",
			this.pokeindex()
		);
	}
}
