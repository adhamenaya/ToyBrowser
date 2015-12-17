/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.layout;

import java.util.Vector;

import com.adhamenaya.css.Keyword;
import com.adhamenaya.css.Length;
import com.adhamenaya.css.Unit;
import com.adhamenaya.css.Value;
import com.adhamenaya.style.StyledNode;

public class LayoutBox {

	public Dimensions dimensions;
	public BoxType boxType;
	public Vector<LayoutBox> children;

	public LayoutBox(BoxType boxType) {

		dimensions = new Dimensions();
		this.boxType = boxType;
		children = new Vector<LayoutBox>();

	}

	public StyledNode getStyleNode() {

		return boxType.accept(new BoxTypeVisitor<StyledNode>() {

			@Override
			public StyledNode visit(BlockType box) {
				return box.styledNode;
			}

			@Override
			public StyledNode visit(InlineType box) {
				return box.styledNode;
			}

			@Override
			public StyledNode visit(AnonymousType box) {
				return null;
			}
		});

	}

	// Where a new in line child should go.
	public LayoutBox getInlineContainer() {

		if (boxType instanceof AnonymousType || boxType instanceof InlineType) {
			return this;
		} else if (boxType instanceof BlockType) {
			// If we've just generated an anonymous block box, keep using it.
			// Otherwise, create a new one.
			Vector<LayoutBox> children1 = children;

			// if (children.get(children.size() - 1).boxType instance of
			// AnonymousType) {
			children.add(new LayoutBox(new AnonymousType()));
			// }
			return this;
		} else {
			return null;
		}
	}

	// Lay out a box and its descendants.
	public void layout(Dimensions containingBlock) {
		if (boxType instanceof BlockType) {
			layoutBlock(containingBlock);
		} else if (boxType instanceof InlineType) {
			// TODO : implement the inline type
			layoutBlock(containingBlock);

		} else {

		}

	}

	private void layoutBlock(Dimensions containingBlock) {
		// Child width can depend on parent width, so we need to calculate
		// this box's width before laying out its children.
		calculateBlockWidth(containingBlock);

		// Determine where the box is located within its container.
		calculateBlockPosition(containingBlock);

		// Recursively lay out the children of this box.
		layoutBlockChildren();

		// Parent height can depend on child height, so `calculate_height`
		// must be called *after* the children are laid out.
		calculateBlockHeight();

	}

	// Calculate the width of a block-level non-replaced element in normal flow.
	// http://www.w3.org/TR/CSS2/visudet.html#blockwidth
	// Sets the horizontal margin/padding/border dimensions, and the `width`.
	private void calculateBlockWidth(Dimensions containingBlock) {

		StyledNode style = getStyleNode();

		// `width` has initial value `auto`.
		Value initWidth = new Keyword("auto");

		Value width = style.lookUp(initWidth, new String[] { "width" });

		// margin, border, and padding have initial value 0.
		Length zeroLength = new Length(0.0f, Unit.px);

		// Try to lookup the margin values in the attributes list.
		Value leftMargin = style.lookUp(zeroLength, new String[] { "margin-left", "margin" });
		Value rightMargin = style.lookUp(zeroLength, new String[] { "margin-right", "margin" });

		// Border
		Value leftBorder = style.lookUp(zeroLength, new String[] { "border-left-width", "border-width" });
		Value rightBorder = style.lookUp(zeroLength, new String[] { "border-right-width", "border-width" });

		// Padding
		Value leftPadding = style.lookUp(zeroLength, new String[] { "padding-left", "padding" });
		Value rightPadding = style.lookUp(zeroLength, new String[] { "padding-right", "padding" });

		float total = leftMargin.toPx() + rightMargin.toPx() + leftBorder.toPx() + rightBorder.toPx() + leftPadding.toPx()
				+ rightPadding.toPx() + width.toPx();

		// If width is not auto and the total is wider than the container, treat
		// auto margins as 0.

		if (!width.getValueString().equals("auto") && total > containingBlock.content.width) {
			if (leftMargin.getValueString().equals("auto")) {
				leftMargin = new Length(0.0f, Unit.px);
			}

			if (rightMargin.getValueString().equals("auto")) {
				rightMargin = new Length(0.0f, Unit.px);
			}
		}

		// Adjust used values so that the above sum equals
		// `containing_block.width`.
		// Each arm of the `match` should increase the total width by exactly
		// `underflow`,
		// and afterward all values should be absolute lengths in px.
		float underflow = containingBlock.content.width - total;

		boolean widthAuto, marginLeftAuto, marginRightAuto;

		widthAuto = width.getValueString().equals("auto");
		marginLeftAuto = leftMargin.getValueString().equals("auto");
		marginRightAuto = rightMargin.getValueString().equals("auto");

		if (!widthAuto & !marginLeftAuto & !marginRightAuto) {
			// If the values are over constrained, calculate margin_right.
			rightMargin = new Length(rightMargin.toPx() + underflow, Unit.px);

		} else if ((!widthAuto & !marginLeftAuto & marginRightAuto)) {
			rightMargin = new Length(underflow, Unit.px);
		} else if ((!widthAuto & marginLeftAuto & !marginRightAuto)) {
			leftMargin = new Length(underflow, Unit.px);
		} else if ((!widthAuto & marginLeftAuto & marginRightAuto)) {
			leftMargin = new Length(underflow / 2, Unit.px);
			rightMargin = new Length(underflow / 2, Unit.px);

		} else if (widthAuto) {
			if (marginLeftAuto)
				leftMargin = new Length(0.0f, Unit.px);
			if (marginRightAuto)
				rightMargin = new Length(0.0f, Unit.px);

			if (underflow >= 0.0f) {
				// Expand width to fill the underflow.
				width = new Length(underflow, Unit.px);
			} else {
				// Width can't be negative. Adjust the right margin instead.
				width = new Length(0.0f, Unit.px);
				rightMargin = new Length(rightMargin.toPx() + underflow, Unit.px);
			}
		}

		this.dimensions.content.width = width.toPx();
		this.dimensions.padding.left = leftPadding.toPx();
		this.dimensions.padding.right = rightPadding.toPx();
		this.dimensions.margin.right = rightMargin.toPx();
		this.dimensions.margin.left = leftMargin.toPx();

	}

