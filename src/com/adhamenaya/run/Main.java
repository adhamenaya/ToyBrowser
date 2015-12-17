/**
 * A toy web browser engine built using java, that parses and displays simple HMTL and CSS files
 *
 * @author  Adham Enaya
 * @version 1.0
 * @since   2015-01-15
 */
package com.adhamenaya.run;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.adhamenaya.css.StyleSheet;
import com.adhamenaya.html.Node;
import com.adhamenaya.layout.Dimensions;
import com.adhamenaya.layout.EdgeSizes;
import com.adhamenaya.layout.LayoutBox;
import com.adhamenaya.layout.Rect;
import com.adhamenaya.paint.DisplayCommand;
import com.adhamenaya.paint.LayoutCanvas;
import com.adhamenaya.style.StyledNode;

public class Main {

	public static void main(String[] args) {

		String html = "<div class='a'>Div A" + "  <div class='b'> Div B" + "<div class='c'> Div C" + "<div class='d'> Div D"
				+ "<div class='e'> Div E" + "<div class='f'> Div F" + "<div class='g'> Div G </div>" + "</div>" + "</div>"
				+ "</div>" + "</div>" + "</div>" + "<div class='d'> Adham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWAAdham Enaya from palestine I am working at UNRWA as ABAP developer can youUNRWA as ABAP developer can youUNRWA as ABAP developer can youUNRWA as ABAP developer can youUNRWA as ABAP developer can you check my LinkedIn profile</div></div>";

		String css = " * { display: block; padding: 12px;}" + ".a { background: #ff0000; }" + ".b { background: #ffa500; }"
				+ ".c { background: #ffff00; }" + ".d { background: #008000; }" + ".e { background: #0000ff; }"
				+ ".f { background: #4b0082; }" + ".g { background: #800080; }";

		Dimensions initialBlock = new Dimensions();

		initialBlock.content = new Rect(0.0f, 0.0f, 800.0f, 600.0f);
		initialBlock.border = new EdgeSizes();
		initialBlock.margin = new EdgeSizes();
		initialBlock.padding = new EdgeSizes();

		CSSParser cssParser = new CSSParser();
		HTMLParser htmlParser = new HTMLParser();
		StyleBuilder styleBuilder = new StyleBuilder();
		LayoutTreeBuilder layoutBuilder = new LayoutTreeBuilder();
		DisplayListBuilder displayListBuilder = new DisplayListBuilder();
		LayoutCanvas layoutCanvas = new LayoutCanvas();

		// 1. Parse CSS file
		StyleSheet style = cssParser.parse(css);

		// 2. Parse HTML file
		Node htmlTree = htmlParser.parse(html);

		// 3. Generate the style tree
		StyledNode styleTree = styleBuilder.build(htmlTree, style);

		// 4. Generate the layout boxes tree
		LayoutBox box = layoutBuilder.layoutTree(styleTree, initialBlock);

		// 5. Generate the paint command list
		Vector<DisplayCommand> displayList = displayListBuilder.buildDisplayList(box);

		// 6. paint on the canvas
		layoutCanvas.setDisplayCommandsList(displayList);

		// 7. Display the paints
		final JFrame mainFrame = new JFrame("PinkBerry web browser!");
		mainFrame.getContentPane().add(layoutCanvas);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setViewportView(layoutCanvas);
		mainFrame.setContentPane(pane);
		mainFrame.setVisible(true);

	}
}
