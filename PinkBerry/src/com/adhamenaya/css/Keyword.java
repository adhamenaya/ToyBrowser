/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

public class Keyword extends Value {
	public String kString;

	public Keyword(String kString) {
		this.kString = kString;
		super.valueString = kString;
	}

	public String getKeyword() {
		return this.kString;
	}

	@Override
	public void setKeyword(Keyword keyword) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLength(Length length) {
		// TODO Auto-generated method stub

	}

	@Override
	public float toPx() {
		// TODO Auto-generated method stub
		return 0;
	}
}