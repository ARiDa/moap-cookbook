/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import dao.LocalDAO;
import dao.PersonDAO;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import java.util.ArrayList;
import java.util.Collection;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * Função de distância entre objetos do tipo <T> (String). 
 * Esta classe segue a definição de uma função de distância em IDistanceFunction<T>
 * 
 * @author igobrilhante
 */
public class LocalDistance implements IDistanceFunction<LocalDAO> {

    /*
     * Calcula a distância entre duas Strings retornando um double
     */
    /**
     * 
     * @param t objeto 1
     * @param t1 objeto 2
     * @return distância
     */
    private ArrayList<PersonDAO> persons;
    
    public LocalDistance(Collection persons){
        this.persons = new ArrayList<PersonDAO>(persons);
    }
    
    @Override
    public Double evaluate(LocalDAO t, LocalDAO t1) {
        int count = 0;
        for(PersonDAO person : persons){
            if(person.getCheckIns().contains(t.getID()) && person.getCheckIns().contains(t1.getID()))
                count ++;
        }
        if (count==0)
            return Double.MAX_VALUE;
        //Debug
        //System.out.println("Exec"+" " +t.getID() +" " +t1.getID() +" " +count +" " +(double)10/count);
        return (double)10/count;
    }

    /**
     * 
     * @return nome da função
     */
    @Override
    public String getName() {
        return "Funcao de Distancia de locais";
    }
    
}
