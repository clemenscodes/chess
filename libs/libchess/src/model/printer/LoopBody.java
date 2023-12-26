package model.printer;

import model.bits.IBitboard;

@FunctionalInterface
public interface LoopBody {
	void apply(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces);
}
