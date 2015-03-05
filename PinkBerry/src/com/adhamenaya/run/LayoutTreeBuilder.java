/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.run;

import com.adhamenaya.layout.BlockType;
import com.adhamenaya.layout.Dimensions;
import com.adhamenaya.layout.Display;
import com.adhamenaya.layout.InlineType;
import com.adhamenaya.layout.LayoutBox;
import com.adhamenaya.style.StyledNode;

public class LayoutTreeBuilder {

	public LayoutBox layoutTree(StyledNode node, Dimensions containingBlock) {
		// The layout algorithm expects the container height to start at 0.
		// TODO: Save the initial containing block height, for calculating
		// percent heights.

		containingBlock.content.height = 0.0f;

		LayoutBox rootBox;
		try {
			rootBox = buildLayoutTree(node);
			rootBox.layout(containingBlock);
			return rootBox;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private LayoutBox buildLayoutTree(StyledNode root) throws Exception {

		LayoutBox rootBox = null;

		Display display = root.getValueOfDisplay();

		switch (display) {
		case BLOCK:
			rootBox = new LayoutBox(new BlockType(root));
			break;

		case INLINE:
			rootBox = new LayoutBox(new InlineType(root));
			break;

		default:
			throw new Exception("Root node has display: none.");
		}

		// Traverse the children nodes
		if (root.children != null && root.children.size() > 0) {

			for (StyledNode childNode : root.children) {

				switch (childNode.getValueOfDisplay()) {
				case BLOCK:
					rootBox.children.add(buildLayoutTree(childNode));
					break;

				case INLINE:
					rootBox.getInlineContainer().children.add(buildLayoutTree(childNode));
					break;

				default:
					break;
				}
			}

		}

		return rootBox;
	}

}
