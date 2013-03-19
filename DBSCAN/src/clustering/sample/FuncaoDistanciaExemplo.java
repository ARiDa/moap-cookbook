/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering.sample;

import arida.ufc.br.moap.distance.spi.IDistanceFunction;

/**
 *
 * Função de distância entre objetos do tipo <T> (String). 
 * Esta classe segue a definição de uma função de distância em IDistanceFunction<T>
 * 
 * @author igobrilhante
 */
public class FuncaoDistanciaExemplo implements IDistanceFunction<String> {

    /*
     * Calcula a distância entre duas Strings retornando um double
     */
    /**
     * 
     * @param t objeto 1
     * @param t1 objeto 2
     * @return distância
     */
    @Override
    public Double evaluate(String t, String t1) {
        return Math.abs(t.length()-t1.length())*1.0;
    }

    /**
     * 
     * @return nome da função
     */
    @Override
    public String getName() {
        return "Funcao de Distancia";
    }
    
}
