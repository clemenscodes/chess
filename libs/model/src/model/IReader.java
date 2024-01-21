package model;

interface IReader<T> {
	T read();
	void flush();
}
