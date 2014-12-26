package com.adhamenaya.layout;

import com.adhamenaya.style.StyledNode;

public class InlineType implements BoxType {
	public final StyledNode styledNode;

	public InlineType(StyledNode styledNode) {
		this.styledNode = styledNode;
	}

	@Override
	public <T> T accept(BoxTypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}