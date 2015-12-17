/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
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