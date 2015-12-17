/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.layout;

public class Rect {

	public float x = 0.0f;
	public float y = 0.0f;
	public float width = 0.0f;
	public float height = 0.0f;

	public Rect() {
	}

	public Rect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rect expandedBy(EdgeSizes edge) {
		Rect rect = new Rect();

		rect.x = this.x - edge.left;
		rect.y = this.y - edge.top;

		rect.width = this.width + edge.left + edge.right;
		rect.height = this.height + edge.top + edge.bottom;
		return rect;
	}

	public Rect expandByAll(EdgeSizes border, EdgeSizes margin, EdgeSizes padding) {
		Rect rect = new Rect();

		rect.x = this.x - border.left - margin.left - padding.left;
		rect.y = this.y - border.top - margin.top - padding.top;

		rect.width = this.width + border.left + border.right + margin.left + margin.right + padding.left + padding.right;
		rect.height = this.height + border.top + border.bottom + margin.top + margin.bottom + padding.top + padding.bottom;
		return rect;
	}
}
