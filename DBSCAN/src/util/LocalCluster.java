/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import arida.ufc.br.moap.clustering.api.ICluster;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que define um cluster para o algorimo StringClustering.
 * 
 * Implementa a interface de cluster Icluster<T> onde <T> é o tipo dos objetos a serem clusterizados.
 * Neste exemplo <T> é String
 * 
 * @author igobrilhante
 */
public class LocalCluster<O extends Object> implements ICluster<O> {

    private final int id;
    private List<O> members = new ArrayList<O>();
    
    public LocalCluster(int id){
        this.id = id;
    }
    
    @Override
    public int getId() {
       return this.id;
    }
    
    public void addObject(O object){
        this.members.add(object);
    }

    @Override
    public List<O> getObjects() {
        return this.members;
    }
    
}
