package com.adhamenaya.run;

public class Parser {

	// The position is the index of the next character we haven’t processed yet
	protected int poistion;

	// The string to be parsed.
	protected String input;

	// Read the next character without consuming it.
	protected Character nextChar() {
		return input.toCharArray()[poistion];
	}

	// Check the string at the position position starts with the given
	// string or not.
	protected boolean startsWith(String prefix) {
		return input.substring(poistion, input.length()).startsWith(prefix);
	}

	// Return true if all characters are consumed.
	protected boolean eof() {
		return (poistion >= input.length());
	}

	// Return the current character and advance to the next character.
	protected Character consumeChar() {
		if (poistion < input.length()) {
			Character consumedChar = input.toCharArray()[poistion];
 			poistion++;
			return consumedChar;
		} else
			return null;
	}

	// Consume and discard zero or more whitespace characters.
	protected void consumeWhiteSpace() {

		// Consume characters until `test` returns false.
		StringBuilder sb = new StringBuilder();
		while (!eof() && isWhiteSpace(nextChar())) {
			Character consumedChar = consumeChar();
		}
	}

	// Return true if the character is white space.
	protected boolean isWhiteSpace(Character ch) {
		boolean result = (ch == ' ');
		return result;
	}

}
