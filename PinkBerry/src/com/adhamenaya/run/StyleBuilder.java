package com.adhamenaya.run;

import java.util.HashMap;
import java.util.Vector;

import com.adhamenaya.css.Declaration;
import com.adhamenaya.css.Rule;
import com.adhamenaya.css.Selector;
import com.adhamenaya.css.SimpleSelector;
import com.adhamenaya.css.StyleSheet;
import com.adhamenaya.css.Value;
import com.adhamenaya.html.ElementData;
import com.adhamenaya.html.Node;
import com.adhamenaya.style.MatchedRule;
import com.adhamenaya.style.StyledNode;

public class StyleBuilder {

	// Apply a style sheet to an entire DOM tree, returning a StyledNode tree.
	public StyledNode build(Node root, StyleSheet styleSheet) {

		// Create Style tree root node.
		StyledNode styledNode = new StyledNode();

		// Add the DOM node for the styled node.
		styledNode.node = root;

		if (root.type.isText()) {
			// If it is a text, then create an empty style values
			styledNode.specifiedValues = new HashMap<String, Value>();

		} else {
			// Get the CSS style values.
			styledNode.specifiedValues = specifiedValues(root.type.element, styleSheet);
		}

		// Traverse the children nodes
		if (root.children != null && root.children.size() > 0) {
			Vector<StyledNode> childrenStyle = new Vector<StyledNode>();

			for (Node childNode : root.children) {
				childrenStyle.add(build(childNode, styleSheet));
			}

			styledNode.children = childrenStyle;
		}

		return styledNode;

	}

	// Apply styles to a single element, returning the specified values.
	private HashMap<String, Value> specifiedValues(ElementData elementData, StyleSheet styleSheet) {

		// Create a hash map for the declarations values.
		HashMap<String, Value> values = new HashMap<String, Value>();

		// Get the matching rules for the element
		Vector<MatchedRule> rules = matchRules(elementData, styleSheet);

		// Iterate all the matching rules
		for (MatchedRule matchedRule : rules) {
			// Iterate all the declarations in the matching rule
			for (Declaration declaration : matchedRule.rule.declarations) {
				values.put(declaration.name, declaration.value);
			}
		}
		return values;
	}

	// Find all CSS rules that match the given element.
	private Vector<MatchedRule> matchRules(ElementData elementData, StyleSheet styleSheet) {

		Vector<MatchedRule> matchedRules = new Vector<MatchedRule>();
		Vector<Rule> rules = styleSheet.rules;

		for (Rule rule : rules) {
			MatchedRule matchedRule = matchRule(elementData, rule);
			if (null != matchedRule)
				matchedRules.add(matchedRule);
		}
		return matchedRules;
	}

	// Matching single CSS rule with a given element data
	private MatchedRule matchRule(ElementData elementData, Rule rule) {

		for (Selector s : rule.selectors) {
			boolean matchedSimpleSelector = matchSimpleSelector(elementData, s.simple);

			if (matchedSimpleSelector) {
				return new MatchedRule(s.getSpecificity(), rule);
			}
		}
		return null;
	}

	private boolean matchSimpleSelector(ElementData elementData, SimpleSelector selector) {
		boolean found = false;

		// Check the tag name
		if (selector.tagName != null)
			if (selector.tagName.equals(elementData.tagName))
				found = true;

		// Check the class name
		for (int i = 0; i < elementData.getClasses().size(); i++) {
			if (selector.classNames.contains("*") || selector.classNames.contains(elementData.getClasses().get(i))) {
				found = true;
				break;
			}
		}

		// Check the element ID
		if (elementData.getId() != null)
			if (selector.id.equals(elementData.getId()))
				found = true;

		// Return true if one or more of the previous is exists.
		return found;
	}

	// Check the type of the selector, here we will work with simple selector.
	private boolean matches(ElementData elementData, Selector selector) {
		if (isSimpleSelector(selector))
			return matchSimpleSelector(elementData, selector.simple);
		else
			return false;
	}

	private boolean isSimpleSelector(Selector selector) {
		return selector.simple != null;
	}
}
