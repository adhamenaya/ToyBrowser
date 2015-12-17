/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.layout;

public interface BoxTypeVisitor<R> {
	public R visit(BlockType box);

	public R visit(InlineType box);

	public R visit(AnonymousType box);
}