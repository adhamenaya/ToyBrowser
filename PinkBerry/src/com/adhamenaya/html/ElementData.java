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
			return new ArrayList<String>(Arrays.asList(attributes.get("class")
					.split(" ")));
		else
			return new ArrayList<String>();
	}
}
