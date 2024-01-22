package model;

interface IReader<T> {
	T read();
	T peek();
	void flush();
}
