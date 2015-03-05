/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.html;

import java.util.Map;
import java.util.Vector;

public class Node {

	public Vector<Node> children;
	public NodeType type;

	public Node(String data) {
		children = new Vector<Node>();
		type = new NodeType(data);
	}

	public Node(String tagName, Map<String, String> attributes, Vector<Node> children) {

		this.children = children;
		type = new NodeType(tagName, attributes);
	}
}
