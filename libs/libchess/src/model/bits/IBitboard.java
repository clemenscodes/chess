package model.bits;

public interface IBitboard {
	long getBits();

	void setBits(long bits);

	boolean contains(IBitboard board);

	void merge(IBitboard board);

	void intersect(IBitboard board);

	void leftShift(int bits);

	void rightShift(int bits);

	void negateBits();
}
