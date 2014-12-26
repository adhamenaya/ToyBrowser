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

		String html = "<div class='a'>" + "  <div class='b'>" + "     <div class='c'>" + "        <div class='d'>"
				+ "           <div class='e'>" + "              <div class='f'>" + "                 <div class='g'></div>"
				+ "              </div>" + "           </div>" + "        </div>" + "     </div>" + "  </div>" + "</div>";

		String css = " * { display: block; padding: 12px;}" + ".a { background: #ff0000; }"
				+ ".b { background: #ffa500; }" + ".c { background: #ffff00; }" + ".d { background: #008000; }"
				+ ".e { background: #0000ff; }" + ".f { background: #4b0082; }" + ".g { background: #800080; }";

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

		// (OK) Parse CSS file
		StyleSheet style = cssParser.parse(css);

		// (OK) Parse HTML file
		Node htmlTree = htmlParser.parse(html);

		// (OK) Generate the style tree
		StyledNode styleTree = styleBuilder.build(htmlTree, style);

		// (OK) Generate the layout boxes tree
		LayoutBox box = layoutBuilder.layoutTree(styleTree, initialBlock);

		// (OK) Generate the paint command list
		Vector<DisplayCommand> displayList = displayListBuilder.buildDisplayList(box);

		// (OK) paint on the canvas
		layoutCanvas.setDisplayCommandsList(displayList);

		final JFrame mainFrame = new JFrame("Adham web browser!");
		mainFrame.getContentPane().add(layoutCanvas);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setViewportView(layoutCanvas);
		mainFrame.setContentPane(pane);
		mainFrame.setVisible(true);

	}
}
