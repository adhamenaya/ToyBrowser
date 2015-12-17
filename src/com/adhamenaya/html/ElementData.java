/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ElementData {

	public String tagName;
	public Map<String, String> attributes;

	public ElementData(String tagName, Map<String, String> attributes) {

		this.tagName = tagName;
		this.attributes = attributes;
	}

	public String getId() {
		return attributes.get("id");
	}

	public ArrayList<String> getClasses() {
		if (attributes.get("class") != null)
			return new ArrayList<String>(Arrays.asList(attributes.get("class").split(" ")));
		else
			return new ArrayList<String>();
	}
}
