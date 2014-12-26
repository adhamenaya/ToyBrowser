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
