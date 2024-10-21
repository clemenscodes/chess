package model;

interface IBitboard {
    long getBits();

    void setBits(long bits);

    boolean overlap(IBitboard board);

    void merge(IBitboard board);

    void intersect(IBitboard board);

    void leftShift(int bits);

    void rightShift(int bits);

    void negateBits();

    void toggleBits(IBitboard board);

    void unsetBitByIndex(int index);

    void setBitByIndex(int index);

    IBitboard copy();
}
