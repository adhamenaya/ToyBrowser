package com.adhamenaya.style;

import com.adhamenaya.css.Rule;
import com.adhamenaya.css.Specificity;

public class MatchedRule {

	public Specificity specificity;
	public Rule rule;

	public MatchedRule(Specificity specificity, Rule rule) {
		this.rule = rule;
		this.specificity = specificity;
	}
}
