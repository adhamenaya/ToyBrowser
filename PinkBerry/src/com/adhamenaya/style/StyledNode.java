/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.style;

import java.util.HashMap;
import java.util.Vector;

import com.adhamenaya.css.Keyword;
import com.adhamenaya.css.Value;
import com.adhamenaya.html.Node;
import com.adhamenaya.layout.Display;

public class StyledNode {

	public Node node;

	// Property map
	public HashMap<String, Value> specifiedValues;

	public Vector<StyledNode> children;

	// Return the specified value of a property if it exists, otherwise `None`.
	public Value getValueOf(String name) {
		return specifiedValues.get(name);
	}

	// The value of the `display` property (defaults to in line).
	public Display getValueOfDisplay() {

		if (specifiedValues.containsKey("display")) {
			if (((Keyword) specifiedValues.get("display")).getKeyword().equals("block"))
				return Display.BLOCK;

			else if (((Keyword) specifiedValues.get("display")).getKeyword().equals("none"))
				return Display.NONE;

			else
				return Display.INLINE;

		} else
			return Display.INLINE;
	}

	public void insertValue1(String key, Value value) {
		specifiedValues.put(key, value);
	}

	public Value lookUp(Value defualtValue, String args[]) {

		for (int i = 0; i < args.length; i++) {
			if (specifiedValues.get(args[i]) != null)
				return specifiedValues.get(args[i]);
		}

		return defualtValue;

	}
}
