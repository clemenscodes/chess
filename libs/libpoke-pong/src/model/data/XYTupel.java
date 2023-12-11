package model.data;

import java.io.Serializable;

public class XYTupel implements Serializable {

	public float x = 0;
	public float y = 0;

	public XYTupel() {}

	public XYTupel(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
