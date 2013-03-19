import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;

import arida.ufc.br.moap.vis.engine.impl.Layer;
import arida.ufc.br.moap.vis.engine.impl.VisualizationEngineImpl;
import arida.ufc.br.moap.vis.engine.jxmap.JXMapEngine;

public class Main {

	public static JXMapEngine mapEngine;
	public static JXMapKit map;
	public JPanel contentPane = new JPanel();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.execute();
	}

	private void execute() {

		map = new JXMapKit();
		map.setDefaultProvider(DefaultProviders.OpenStreetMaps);

		ObjectToDraw objectToDraw = new ObjectToDraw();
		Layer layer = new Layer();
		layer.setStroke(new BasicStroke(5.2f, BasicStroke.CAP_ROUND,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		layer.setColor(Color.BLACK);
		layer.addFeaturable(objectToDraw);

		/*
		 * Object 2, Layer 2
		 */
		Layer layer2 = new Layer();
		ObjectToDraw2 objectToDraw2 = new ObjectToDraw2();
		layer2.setStroke(new BasicStroke(5.2f, BasicStroke.CAP_ROUND,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		layer2.setColor(Color.PINK);
		layer2.addFeaturable(objectToDraw2);

		VisualizationEngineImpl engine = new VisualizationEngineImpl();

		mapEngine = new JXMapEngine(map, engine);
		/*
		 * Adding layers in the mapEngine
		 */
		mapEngine.addLayer(layer);
		mapEngine.addLayer(layer2);
		mapEngine.run();

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example 1");
		frame.getContentPane().add(map);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
