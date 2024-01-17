import api.model.bits.IBitboard;

@FunctionalInterface
interface LoopBody {
	void apply(int rank, int file, StringBuilder stringBuilder, IBitboard[] pieces);
}
