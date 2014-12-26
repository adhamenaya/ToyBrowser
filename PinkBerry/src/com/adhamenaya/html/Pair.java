package com.adhamenaya.html;

public class Pair {
	private final String left;
	private final String right;

	public Pair(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getKey() {
		return left;
	}

	public String getValue() {
		return right;
	}

	@Override
	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Pair))
			return false;
		Pair pairo = (Pair) o;
		return this.left.equals(pairo.getKey())
				&& this.right.equals(pairo.getValue());
	}

}
