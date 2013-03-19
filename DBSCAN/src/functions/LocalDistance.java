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
 * Fun��o de dist�ncia entre objetos do tipo <T> (String). 
 * Esta classe segue a defini��o de uma fun��o de dist�ncia em IDistanceFunction<T>
 * 
 * @author igobrilhante
 */
public class LocalDistance implements IDistanceFunction<LocalDAO> {

    /*
     * Calcula a dist�ncia entre duas Strings retornando um double
     */
    /**
     * 
     * @param t objeto 1
     * @param t1 objeto 2
     * @return dist�ncia
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
     * @return nome da fun��o
     */
    @Override
    public String getName() {
        return "Funcao de Distancia de locais";
    }
    
}
