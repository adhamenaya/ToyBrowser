package com.adhamenaya.layout;

public interface BoxTypeVisitor<R> {
	public R visit(BlockType box);

	public R visit(InlineType box);

	public R visit(AnonymousType box);
}