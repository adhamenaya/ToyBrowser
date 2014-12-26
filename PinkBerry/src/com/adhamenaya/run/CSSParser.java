package com.adhamenaya.run;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.adhamenaya.css.Color;
import com.adhamenaya.css.Declaration;
import com.adhamenaya.css.Keyword;
import com.adhamenaya.css.Length;
import com.adhamenaya.css.Rule;
import com.adhamenaya.css.Selector;
import com.adhamenaya.css.SimpleSelector;
import com.adhamenaya.css.StyleSheet;
import com.adhamenaya.css.Unit;
import com.adhamenaya.css.Value;

public class CSSParser extends Parser {

	// Parse one simple selector, e.g.: `type#id.class1.class2.class3`
	SimpleSelector parseSimpleSelector() {
		SimpleSelector SimpleSelector = new SimpleSelector(null, null, new Vector<String>());

		while (true) {
			Character ch = nextChar();
			switch (ch) {
			case '#':
				// Consume char to move after the #
				consumeChar();
				SimpleSelector.id = parseIdentifier();
				break;
			case '.':
				consumeChar();
				SimpleSelector.classNames.add(parseIdentifier());
				break;
			case '*':
				consumeChar();
				SimpleSelector.classNames.add("*");
			default:
				if (!isValidIdentifierChar(nextChar()))
					return SimpleSelector;
				String tagName = parseIdentifier();
				SimpleSelector.tagName = tagName;
				break;
			}
		}
	}

	Vector<Selector> parseSelectors() {

		consumeWhiteSpace();

		Vector<Selector> selectors = new Vector<Selector>();
		while (true) {
			SimpleSelector simple = parseSimpleSelector();
			Selector selector = new Selector();
			selector.setSimpleSelector(simple);
			selectors.add(selector);

			Character nextChar = nextChar();

			switch (nextChar) {
			case ',':
				consumeChar();
				break;
			case '{':
				consumeWhiteSpace();
				return sort(selectors);
			default:
				return sort(selectors);
			}
		}
	}

	// Sort the selectors list according the specificity value
	Vector<Selector> sort(Vector<Selector> selectors) {

		Collections.sort(selectors, new Comparator<Selector>() {

			@Override
			public int compare(Selector selector1, Selector selector2) {
				return (selector1.getSpecificityString().compareTo(selector2.getSpecificityString()));
			}
		});

		Collections.reverse(selectors);

		return selectors;
	}

	Vector<Declaration> parseDeclarations() {

		consumeWhiteSpace();

		Vector<Declaration> declarations = new Vector<Declaration>();
		Character consumedChar = consumeChar();

		if (!consumedChar.toString().equals("{"))
			return null;

		while (true) {
			consumeWhiteSpace();
			String nextChar = nextChar().toString();
			boolean endOfDeclaration = nextChar().toString().equals("}");
			if (eof() || endOfDeclaration) {
				consumeChar();
				return declarations;
			}

			Declaration declaration = parseDeclaration();
			if (declaration != null)
				declarations.add(declaration);

		}
	}

	Declaration parseDeclaration() {

		consumeWhiteSpace();
		String propertyName = parseIdentifier();

		consumeWhiteSpace();
		if (!consumeChar().toString().equals(":"))
			return null;

		consumeWhiteSpace();
		Value propertyValue = parseValue();

		if (!consumeChar().toString().equals(";"))
			return null;

		return new Declaration(propertyName, propertyValue);
	}

	Value parseValue() {
		Value value = null;

		if (nextChar().toString().matches("[0-9]")) {
			value = parseLength();
		} else if (nextChar().toString().equals("#")) {
			value = parseColor();
		} else {
			value = new Keyword(parseIdentifier());
		}
		return value;
	}

	Length parseLength() {
		return new Length(parseFloat(), parseUnit());
	}

	float parseFloat() {
		StringBuilder sb = new StringBuilder();
		while (!eof() && nextChar().toString().matches("[0-9.]")) {
			sb.append(consumeChar());
		}
		return Float.parseFloat(sb.toString());
	}

	Unit parseUnit() {

		String unitStr = parseIdentifier().toLowerCase();
		if (unitStr.equals("px"))
			return Unit.px;
		else
			return null;
	}

	Color parseColor() {

		if (!consumeChar().toString().equals("#"))
			return null;

		Color color = new Color();
		color.a = 255;
		color.r = parseHexPair();
		color.g = parseHexPair();
		color.b = parseHexPair();

		return color;

	}

	int parseHexPair() {

		String str = input.substring(poistion, poistion + 2);
		poistion += 2;
		return Integer.parseInt(str, 16);
	}

	String parseIdentifier() {
		// Continue reading characters while you don't have white space or {
		StringBuilder sb = new StringBuilder();
		while (!eof() && nextChar().toString().matches("[a-zA-Z0-9_-]")) {
			Character consumedChar = consumeChar();
			sb.append(consumedChar);
		}
		return sb.toString();
	}

	// Parse a rule set: `<selectors> { <declarations> }`.
	Rule parseRule() {

		Rule rule = new Rule();

		Vector<Selector> selectors = parseSelectors();
		Vector<Declaration> declarations = parseDeclarations();

		rule.selectors = selectors;
		rule.declarations = declarations;

		return rule;
	}

	// Parse a list of rule sets, separated by optional whitespace.
	Vector<Rule> parseRules() {
		Vector<Rule> rules = new Vector<Rule>();
		while (!eof()) {
			Rule rule = parseRule();
			rules.add(rule);
		}
		return rules;
	}

	public StyleSheet parse(String source) {
		input = source;
		poistion = 0;

		StyleSheet sheet = new StyleSheet();
		sheet.rules = parseRules();
		return sheet;
	}

	boolean isValidIdentifierChar(Character ch) {
		return ch.toString().matches("[a-zA-Z0-9_-]");
	}
}
