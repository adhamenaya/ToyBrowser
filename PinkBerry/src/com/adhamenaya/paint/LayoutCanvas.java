package com.adhamenaya.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JComponent;

public class LayoutCanvas extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vector<DisplayCommand> commandList = null;

	private static Font serifFont = new Font("Serif", Font.BOLD, 24);

	public void setDisplayCommandsList(Vector<DisplayCommand> commandList) {
		this.commandList = commandList;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw entire component white
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (DisplayCommand cmd : commandList) {
			if (cmd instanceof SolidColor) {
				com.adhamenaya.css.Color myColor = ((SolidColor) cmd).color;
				if (myColor != null) {
					Color color = new Color(myColor.r, myColor.g, myColor.b, myColor.a);
					g.setColor(color);
				}

				com.adhamenaya.layout.Rect myRect = ((SolidColor) cmd).rect;
				if (myRect != null) {
					g.fillRect((int) myRect.x, (int) myRect.y, (int) myRect.width, (int) (myRect.height));

				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}