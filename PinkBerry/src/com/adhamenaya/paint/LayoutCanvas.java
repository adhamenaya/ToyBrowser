/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JComponent;

public class LayoutCanvas extends JComponent {

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
			} else if (cmd instanceof DrawText) {
				com.adhamenaya.layout.Rect myRect1 = ((DrawText) cmd).rect;

				if (myRect1 != null) {

					Color color = new Color(0, 0, 0);
					g.setColor(color);

					String perparedString = prepareString(g, ((DrawText) cmd).text, (int) myRect1.width);

					drawString(g, perparedString, (int) myRect1.x, (int) myRect1.y);

				}
			}
		}
	}

	private String prepareString(Graphics g, String text, int boxWidth) {
		StringBuilder string = new StringBuilder();
		int lineWidth = 0;
		int fontWidth = g.getFontMetrics().getWidths()[0];

		for (int i = 0; i < text.length(); i++) {
			string.append(String.valueOf(text.toCharArray()[i]));
			lineWidth += fontWidth;

			if (lineWidth > boxWidth) {
				lineWidth = 0;
				string.append("\n");
			}
		}
		return string.toString();
	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}