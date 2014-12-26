package com.adhamenaya.css;

public class Specificity {

	public int a; // ID level
	public int b; // Class level
	public int c; // Tag level

	public int getInt() {
		return Integer.parseInt(String.valueOf(a) + String.valueOf(b)
				+ String.valueOf(c));
	}

	public String get() {
		return String.valueOf(a) + String.valueOf(b) + String.valueOf(c);
	}
}
