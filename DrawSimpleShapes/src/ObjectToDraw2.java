import arida.ufc.br.moap.vis.api.GeometryType;
import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.api.IFeature;
import arida.ufc.br.moap.vis.engine.impl.Feature;
import arida.ufc.br.moap.vis.engine.impl.Geometry;


public class ObjectToDraw2 implements IFeaturable {

	@Override
	public IFeature getFeature() {
		double[][] coordinates = {{-0.119824,51.511214},{-1.109619,52.167194},{-3.867188,52.160455},{-0.119824,51.511214}};
		Geometry geometry = new Geometry(GeometryType.POLYGON, coordinates);
		return new Feature(geometry, null);
	}

}