	// / Finish calculating the block's edge sizes, and position it within its
	// containing block.
	// / http://www.w3.org/TR/CSS2/visudet.html#normal-block
	// / Sets the vertical margin/padding/border dimensions, and the `x`, `y`
	// values.
	private void calculateBlockPosition(Dimensions containingBlock) {

		StyledNode style = getStyleNode();

		// margin, border, and padding have initial value 0.
		Length zeroLength = new Length(0.0f, Unit.px);

		// Margin
		this.dimensions.margin.top = (style.lookUp(zeroLength, new String[] { "margin-top", "margin" })).toPx();
		this.dimensions.margin.bottom = (style.lookUp(zeroLength, new String[] { "margin-bottom", "margin" })).toPx();

		// Border
		this.dimensions.border.top = (style.lookUp(zeroLength, new String[] { "border-top-width", "border-width" })).toPx();
		this.dimensions.border.bottom = ((Length) style
				.lookUp(zeroLength, new String[] { "border-bottom-width", "border-width" })).toPx();

		// Padding
		this.dimensions.padding.top = (style.lookUp(zeroLength, new String[] { "padding-top", "padding" })).toPx();
		this.dimensions.padding.bottom = (style.lookUp(zeroLength, new String[] { "padding-bottom", "padding" })).toPx();

		// Position the box below all the previous boxes in the container.
		this.dimensions.content.x = containingBlock.content.x + this.dimensions.margin.left + this.dimensions.border.left
				+ this.dimensions.padding.left;

		this.dimensions.content.y = containingBlock.content.y + containingBlock.content.height + this.dimensions.margin.top
				+ this.dimensions.border.top + this.dimensions.padding.top;
	}

	// Lay out the block's children within its content area.
	// Sets `self.dimensions.height` to the total content height.
	private void layoutBlockChildren() {

		int minimumHeight = 20;

		if (children.size() > 0)
			for (LayoutBox child : children) {
				child.layout(dimensions);
				// Increment the height so each child is laid out below the
				// previous
				// one.
				this.dimensions.content.height += child.dimensions.extraBox().height;
			}
		else
			this.dimensions.content.height = minimumHeight + this.dimensions.content.height + this.dimensions.extraBox().height;

	}

	private void calculateBlockHeight() {
		StyledNode style = getStyleNode();

		Value height = style.specifiedValues.get("height");
		if (height != null && height.toPx() != 0.0f) {
			this.dimensions.content.height = height.toPx();
		}
	}
}
