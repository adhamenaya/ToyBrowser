package com.adhamenaya.css;

public class Color extends Value {

	public int r;
	public int g;
	public int b;
	public int a;

	public void setColor(Color color) {
		this.r = color.r;
		this.b = color.b;
		this.g = color.g;
		this.a = color.a;
	}

	@Override
	public void setKeyword(Keyword keyword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLength(Length length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float toPx() {
		// TODO Auto-generated method stub
		return 0;
	}
}
