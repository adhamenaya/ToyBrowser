package com.adhamenaya.css;

public class Selector {

	public SimpleSelector simple;

	public String getSpecificityString() {

		Specificity specificity = new Specificity();

		specificity.a = (simple.id.isEmpty() ? 0 : 1);
		specificity.b = simple.classNames.size();
		specificity.c = (simple.tagName.isEmpty() ? 0 : 1);

		return specificity.get();
	}

	public Specificity getSpecificity() {

		Specificity specificity = new Specificity();

		if (simple != null & simple.id != null) {
			specificity.a = (simple.id.isEmpty() ? 0 : 1);
		} else
			specificity.a = 0;

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
