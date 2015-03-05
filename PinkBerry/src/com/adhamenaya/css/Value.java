/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

abstract public class Value {

	protected String valueString = "";

	abstract public void setKeyword(Keyword keyword);

	abstract public void setColor(Color color);

	abstract public void setLength(Length length);

	abstract public float toPx();

	public String getValueString() {
		return this.valueString;
	}
}
