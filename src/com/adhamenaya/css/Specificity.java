/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

public class Specificity {

	public int a; // ID level
	public int b; // Class level
	public int c; // Tag level

	public int getInt() {
		/*
		 * DIV#myDiv.class1.class2.class3 Example : ID = 1, class = 3, tag = 1
		 * Specificity integer = 131
		 */
		return Integer.parseInt(String.valueOf(a) + String.valueOf(b) + String.valueOf(c));
	}

	public String get() {
		return String.valueOf(a) + String.valueOf(b) + String.valueOf(c);
	}
}
