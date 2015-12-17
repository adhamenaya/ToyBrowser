/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.run;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.adhamenaya.html.Node;
import com.adhamenaya.html.Pair;

public class HTMLParser extends Parser {

	// Consume characters until `test` returns false.
	String consumeWhile() {
		StringBuilder sb = new StringBuilder();
		while (!eof()) {
			sb.append(consumeChar());
		}
		return sb.toString();
	}

	// Parse a tag or attribute name.
	String parseTagName() {
		StringBuilder sb = new StringBuilder();
		while (!eof() && nextChar().toString().matches("[0-9A-Za-z]")) {
			Character consumedChar = consumeChar();
			sb.append(consumedChar.toString());
		}
		return sb.toString();
	}

	// Parse a single node, we assume that the node start with '<', and the text
	// can't contains this special char.
	Node parseNode() {
		Node node;
		if (nextChar().equals('<'))
			node = parseElement();
		else
			node = parseText();
		return node;
	}

	// Parse a single element, including its open tag, contents, and closing
	// tag.
	Node parseElement() {
		// Opening tag.
		if (!consumeChar().equals('<'))
			return null;

		// Opening tag
		String tagName = parseTagName();

		// The attributes of the tag
		Map<String, String> attributes = parseAttributes();

		Character cc = consumeChar();

		if (!cc.equals('>'))
			return null;

		// Contents.
		Vector<Node> children = parseNodes();

		// Closing tag.
		if (!consumeChar().equals('<'))
			return null;
		if (!consumeChar().equals('/'))
			return null;
		if (!parseTagName().equals(tagName))
			return null;
		if (!consumeChar().equals('>'))
			return null;

		return new Node(tagName, attributes, children);

	}

	// Parse a text node.
	Node parseText() {
		StringBuilder sb = new StringBuilder();
		while (!eof() && !nextChar().equals('<')) {
			sb.append(consumeChar());
		}
		return new Node(sb.toString());
	}

	// Parse a single name="value" pair.
	Pair parseAttribute() {

		final String KeyName = parseTagName();

		String charAfterKey = consumeChar().toString();

		if (!charAfterKey.equals("="))
			return null;

		final String ValueName = parseAttributeValue();

		return new Pair(KeyName, ValueName);
	}

	// Parse a quoted value for the attribute.
	String parseAttributeValue() {

		Character openQoute = consumeChar();
		if (!openQoute.equals('"') && !openQoute.equals('\''))
			return null;

		// Consume while the close quote not reached yet.
		StringBuilder sb = new StringBuilder();
		while (!eof() && !nextChar().equals(openQoute)) {
			sb.append(consumeChar());
		}
		String value = sb.toString();

		if (!consumeChar().equals(openQoute))
			return null;

		return value;
	}

	// Parse a list of name="value" pairs, separated by whitespace.
	HashMap<String, String> parseAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();

		while (true) {
			consumeWhiteSpace();

			Character nextChar = nextChar();

			if (eof() || nextChar.equals('>'))
				break;

			Pair attribute = parseAttribute();

			if (attribute != null)
				attributes.put(attribute.getKey(), attribute.getValue());
		}
		return attributes;
	}

	// Parse a sequence of sibling nodes.
	Vector<Node> parseNodes() {
		Vector<Node> nodes = new Vector<Node>();

		while (true) {
			consumeWhiteSpace();
			if (eof() || startsWith("</"))
				break;
			Node node = parseNode();
			nodes.add(node);
		}
		return nodes;
	}

	// Parse an HTML document and return the root element.
	public Node parse(String source) {

		poistion = 0;
		input = source;

		Vector<Node> nodes = parseNodes();
		if (nodes.size() == 1)
			return nodes.get(0);
		else
			return new Node("html", new HashMap<String, String>(), nodes);
	}
}
