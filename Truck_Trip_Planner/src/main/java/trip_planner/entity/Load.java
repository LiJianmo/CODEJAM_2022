package trip_planner.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Load implements Serializable{

	private static final long serialVersionUID = -8559494090031275154L;
	
	public final LocalDateTime pickupTime;
    public final LocalDateTime arriveTime;
    public final Vertex start;
    public final Vertex destination;
    public final Integer loadID;
    public final Double profit;
    public Load lastLoad = null;
    
    public static LocalDateTime plusTime(LocalDateTime dateTime, double hours){
        long h = (long) Math.floor(hours);
        long m = (long) Math.floor((hours - h) * 60);
        long s = (long) Math.floor(((hours - h) * 60 - m) * 60);
        LocalDateTime newDateTime;
        newDateTime= dateTime.plusHours(h);
        newDateTime = newDateTime.plusMinutes(m);
        newDateTime = newDateTime.plusSeconds(s);
        return newDateTime;
    }

    public Load(LocalDateTime pickupTime, Vertex start, Vertex destination, Integer loadID, double profit) {
        this.pickupTime = pickupTime;
        this.start = start;
        this.destination = destination;
        this.loadID = loadID;
        this.profit = profit;
        this.arriveTime = plusTime(pickupTime, Vertex.hours(start, destination));
    }
    
    // The constructor with 7 parameters
    public Load(LocalDateTime pickupTime, double originLatitude, double originLongitude, double destLatitude, double destLongitude, Integer loadID, double profit)
    {
    	this(pickupTime, new Vertex(originLatitude, originLongitude), new Vertex(destLatitude, destLongitude), loadID, profit);
    }

    public Load(Load l){
        this.pickupTime = l.pickupTime;
        this.start = l.start;
        this.destination = l.destination;
        this.loadID = l.loadID;
        this.profit = l.profit;
        this.arriveTime = l.arriveTime;
    }

	@Override
	public String toString() {
		return "Load [pickupTime=" + pickupTime + ", arriveTime=" + arriveTime + ", start=" + start + ", destination="
				+ destination + ", loadID=" + loadID + ", profit=" + profit + ", lastLoad=" + lastLoad + "]";
	}
    
    
}
