/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.layout;

public class Dimensions {

	// Position of the content area relative to the document origin:
	public Rect content;

	// Surrounding edges:
	public EdgeSizes padding;
	public EdgeSizes border;
	public EdgeSizes margin;

	public Dimensions() {
		content = new Rect();

		padding = new EdgeSizes();
		border = new EdgeSizes();
		margin = new EdgeSizes();
	}

	// The area covered by the content area plus its padding.
	public Rect paddingBox() {
		return content.expandedBy(padding);
	}

	// The area covered by the content area plus padding and borders.
	public Rect borderBox() {
		return content.expandedBy(border);
	}

	// The area covered by the content area plus padding, borders, and margin.
	public Rect marginBox() {
		return content.expandedBy(margin);
	}

	public Rect extraBox() {
		return content.expandByAll(border, margin, padding);
	}
}
