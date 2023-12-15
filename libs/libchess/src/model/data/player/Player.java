package model.data.player;

import model.data.Color;
import model.data.unit.Unit;

import java.io.Serializable;

public class Player implements Serializable {
    private Unit[] units;
    Color color;

    public Player(int material) {
        setMaterial(material);
    }

    public int getMaterial() {
        return material;
    }

    private void setMaterial(int material) {
        this.material = material;
    }
}
