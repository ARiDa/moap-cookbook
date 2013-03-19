
package dao;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Victor
 */
public class PersonDAO {
    
    private HashSet<String> checkIns;
    
    public PersonDAO(){
        this.checkIns = new HashSet<String>();
    }
    
    public PersonDAO(Collection checkIns){
        this.checkIns = new HashSet<String>(checkIns);
    }
    
    public void addCheckIn(String ID){
        checkIns.add(ID);
    }
    
    public HashSet getCheckIns(){
        return checkIns;
    }
}
