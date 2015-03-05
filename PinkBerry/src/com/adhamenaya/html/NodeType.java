/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.html;

import java.util.Map;

public class NodeType {

	// Text node
	public String text;

	// Element node
	public ElementData element;

	public NodeType(String text) {
		this.text = text;
	}

	public NodeType(String tagName, Map<String, String> attributes) {
		this.element = new ElementData(tagName, attributes);
	}

	public boolean isText() {
		if (element == null && text != null)
			return true;
		else
			return false;
	}
}
