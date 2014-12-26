package com.adhamenaya.layout;

public class AnonymousType implements BoxType {

	@Override
	public <T> T accept(BoxTypeVisitor<T> visitor) {
		return null;
	}
}
