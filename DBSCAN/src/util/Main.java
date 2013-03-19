
package util;

import java.util.ArrayList;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.ListModelImpl;
import clustering.sample.DBScan;
import dao.LocalDAO;
import dao.PersonDAO;
import functions.LocalDistance;

/**
 *
 * @author franzejr
 * @author victorfarias
 * 
 */
public class Main {

    public static void main(String[] args) {
        
    	ListModelImpl<LocalDAO> data = new ListModelImpl<LocalDAO>();        
        data.add(new LocalDAO("1"));
        data.add(new LocalDAO("7"));
        data.add(new LocalDAO("16"));
        data.add(new LocalDAO("20"));
        data.add(new LocalDAO("24"));
        
        ArrayList<PersonDAO> persons = new ArrayList<PersonDAO>();
        PersonDAO person = new PersonDAO();
     
        person.addCheckIn("1");
        person.addCheckIn("7");
        persons.add(person);
        
        person = new PersonDAO();
        person.addCheckIn("1");
        person.addCheckIn("7");
        persons.add(person);
        
        person = new PersonDAO();
        person.addCheckIn("1");
        person.addCheckIn("7");
        persons.add(person);
        
        person = new PersonDAO();
        person.addCheckIn("16");
        person.addCheckIn("20");
        persons.add(person);
        
        person = new PersonDAO();
        person.addCheckIn("16");
        person.addCheckIn("20");
        persons.add(person);
        
        
        
        /* 
         * Instanciação do algoritmo
         */
        DBScan clustering = new DBScan();
        
        /**
         * Indica qual a função de distância. Para o algoritmo neste exemplo, nós não utilizamos nenhuma função
         * 
         **/
        clustering.setDistanceFunction(new LocalDistance(persons));
        
        Parameters parameters = new Parameters();
        parameters.addParam("minPoints", 1);
        parameters.addParam("eps", 6.0);
        IDataModel<LocalCluster> res = clustering.execute(data, parameters);
        
        /*
         * Ver o resultado. Iterar nos clusters encontrados
         */
        for(LocalCluster cluster : res.getInstances()){
            System.out.println("Cluster "+cluster.getId());
            for(Object local : cluster.getObjects()){
                System.out.println("Objetos:\n"+((LocalDAO)(local)).getID());
            }
        }
        
        
    }
}
