/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.run;

import java.util.Vector;

import com.adhamenaya.css.Color;
import com.adhamenaya.css.Value;
import com.adhamenaya.layout.BlockType;
import com.adhamenaya.layout.Dimensions;
import com.adhamenaya.layout.InlineType;
import com.adhamenaya.layout.LayoutBox;
import com.adhamenaya.layout.Rect;
import com.adhamenaya.paint.DisplayCommand;
import com.adhamenaya.paint.DrawText;
import com.adhamenaya.paint.SolidColor;

public class DisplayListBuilder {

	Vector<DisplayCommand> list = null;

	public Vector<DisplayCommand> buildDisplayList(LayoutBox rootBox) {
		list = new Vector<DisplayCommand>();
		renderLayoutBox(rootBox);
		return list;
	}

	private void renderLayoutBox(LayoutBox rootBox) {

		renderBackground(rootBox);
		list.addAll(renderBorders(rootBox));

		// Draw text element
		if (rootBox.getStyleNode() != null && rootBox.getStyleNode().node.type.isText()) {
			renderText(rootBox);

		}

		for (LayoutBox child : rootBox.children) {
			renderLayoutBox(child);
		}
	}

	private void renderText(LayoutBox rootBox) {

		DrawText command = new DrawText();
		command.rect = rootBox.dimensions.borderBox();
		command.text = rootBox.getStyleNode().node.type.text;

		list.add(command);
	}

	private Vector<DisplayCommand> renderBorders(LayoutBox rootBox) {

		Vector<DisplayCommand> list = new Vector<DisplayCommand>();

		Color borderColor = (Color) getColor(rootBox, "border-color");
		Dimensions dims = rootBox.dimensions;
		Rect borderBox = rootBox.dimensions.borderBox();

		// Left border
		SolidColor leftBorderCommand = new SolidColor();
		leftBorderCommand.color = borderColor;

		Rect leftBorderRect = new Rect();
		leftBorderRect.x = borderBox.x;
		leftBorderRect.y = borderBox.y;
		leftBorderRect.width = dims.border.left;
		leftBorderRect.height = borderBox.height;

		leftBorderCommand.rect = rootBox.dimensions.borderBox();
		list.add(leftBorderCommand);

		// Right border
		SolidColor rightBorderCommand = new SolidColor();
		rightBorderCommand.color = borderColor;

		Rect rightBorderRect = new Rect();
		rightBorderRect.x = borderBox.x + borderBox.width - dims.border.right;
		rightBorderRect.y = borderBox.y;
		rightBorderRect.width = dims.border.right;
		rightBorderRect.height = borderBox.height;

		rightBorderCommand.rect = rootBox.dimensions.borderBox();
		list.add(rightBorderCommand);

		// Top border
		SolidColor topBorderCommand = new SolidColor();
		topBorderCommand.color = borderColor;

		Rect topBorderRect = new Rect();
		topBorderRect.x = borderBox.x;
		topBorderRect.y = borderBox.y;
		topBorderRect.width = borderBox.width;
		topBorderRect.height = dims.border.top;

		topBorderCommand.rect = rootBox.dimensions.borderBox();
		list.add(topBorderCommand);

		// Bottom border
		SolidColor bottomBorderCommand = new SolidColor();
		bottomBorderCommand.color = borderColor;

		Rect bottomBorderRect = new Rect();
		bottomBorderRect.x = borderBox.x;
		bottomBorderRect.y = borderBox.y + borderBox.height - dims.border.bottom;
		bottomBorderRect.width = borderBox.width;
		bottomBorderRect.height = dims.border.bottom;

		bottomBorderCommand.rect = rootBox.dimensions.borderBox();
		list.add(bottomBorderCommand);

		return list;
	}

	private void renderBackground(LayoutBox rootBox) {

		Color backgroundColor = (Color) getColor(rootBox, "background");

		SolidColor command = new SolidColor();
		command.color = backgroundColor;
		command.rect = rootBox.dimensions.borderBox();
		
		list.add(command);
	}

	private Value getColor(LayoutBox box, String name) {

		// White color
		Color defaultColor = new Color();
		defaultColor.a = 0;
		defaultColor.r = 0;
		defaultColor.g = 0;
		defaultColor.b = 0;

		if (box.boxType instanceof BlockType || box.boxType instanceof InlineType) {
			return box.getStyleNode().lookUp(defaultColor, new String[] { name });
		}
		return null;
	}
}
