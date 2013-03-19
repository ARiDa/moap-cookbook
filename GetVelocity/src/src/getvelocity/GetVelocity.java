
package src.getvelocity;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import arida.ufc.br.moap.function.api.IDistanceFunction;
import arida.ufc.br.moap.functions.spatial.LatLonDistance;
import arida.ufc.br.moap.importer.csv.imp.RawTrajectoryCSVImporter;

/**
 *
 *How to get a Trajectory from a CSV file and to computer using a DistanceFunctiona your velocity
 *
 * @author franzejr
 */
public class GetVelocity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Using importer
        RawTrajectoryCSVImporter importer = new RawTrajectoryCSVImporter();
        TrajectoryModelImpl model = new TrajectoryModelImpl();
        //Adding Parameters to the Importer
        Parameters parameters = new Parameters();
        parameters.addParam(RawTrajectoryCSVImporter.PARAMETER_FILE, "points.csv");
        
        try {
            importer.buildImport(model, parameters);
            System.out.println("Trajectories: "+model.getTrajectoryCount());
            System.out.println("Moving Object Count: "+model.getMovingObjectCount());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Using some distance Function
        LatLonDistance distanceFunction = new LatLonDistance();

        for (Object m : model.getInstances()){
        	Trajectory t = (Trajectory)m;
        	t.getId();
        	System.out.println(t.getPoint(0));
        	System.out.println(t.getAnnotations().toString());
        }
        
        //Example using iterable, if we have more than one trajectory
        for (Object obj : model.getInstances()) {
            Trajectory traj = (Trajectory)obj;
        	getInstantaneousVelocity(traj, distanceFunction);
            System.out.println("Average Velocity:"+ getAverageVelocity(traj, distanceFunction));
        }
        
        
        
    }
    /*
     * Instataneous Velocity
     */
    private static void getInstantaneousVelocity(Trajectory traj, IDistanceFunction distanceFunction) {
        
        LatLonPoint pointI, pointJ;
        DateTime timeI, timeJ;
        long timeDifference;
        
        for(int i=1;i<traj.getPoints().size();i++){
            pointI = (LatLonPoint) traj.getPoint(i);
            pointJ = (LatLonPoint) traj.getPoint(i-1);
            
            double distance = distanceFunction.evaluate(pointI, pointJ)/1000.0;
            
            System.out.println("DISTANCE:" +distance);

            
            timeI = (DateTime) traj.getTime(i);
            timeJ = (DateTime) traj.getTime(i-1);
            
            timeDifference = timeI.getMillis() - timeJ.getMillis();
            
            timeDifference = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
            System.out.println("Time Difference: "+timeDifference);
            
            System.out.println("Velocity: "+ distance/(double)timeDifference );
            System.out.println("------------------------------------");
            
            
        }
    
    }
    
   /*
    * Average Velocity
    */
   private static double getAverageVelocity(Trajectory traj, IDistanceFunction distanceFunction){
        LatLonPoint pointI, pointJ;
        DateTime timeI, timeJ;
        long timeDifference;
        double totalDistance=0,totalTime=0;
        
        for(int i=1;i<traj.getPoints().size();i++){
            pointI = (LatLonPoint) traj.getPoint(i);
            pointJ = (LatLonPoint) traj.getPoint(i-1);
            
            double distance = distanceFunction.evaluate(pointI, pointJ)/1000.0;
            totalDistance += distance;
            
            timeI = (DateTime) traj.getTime(i);
            timeJ = (DateTime) traj.getTime(i-1);
            
            timeDifference = timeI.getMillis() - timeJ.getMillis();
            timeDifference = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
            
            totalTime += timeDifference;
            
            
        }
        
        return totalDistance/totalTime;
    } 
}
