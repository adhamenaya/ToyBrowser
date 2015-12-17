/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.css;

public class Selector {

	public SimpleSelector simple;

	// Get the specify for simple selector as String
	public String getSpecificityString() {

		Specificity specificity = new Specificity();

		specificity.a = (simple.id.isEmpty() ? 0 : 1);
		specificity.b = simple.classNames.size();
		specificity.c = (simple.tagName.isEmpty() ? 0 : 1);

		return specificity.get();
	}

	public Specificity getSpecificity() {

		Specificity specificity = new Specificity();

		// Element ID
		if (simple != null & simple.id != null) {
			specificity.a = (simple.id.isEmpty() ? 0 : 1);
		} else
			specificity.a = 0;

		// Classes
		specificity.b = simple.classNames.size();

		// Tag name
		if (simple != null & simple.tagName != null) {
			specificity.c = (simple.tagName.isEmpty() ? 0 : 1);
		} else
			specificity.c = 0;

		return specificity;
	}

	public void setSimpleSelector(SimpleSelector simple) {
		this.simple = simple;
	}
}
