package com.adhamenaya.css;

public class Length extends Keyword {

	float value = 0.0f;
	Unit unit;

	public Length() {
		super("");
	}

	public Length(float value, Unit unit) {
		super(value + "" + unit.toString());
		this.value = value;
		this.unit = unit;
	}

	public void setLength(Length length) {
		this.value = length.value;
		this.unit = length.unit;
	}

	// Return the size of a length in px, or zero for non-lengths.
	public float toPx() {
		return this.value;

	}

	@Override
	public void setKeyword(Keyword keyword) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

}
