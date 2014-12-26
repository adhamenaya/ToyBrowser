package com.adhamenaya.layout;

public interface BoxType {
	public <T> T accept(BoxTypeVisitor<T> visitor);
}
