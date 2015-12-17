/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

import java.util.Vector;

public class SimpleSelector {

	public String id;
	public String tagName;
	public Vector<String> classNames;

	public SimpleSelector(String tagName, String id, Vector<String> classNames) {
		this.id = id;
		this.tagName = tagName;
		this.classNames = classNames;
	}

}
