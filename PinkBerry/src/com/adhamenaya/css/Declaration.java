/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

public class Declaration {

	public String name;
	public Value value;

	public Declaration(String name, Value value) {
		this.name = name;
		this.value = value;
	}
}
