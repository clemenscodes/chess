import java.util.Map;
import pokemon.*;

public class Main {

	public static void main(String[] args) {
		Pokedex<Pokemon> pokedexOne = new Pokedex<>();
		Pokedex<Pokemon> pokedexTwo = new Pokedex<>();
		Map<String, Pokemon> pokemonOne = pokedexOne.getPokemon();
		Map<String, Pokemon> pokemonTwo = pokedexTwo.getPokemon();
		pokemonOne.put("Pikachu", new Pikachu(100, 20));
		pokemonOne.put("Lavados", new Lavados(170, 10));
		pokemonTwo.put("Glurak", new Glurak(140, 14));
		pokemonTwo.put("Zapdos", new Zapdos(180, 9));
        pokemonTwo.put("Pikachu", new Pikachu(100, 20));

		System.out.println("Before swap");
		System.out.println("Pokedex One:");
		System.out.println(pokedexOne);
		System.out.println("Pokedex Two:");
		System.out.println(pokedexTwo);

		pokedexOne.swap("Pikachu", pokedexTwo, "Glurak");
		pokedexOne.swap("Lavados", pokedexTwo, "Zapdos");

		System.out.println("After swap");
		System.out.println("Pokedex One:");
		System.out.println(pokedexOne);
		System.out.println("Pokedex Two:");
		System.out.println(pokedexTwo);
	}
}
