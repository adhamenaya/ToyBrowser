/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
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
