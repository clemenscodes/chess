import java.io.*;

public class SerializationDemo {

	record Pokemon(String name, int level) implements Serializable {}

	public static void main(String[] args)
		throws IOException, ClassNotFoundException {
		var p = new Pokemon("Flegmon", 42);
		System.out.println(p);
		savePokemon(p, "Flegmon.ser");

		var q = loadPokemon("Flegmon.ser");
		System.out.println(q);
	}

	static void savePokemon(Pokemon p, String file) throws IOException {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(p);
		} finally {
			oos.close();
		}
	}

	static Pokemon loadPokemon(String file)
		throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			return (Pokemon) ois.readObject();
		} finally {
			ois.close();
		}
	}
}
