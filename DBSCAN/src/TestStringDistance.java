import org.junit.Before;
import org.junit.Test;

import util.LocalCluster;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.ListModelImpl;
import clustering.sample.DBScan;
import functions.DistanceString;


public class TestStringDistance {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		 /* 
         * Instanciacao do algoritmo
         */
        DBScan clustering = new DBScan();
        
        /**
         * Indica qual a funcao de distancia. 
         * 
         **/
        clustering.setDistanceFunction(new DistanceString());
        
        /*
         * Criando um modelo simples de dados utilizando ListModelImpl que eh igual a uma lista de elementos, no caso,
         * strings
         */
        ListModelImpl<String> data = new ListModelImpl<String>();
        data.add("mava");
        data.add("maca");
        data.add("banan");
        data.add("125456789");
        data.add("1230567890");
        data.add("126454789");
        data.add("12445567890"); //11
        data.add("1");
        data.add("12345678901234567890");
        
        /*
         * Execucao do algoritmo.
         * Tem como entrada os dados (data) e parametros. Para esse algoritmo, nenhuma parâmetro foi necessário
         * 
         * O resultado eh armazenado em ListModelImpl<StringCluster>
         */
        Parameters parameters = new Parameters();
        parameters.addParam("minPoints", 2);
        parameters.addParam("eps", 1.0);
        IDataModel<LocalCluster> res = clustering.execute(data, parameters);
        
        /*
         * Ver o resultado. Iterar nos clusters encontrados
         */
        for(LocalCluster cluster : res.getInstances()){
            System.out.println("Cluster "+cluster.getId());
            System.out.println("Objetos:\n"+cluster.getObjects());
        }

	}
	
	@Test
	public void testFuncaoDistanciaString(){
		/*
         * Exemplo de uma funcao de distancia
         */
        DistanceString funcao = new DistanceString();
        System.out.println("Distancia "+funcao.evaluate("maca", "banana"));
	}

}
