
package clustering.sample;

import arida.ufc.br.moap.clustering.api.ICluster;
import arida.ufc.br.moap.clustering.api.IClusteringAlgorithm;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.ListModelImpl;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author franzejr
 * @author victorfarias
 *
 * Esse é um exemplo de como criar um algoritmo de clusterização seguindo o
 * modelo.
 *
 * A classe abstrata IClusteringAlgorithm<T> requer a definição de T que é o
 * tipo a ser clusterizado. Neste exemplo utilizamos O. Ex.
 * IClusteringAlgorithm<O>.
 *
 * Esse algoritmo é bem simples. Ele simplesmente junta Os que possuem o
 * mesmo tamanho. Neste caso, nenhuma função de distância é necessária.
 *
 *
 */
public class DBScan<O extends Object> extends IClusteringAlgorithm<O> {

    private Set<O> corePoints;
    private Set<O> borderPoints;
    private Set<O> noisePoints;
    private Double eps;
    private Integer minPoints;
    private IDistanceFunction funcao;

    /*
     * Construtor padrão
     */
    public DBScan() {
    }

    /**
     * Este é o método que executa o algoritmo. O algoritmo recebe dados de
     * entrada e retorna um modelo com clusters OCluster
     *
     * @param data Dados de entrada cujo tipo é T (O)
     * @param prmtrs Paramêtros para o algoritmo (não utilizado nesse exemplo)
     * @return ListModelImpl<OCluster> modelo de dados com os cluster
     */
    @Override
    public IDataModel<? extends ICluster<O>> execute(IDataModel data, Parameters prmtrs) {
        
        //Inicializando corePoints,borderPoints e noisePoints
        corePoints = new HashSet<O>();
        borderPoints = new HashSet<O>();
        noisePoints = new HashSet<O>();

        //Pegando atributos que foram passados
        eps = (Double) prmtrs.getParamValue("eps");
        minPoints = (Integer) (prmtrs.getParamValue("minPoints"));

        List<O> vizinhos = new ArrayList<O>();
        funcao = (IDistanceFunction) this.distanceFunction;
       
        /* Definindo quem eh corePoint, noisePoint ou borderPoint de acordo com o numero de
         * vizinhos de cada ponto e pelo EPS.
         * 
         * CorePoint: Possui numero de vizinhos maior ou igual ao minPoints
         * BorderPoint: Possui numero de vizinhos menor que o minPoints e esta na
         * dependencia de um corePoint
         * NoisePoints: Nao possui quantidade de minPoints e nao esta na dependencia
         * de um corePoint.
         * 
        */

        for (Object local1 : data.getInstances()) {
            O local = (O)local1;
            vizinhos = getNeighbors(data, funcao, local);
            
            if (vizinhos.size() >= minPoints) {
                corePoints.add(local);
                if(borderPoints.contains(local))
                    borderPoints.remove(local);
                for (O vizinho : vizinhos) {
                    if (!(corePoints.contains(vizinho))) {
                        borderPoints.add(vizinho);

                        if (noisePoints.contains(vizinho)) {
                            noisePoints.remove(vizinho);
                        }
                    }
                }

            } else {
                if (!borderPoints.contains(local)) {
                    noisePoints.add(local);
                }
            }
        }
        
        /* Gera os clusters a partir da classificação de cada ponto
         * 
         * Se escolhe um ponto não escolhido previamente e se adiciona dos os
         * vizinhos dele no mesmo cluster e repete o processo para os vizinhos
         * dos vizinhos desse ponto até que se esgote o cluster.
         */
        List<ICluster> clusters = new ArrayList<ICluster>();
        Set<O> markedPoints = new HashSet<O>();
        Queue<O> queue = new LinkedBlockingQueue<O>();
        LocalCluster<O> cluster = null;
        int id = 0;

        for (Object point1 : data.getInstances()) {
            O point = (O)point1;
            if ((corePoints.contains(point) || borderPoints.contains(point)) 
                    && !markedPoints.contains(point) ) {
                cluster = new LocalCluster(id++);
                clusters.add(cluster);
                markedPoints.add(point);
                do {
                    cluster.addObject(point);
                    List<O> list = getNeighbors(data, funcao, point);
                    for (O pointIter : list) {
                        if(!markedPoints.contains(pointIter))
                            queue.add(pointIter);
                            markedPoints.add(pointIter);
                    }
                    if(!queue.isEmpty()){
                        point = queue.poll();
                    }
                    else{
                        point = null;
                    }
                } 
                while(!queue.isEmpty() || point != null) ;
            }
        }
        
        LocalCluster<O> noiseCluster = new LocalCluster(-1);
        for (Object point1 : data.getInstances()) {
            O point = (O)point1;
            if(noisePoints.contains(point)){
                noiseCluster.addObject(point);
            }
        }
        if(!cluster.getObjects().isEmpty())
            clusters.add(noiseCluster);
        
        return new ListModelImpl(clusters);
    }

    /**
     *
     * @return Nome do algoritmo
     */
    @Override
    public String getName() {
        return "Clustering Example";
    }

    private List<O> getNeighbors(IDataModel<O> data,IDistanceFunction funcao, O local) {

        List<O> vizinhos = new ArrayList<O>();
        for (O localIter : data.getInstances()) {
            if (local!=localIter && funcao.evaluate(local, localIter) <=eps) {
                vizinhos.add(localIter);
            }

        }
        return vizinhos;
    }
}
