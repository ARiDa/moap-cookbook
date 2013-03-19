import arida.ufc.br.moap.vis.api.GeometryType;
import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.api.IFeature;
import arida.ufc.br.moap.vis.engine.impl.Feature;
import arida.ufc.br.moap.vis.engine.impl.Geometry;


public class ObjectToDraw implements IFeaturable {

	@Override
	public IFeature getFeature() {
		double[][] coordinates = {{-0.119824,51.511214},{4.636230,52.281602},{2.175293,49.253465},{-3.295898,50.373496},{-0.119824,51.511214}};
		Geometry geometry = new Geometry(GeometryType.POLYGON, coordinates);
		return new Feature(geometry, null);
	}

}
