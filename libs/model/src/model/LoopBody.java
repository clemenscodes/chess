package model;

import api.model.IBitboard;

@FunctionalInterface
interface LoopBody {
	void apply(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces);
}
